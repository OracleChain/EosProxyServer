package com.oraclechain.eosio.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.oraclechain.eosio.dto.NewDexTicker;
import com.oraclechain.eosio.eosApi.GetCurrencyInfo;
import com.oraclechain.eosio.constants.Variables;
import com.oraclechain.eosio.dto.ExchangeRate;
import com.oraclechain.eosio.dto.UserAsset;
import com.oraclechain.eosio.exceptions.ErrorCodeEnumChain;
import com.oraclechain.eosio.exceptions.ExceptionsChain;
import com.oraclechain.eosio.service.RedisService;
import com.oraclechain.eosio.service.BlockServiceEos;
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
                token_balance = EosTxUtils.translateToken(arr_obj.get(i).getAsString(), tokenSymbol);
            }
        }


        if(token_balance == null)
        {
            token_balance = new BigDecimal("0");
        }
        return token_balance;

    }



    private NewDexTicker getNewDexTicker(String coinmarket_id) throws Exception
    {
        StringBuilder req_url = new StringBuilder();


        String redis_key = Variables.redisKeyPrefixBlockchain+ Variables.redisKeyEosNewdexMid + coinmarket_id;
        NewDexTicker newDexTicker = redisService.get(redis_key, NewDexTicker.class);
        if(newDexTicker == null){
            try{
                req_url.append(Variables.NEWDEX_TICKER).append("?symbol=").append(coinmarket_id);
                String result = HttpClientUtils.ocGet(req_url.toString());
                JsonElement data = new JsonParser().parse(result);
                if(data.isJsonObject()){
                    newDexTicker = new Gson().fromJson(data.getAsJsonObject().get("data").toString(), NewDexTicker.class);
                    redisService.set(redis_key, newDexTicker, Variables.redisCacheTimeout);
                }
            }
            catch (Exception e)
            {
                //不再抛出错误，直接初始化为零
                newDexTicker = new NewDexTicker();
//                throw new ExceptionsChain(ErrorCodeEnumChain.unknown_new_dex_exception);
            }
        }
        return newDexTicker;
    }

    private ExchangeRate getBaseTicker(String coinmarket_id) throws Exception
    {

        StringBuilder req_url = new StringBuilder();



        String redis_key = Variables.redisKeyPrefixBlockchain+ Variables.redisKeyEosCoinmarketcapMid + coinmarket_id;
        ExchangeRate coinMarketTicker = redisService.get(redis_key, ExchangeRate.class);
        if(coinMarketTicker == null){
            try{
                req_url.append(Variables.COINMARKETCAP_TICKER).append(coinmarket_id).append("?convert=CNY");
                String result = HttpClientUtils.get(req_url.toString(), "UTF-8");
                coinMarketTicker  = JSON.parseArray(result, ExchangeRate.class).get(0);
                redisService.set(redis_key, coinMarketTicker, Variables.redisCacheTimeout);
            }
            catch (Exception e)
            {
                //不再抛出错误，直接初始化为零
                coinMarketTicker = new ExchangeRate();
//                throw new ExceptionsChain(ErrorCodeEnumChain.unknown_market_id_exception);
            }
        }
        return coinMarketTicker;
    }



    public ExchangeRate getRate(String external_id) throws Exception{

        //处理异常情况
        if(external_id == null || external_id.isEmpty() || external_id.length()> 12){
            return new ExchangeRate();
        }

        //获取eos
        ExchangeRate exchangeRate = getBaseTicker(Variables.COINMARKETCAP_ID_EOS);
        if(external_id.equals("eos") ){
            return exchangeRate;
        }

        BigDecimal eos_to_cny = new BigDecimal(exchangeRate.getPrice_cny());
        BigDecimal eos_to_usd = new BigDecimal(exchangeRate.getPrice_usd());

        //获取newdex
        NewDexTicker newDexTicker = getNewDexTicker(external_id);
        log.info("--->get_rate success:" + newDexTicker.toString());

        //获取兑换EOS中间价
        BigDecimal coin_to_eos = new BigDecimal(newDexTicker.getLast());
        BigDecimal volume_to_eos = new BigDecimal(newDexTicker.getVolume());

        //转换中间价为货币
        BigDecimal coin_to_cny = coin_to_eos.multiply(eos_to_cny);
        BigDecimal coin_to_usd = coin_to_eos.multiply(eos_to_usd);
        BigDecimal volume_to_cny = volume_to_eos.multiply(eos_to_cny);
        BigDecimal volume_to_usd = volume_to_eos.multiply(eos_to_usd);

        //
        exchangeRate.setId(external_id);
        exchangeRate.setPercent_change_24h(newDexTicker.getChange());
        exchangeRate.setPrice_cny(coin_to_cny.setScale(4, RoundingMode.DOWN).toPlainString());
        exchangeRate.setPrice_usd(coin_to_usd.setScale(4, RoundingMode.DOWN).toPlainString());
        exchangeRate.setMarket_cap_cny(volume_to_cny.setScale(4, RoundingMode.DOWN).toPlainString());
        exchangeRate.setMarket_cap_usd(volume_to_usd.setScale(4, RoundingMode.DOWN).toPlainString());
        return exchangeRate;

    }


    public UserAsset getUserAssetInfo(String baseUrl,
                                      String accountName,
                                      String contractName,
                                      String tokenSymbol,
                                      String coinmarket_id) throws Exception
    {


        ExchangeRate exchangeRate = getRate(coinmarket_id);


        //根据ticker换算成货币
        UserAsset userAssetInfo = new UserAsset();
        userAssetInfo.setAccount_name(accountName);
        userAssetInfo.setContract_name(contractName);
        userAssetInfo.setToken_symbol(tokenSymbol);
        userAssetInfo.setCoinmarket_id(coinmarket_id);

        userAssetInfo.setAsset_market_cap_cny(exchangeRate.getMarket_cap_cny());
        userAssetInfo.setAsset_market_cap_usd(exchangeRate.getMarket_cap_usd());
        userAssetInfo.setAsset_price_change_in_24h(exchangeRate.getPercent_change_24h());


        BigDecimal eos_usd_price = new BigDecimal(exchangeRate.getPrice_usd());
        BigDecimal eos_cny_price = new BigDecimal(exchangeRate.getPrice_cny());
        userAssetInfo.setAsset_price_usd(eos_usd_price.setScale(4, RoundingMode.DOWN).toPlainString());
        userAssetInfo.setAsset_price_cny(eos_cny_price.setScale(4, RoundingMode.DOWN).toPlainString());


        BigDecimal token_balance = getBalance(
                baseUrl,
                contractName,
                tokenSymbol,
                accountName);

        userAssetInfo.setBalance(token_balance.toPlainString());
        userAssetInfo.setBalance_usd(token_balance.multiply(eos_usd_price).setScale(4, RoundingMode.DOWN).toPlainString());
        userAssetInfo.setBalance_cny(token_balance.multiply(eos_cny_price).setScale(4, RoundingMode.DOWN).toPlainString());



        return userAssetInfo;
    }



}
