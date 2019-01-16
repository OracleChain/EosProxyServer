package com.oraclechain.eosio.controller;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.oraclechain.eosio.constants.Variables;
import com.oraclechain.eosio.dto.*;
import com.oraclechain.eosio.eosApi.AccountAssetInfo;
import com.oraclechain.eosio.eosApi.AccountInfo;
import com.oraclechain.eosio.exceptions.ErrorCodeEnumChain;
import com.oraclechain.eosio.exceptions.ExceptionsChain;
import com.oraclechain.eosio.service.BlockServiceEos;
import com.oraclechain.eosio.service.RedisService;
import com.oraclechain.eosio.utils.EosErrorUtils;
import com.oraclechain.eosio.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@RestController
public class AdvancedQueryController {



    @Resource
    private RedisService redisService;

    @Resource
    private BlockServiceEos blockServiceEos;


    //Get asset information related to an array of accounts.
    @CrossOrigin
    @PostMapping("get_account_assets")
    public MessageResult get_account_assets(@Valid @RequestBody ReqAssetsInfo[] reqAssetsInfos) throws Exception
    {
        log.info("--->get_account_assets :" + new Gson().toJson(reqAssetsInfos));

        RspAssetsInfo rspAssetsInfo = new RspAssetsInfo();
        for(int i = 0; i< reqAssetsInfos.length; i++){
            ReqAssetsInfo reqAssetsInfo = reqAssetsInfos[i];

                UserAsset userAssetInfo = blockServiceEos.getUserAssetInfo(
                        Variables.eosChainUrl,
                        reqAssetsInfo.getAccount_name(),
                        reqAssetsInfo.getContract_name(),
                        reqAssetsInfo.getToken_symbol(),
                        reqAssetsInfo.getCoinmarket_id()
                );
            rspAssetsInfo.addUserAsset(userAssetInfo);
        }
        log.info("--->get_account_assets success:" + rspAssetsInfo.toString());
        return MessageResult.success(rspAssetsInfo);
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


        ExchangeRate coinMarketTicker_eos = blockServiceEos.getBaseTicker("eos");
        ExchangeRate coinMarketTicker_oct = blockServiceEos.getBaseTicker("oraclechain");

        //EOS
        BigDecimal eos_usd_price = new BigDecimal(coinMarketTicker_eos.getPrice_usd());
        BigDecimal eos_cny_price = new BigDecimal(coinMarketTicker_eos.getPrice_cny());
        double eos_price_change_in_24h = Double.valueOf(coinMarketTicker_eos.getPercent_change_24h());//.doubleValue();

        asset_info.setAccount_name(name);
        asset_info.setEos_balance(eos_balance.setScale(8, RoundingMode.DOWN).toPlainString());
        asset_info.setEos_balance_usd(eos_balance.multiply(eos_usd_price).setScale(8, RoundingMode.DOWN).toPlainString());
        asset_info.setEos_balance_cny(eos_balance.multiply(eos_cny_price).setScale(8, RoundingMode.DOWN).toPlainString());
        asset_info.setEos_price_usd(eos_usd_price.toString());
        asset_info.setEos_price_cny(eos_cny_price.toString());
        asset_info.setEos_price_change_in_24h(Double.toString(eos_price_change_in_24h));
        asset_info.setEos_market_cap_usd(coinMarketTicker_eos.getMarket_cap_usd());
        asset_info.setEos_market_cap_cny(coinMarketTicker_eos.getMarket_cap_cny());


        //OCT
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




    //Get latest information related to a node
    @CrossOrigin
    @PostMapping("get_rate")
    public MessageResult get_rate(@RequestParam(value = "coinmarket_id", required = true) String coinmarket_id) throws Exception
    {

        ExchangeRate coinMarketTicker = blockServiceEos.getRate(coinmarket_id);
        log.info("--->get_rate success:" + coinMarketTicker.toString());
        return MessageResult.success(coinMarketTicker);
    }




    //Get coinmarketcap sparklines of eos and oct
    @CrossOrigin
    @GetMapping("get_sparklines")
    public MessageResult get_sparklines() throws Exception
    {
        Sparklines sparklines = new Sparklines();
        sparklines.setSparkline_eos_png(Variables.COINMARKETCAP_SPARKLINES_EOS);
        sparklines.setSparkline_oct_png(Variables.COINMARKETCAP_SPARKLINES_OCT);
        sparklines.setSparkline_iq_png(Variables.COINMARKETCAP_SPARKLINES_IQ);
        log.info("--->get_sparklines success:" + sparklines.toString());
        return MessageResult.success(sparklines);
    }



}
