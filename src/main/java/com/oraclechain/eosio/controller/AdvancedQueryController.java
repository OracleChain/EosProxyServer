package com.oraclechain.eosio.controller;

import com.alibaba.fastjson.JSON;
import com.oraclechain.eosio.constants.Variables;
import com.oraclechain.eosio.dto.*;
import com.oraclechain.eosio.exceptions.ErrorCodeEnumChain;
import com.oraclechain.eosio.exceptions.ExceptionsChain;
import com.oraclechain.eosio.service.BlockServiceEos;
import com.oraclechain.eosio.service.RedisService;
import com.oraclechain.eosio.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;
import javax.validation.Valid;

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
