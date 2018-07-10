package com.oraclechain.eosio.controller;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.oraclechain.eosio.dto.*;
import com.oraclechain.eosio.exceptions.*;
import com.oraclechain.eosio.constants.Variables;
import com.oraclechain.eosio.service.RedisService;
import com.oraclechain.eosio.service.BlockServiceEos;
import com.oraclechain.eosio.utils.EosErrorUtils;
import com.oraclechain.eosio.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Slf4j
@RestController
public class QueryTabController {



    @Resource
    private RedisService redisService;

    @Resource
    private BlockServiceEos blockServiceEos;

    //Get information related to an account.
    @CrossOrigin
    @PostMapping(value = "get_account")
    public MessageResult get_account(@RequestParam(value = "name", required = true) String name) throws Exception {

        //拼接url
        StringBuilder chain_url = new StringBuilder();
        chain_url.append(Variables.eosChainUrl).append("get_account");
        StringBuilder body = new StringBuilder();
        body.append("{\"account_name\":\"" + name + "\"}");


        String result= HttpClientUtils.post(
                chain_url.toString(),
                body.toString(),
                "application/json",
                "UTF-8",
                Variables.conTimeOut,
                Variables.reqTimeOut);


        return EosErrorUtils.handleEosResponse(result, "get_account");

    }



    //Fetch smart contract data from an account.
    @CrossOrigin
    @PostMapping(value = "get_table_rows")
    public MessageResult get_table_rows(@RequestBody String body) throws Exception {

        //拼接url
        StringBuilder url = new StringBuilder();
        url.append(Variables.eosChainUrl).append("get_table_rows");
        String result= HttpClientUtils.post(
                url.toString(),
                body.toString(),
                "application/json",
                "UTF-8",
                Variables.conTimeOut,
                Variables.reqTimeOut);


        return EosErrorUtils.handleEosResponse(result, "get_table_rows");
    }




    //Get information related to an account.
    @CrossOrigin
    @PostMapping(value = "get_account_asset")
    public MessageResult get_account_asset(@RequestParam(value = "name", required = true) String name) throws Exception {



        //STAKED token
        StringBuilder chain_url = new StringBuilder();
        chain_url.append(Variables.eosChainUrl).append("get_account");
        StringBuilder body = new StringBuilder();
        body.append("{\"account_name\":\"" + name + "\"}");

        String result= HttpClientUtils.post(
                chain_url.toString(),
                body.toString(),
                "application/json",
                "UTF-8",
                Variables.conTimeOut,
                Variables.reqTimeOut);



        EosErrorUtils.handleEosResponse(result, "get_account_asset");


        AccountAssetInfo asset_info= new AccountAssetInfo();

        AccountInfo accountInfo = new Gson().fromJson(result, AccountInfo.class);
        BigDecimal cpu = new BigDecimal(accountInfo.getCpu_weight());
        BigDecimal net = new BigDecimal(accountInfo.getNet_weight());
        asset_info.setEos_cpu_weight( cpu.divide( BigDecimal.valueOf(10000)).setScale(8, RoundingMode.DOWN).toPlainString());
        asset_info.setEos_net_weight( net.divide( BigDecimal.valueOf(10000)).setScale(8, RoundingMode.DOWN).toPlainString());
        Long temp = accountInfo.getRam_quota();
        asset_info.setEos_ram_quota(temp.toString());



        BigDecimal eos_balance = blockServiceEos.getBalance(
                Variables.eosChainUrl,
                Variables.EOS_TOKEN_CONTRACT_NAME,
                Variables.EOS_TOKEN_CONTRACT_SYMBOL,
                name);

        BigDecimal oct_balance = blockServiceEos.getBalance(
                Variables.eosChainUrl,
                Variables.OCT_TOKEN_CONTRACT_NAME,
                Variables.OCT_TOKEN_CONTRACT_SYMBOL,
                name);


        if(eos_balance == null || oct_balance == null)
        {
            throw new ExceptionsChain(ErrorCodeEnumChain.unknown_market_id_exception);
        }


        StringBuilder req_url = new StringBuilder();

        String redis_key = Variables.redisKeyPrefixBlockchain+ Variables.redisKeyEosCoinmarketcapMid+ "eos";
        CoinMarketTicker coinMarketTicker_eos = redisService.get(redis_key, CoinMarketTicker.class);
        if(coinMarketTicker_eos == null){
            try{
                req_url.append(Variables.COINMARKETCAP_TICKER).append("eos").append("?convert=CNY");
                result = HttpClientUtils.get(req_url.toString(), "UTF-8");
                coinMarketTicker_eos  = JSON.parseArray(result, CoinMarketTicker.class).get(0);
                redisService.set(redis_key, coinMarketTicker_eos, Variables.redisCacheTimeout);
            }
            catch (Exception e)
            {
                throw new ExceptionsChain(ErrorCodeEnumChain.unknown_market_id_exception);
            }
        }
        redis_key = Variables.redisKeyPrefixBlockchain+ Variables.redisKeyEosCoinmarketcapMid+ "eos";
        CoinMarketTicker coinMarketTicker_oct = redisService.get(redis_key, CoinMarketTicker.class);
        if(coinMarketTicker_oct == null){
            try{
                req_url.append(Variables.COINMARKETCAP_TICKER).append("eos").append("?convert=CNY");
                result = HttpClientUtils.get(req_url.toString(), "UTF-8");
                coinMarketTicker_oct  = JSON.parseArray(result, CoinMarketTicker.class).get(0);
                redisService.set(redis_key, coinMarketTicker_oct, Variables.redisCacheTimeout);
            }
            catch (Exception e)
            {
                throw new ExceptionsChain(ErrorCodeEnumChain.unknown_market_id_exception);
            }
        }

        BigDecimal eos_usd_price = new BigDecimal(coinMarketTicker_eos.getPrice_usd());
        BigDecimal eos_cny_price = new BigDecimal(coinMarketTicker_eos.getPrice_cny());
        double eos_price_change_in_24h = Double.valueOf(coinMarketTicker_eos.getPercent_change_24h());
        asset_info.setAccount_name(name);
        asset_info.setEos_balance(eos_balance.setScale(8, RoundingMode.DOWN).toPlainString());
        asset_info.setEos_balance_usd(eos_balance.multiply(eos_usd_price).setScale(8, RoundingMode.DOWN).toPlainString());
        asset_info.setEos_balance_cny(eos_balance.multiply(eos_cny_price).setScale(8, RoundingMode.DOWN).toPlainString());
        asset_info.setEos_price_usd(eos_usd_price.toString());
        asset_info.setEos_price_cny(eos_cny_price.toString());
        asset_info.setEos_price_change_in_24h(Double.toString(eos_price_change_in_24h));
        asset_info.setEos_market_cap_usd(coinMarketTicker_eos.getMarket_cap_usd());
        asset_info.setEos_market_cap_cny(coinMarketTicker_eos.getMarket_cap_cny());


        BigDecimal oct_usd_price = new BigDecimal(coinMarketTicker_oct.getPrice_usd());
        BigDecimal oct_cny_price = new BigDecimal(coinMarketTicker_oct.getPrice_cny());
        double oct_price_change_in_24h = Double.valueOf(coinMarketTicker_oct.getPercent_change_24h());//.doubleValue();

        asset_info.setOct_balance(oct_balance.setScale(8, RoundingMode.DOWN).toPlainString());
        asset_info.setOct_balance_usd(oct_balance.multiply(oct_usd_price).setScale(8, RoundingMode.DOWN).toPlainString());
        asset_info.setOct_balance_cny(oct_balance.multiply(oct_cny_price).setScale(8, RoundingMode.DOWN).toPlainString());
        asset_info.setOct_price_usd(oct_usd_price.toString());
        asset_info.setOct_price_cny(oct_cny_price.toString());
        asset_info.setOct_price_change_in_24h(Double.toString(oct_price_change_in_24h));
        asset_info.setOct_market_cap_usd(coinMarketTicker_oct.getMarket_cap_usd());
        asset_info.setOct_market_cap_cny(coinMarketTicker_oct.getMarket_cap_cny());


        System.out.println(MessageResult.success(asset_info));
        return MessageResult.success(asset_info);
    }





}
