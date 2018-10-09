package com.oraclechain.eosio.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.oraclechain.eosio.eosApi.EosChainInfo;
import com.oraclechain.eosio.eosApi.GetCurrencyInfo;
import com.oraclechain.eosio.eosApi.JsonToBinRequest;
import com.oraclechain.eosio.eosApi.JsonToBinResponse;
import com.oraclechain.eosio.chain.PackedTransaction;
import com.oraclechain.eosio.chain.SignedTransaction;
import com.oraclechain.eosio.constants.Variables;
import com.oraclechain.eosio.dto.CoinMarketTicker;
import com.oraclechain.eosio.dto.UserAsset;
import com.oraclechain.eosio.exceptions.ErrorCodeEnumChain;
import com.oraclechain.eosio.exceptions.ExceptionsChain;
import com.oraclechain.eosio.service.RedisService;
import com.oraclechain.eosio.service.BlockServiceEos;
import com.oraclechain.eosio.utils.EosErrorUtils;
import com.oraclechain.eosio.utils.EosTxUtils;
import com.oraclechain.eosio.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;


@Slf4j
@Service
public class BlockServiceEosImpl implements BlockServiceEos {


    @Resource
    private RedisService redisService;



    public PackedTransaction createTrx(String oracleKey,
                                         String contract,
                                         String action,
                                         String message) throws Exception
    {
        //abi to bin
        JsonToBinRequest jsonToBinRequest = new JsonToBinRequest(contract, action, message);
        String jsonToBinRequestJson = new Gson().toJson(jsonToBinRequest);
        String result= HttpClientUtils.ocPost( Variables.eosChainUrl+ "abi_json_to_bin", jsonToBinRequestJson);
        EosErrorUtils.handleEosResponse(result, "abi_json_to_bin");
        JsonToBinResponse jsonToBinResponse = new Gson().fromJson(result, JsonToBinResponse.class);

        //get info
        result= HttpClientUtils.ocGet( Variables.eosChainUrl+ "get_info");
        EosErrorUtils.handleEosResponse(result, "get_info");
        EosChainInfo chain_info = new Gson().fromJson(result, EosChainInfo.class);

        SignedTransaction unsigned_tx = EosTxUtils.createTransaction(
                contract,
                action,
                jsonToBinResponse.getBinargs(),
                EosTxUtils.getActivePermission(Variables.eosAccount),
                chain_info
        );

        SignedTransaction signed_tx = EosTxUtils.signTransaction(unsigned_tx, oracleKey, chain_info.getChain_id());
        return new PackedTransaction(signed_tx);
    }


    public BigDecimal getBalance(String baseUrl,
                                 String contractName,
                                 String tokenSymbol,
                                 String accountName) throws Exception
    {

        GetCurrencyInfo currencyInfo = new GetCurrencyInfo();
        currencyInfo.setAccount(accountName);
        currencyInfo.setCode(contractName);
        currencyInfo.setSymbol(tokenSymbol);
        String json = new Gson().toJson(currencyInfo);
        String result= HttpClientUtils.ocPost(Variables.eosChainUrl+ "get_currency_balance", json);
        //should NOT add handleEosResponse here

        BigDecimal token_balance = null;
        JsonElement data = new JsonParser().parse(result);
        if(data.isJsonArray())
        {
            JsonArray arr_obj = data.getAsJsonArray();
            for(int i=0 ; i< arr_obj.size(); i++)
            {
                String tokenHolder = arr_obj.get(i).getAsString();
                String[] tokenHolderArray = tokenHolder.split(" ");
                if(tokenHolderArray[1].equals(tokenSymbol))
                {
                    token_balance = new BigDecimal(tokenHolderArray[0]);
                    break;
                }
            }
        }

        if(token_balance == null)
        {
            token_balance = new BigDecimal("0");
        }

        return token_balance;

    }



    public CoinMarketTicker getTicker(String coinmarket_id) throws Exception
    {

        StringBuilder req_url = new StringBuilder();

        String redis_key = Variables.redisKeyPrefixBlockchain+ Variables.redisKeyEosCoinmarketcapMid + coinmarket_id;
        CoinMarketTicker coinMarketTicker = redisService.get(redis_key, CoinMarketTicker.class);
        if(coinMarketTicker == null){
            try{
                req_url.append(Variables.COINMARKETCAP_TICKER).append(coinmarket_id).append("?convert=CNY");
                String result = HttpClientUtils.ocGet(req_url.toString());
                coinMarketTicker  = JSON.parseArray(result, CoinMarketTicker.class).get(0);
                redisService.set(redis_key, coinMarketTicker, Variables.redisCacheTimeout);
            }
            catch (Exception e)
            {
                throw new ExceptionsChain(ErrorCodeEnumChain.unknown_market_id_exception);
            }
        }
        return coinMarketTicker;
    }




    public UserAsset getUserAssetInfo(String baseUrl,
                                      String accountName,
                                      String contractName,
                                      String tokenSymbol,
                                      String coinmarket_id) throws Exception
    {

        CoinMarketTicker coinMarketTicker = null;
        if(coinmarket_id == null || coinmarket_id.isEmpty() || coinmarket_id.length()> 20){
            coinMarketTicker = new CoinMarketTicker();
        }
        else
        {
            coinMarketTicker = getTicker(coinmarket_id);
        }


        UserAsset userAssetInfo = new UserAsset();
        userAssetInfo.setAccount_name(accountName);
        userAssetInfo.setContract_name(contractName);
        userAssetInfo.setToken_symbol(tokenSymbol);
        userAssetInfo.setCoinmarket_id(coinmarket_id);

        userAssetInfo.setAsset_market_cap_cny(coinMarketTicker.getMarket_cap_cny());
        userAssetInfo.setAsset_market_cap_usd(coinMarketTicker.getMarket_cap_usd());
        userAssetInfo.setAsset_price_change_in_24h(coinMarketTicker.getPercent_change_24h());


        BigDecimal eos_usd_price = new BigDecimal(coinMarketTicker.getPrice_usd());
        BigDecimal eos_cny_price = new BigDecimal(coinMarketTicker.getPrice_cny());
        userAssetInfo.setAsset_price_usd(eos_usd_price.setScale(Variables.moneyPrecision, RoundingMode.DOWN).toPlainString());
        userAssetInfo.setAsset_price_cny(eos_cny_price.setScale(Variables.moneyPrecision, RoundingMode.DOWN).toPlainString());


        BigDecimal token_balance = getBalance(
                baseUrl,
                contractName,
                tokenSymbol,
                accountName);

        userAssetInfo.setBalance(token_balance.toPlainString());
        userAssetInfo.setBalance_usd(token_balance.multiply(eos_usd_price).setScale(Variables.moneyPrecision, RoundingMode.DOWN).toPlainString());
        userAssetInfo.setBalance_cny(token_balance.multiply(eos_cny_price).setScale(Variables.moneyPrecision, RoundingMode.DOWN).toPlainString());



        return userAssetInfo;
    }



}
