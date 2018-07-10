package com.oraclechain.eosio.controller;

import com.alibaba.fastjson.JSON;
import com.oraclechain.eosio.constants.Variables;
import com.oraclechain.eosio.dto.CoinMarketTicker;
import com.oraclechain.eosio.dto.MessageResult;
import com.oraclechain.eosio.dto.Sparklines;
import com.oraclechain.eosio.exceptions.ErrorCodeEnumChain;
import com.oraclechain.eosio.exceptions.ExceptionsChain;
import com.oraclechain.eosio.service.RedisService;
import com.oraclechain.eosio.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import javax.annotation.Resource;

@Slf4j
@RestController
public class QueryExtController {



    @Resource
    private RedisService redisService;



    //Get latest information related to a node
    @CrossOrigin
    @PostMapping(value = "get_rate")
    public MessageResult get_rate(@RequestParam(value = "coinmarket_id", required = true) String coinmarket_id) throws Exception
    {

        StringBuilder req_url = new StringBuilder();

        String redis_key = Variables.redisKeyPrefixBlockchain+ Variables.redisKeyEosCoinmarketcapMid+ coinmarket_id;
        CoinMarketTicker coinMarketTicker = redisService.get(redis_key, CoinMarketTicker.class);
        if(coinMarketTicker == null){
            try{
                req_url.append(Variables.COINMARKETCAP_TICKER).append(coinmarket_id).append("?convert=CNY");
                String result = HttpClientUtils.get(req_url.toString(), "UTF-8");
                coinMarketTicker  = JSON.parseArray(result, CoinMarketTicker.class).get(0);
                redisService.set(redis_key, coinMarketTicker, Variables.redisCacheTimeout);
            }
            catch (Exception e)
            {
                throw new ExceptionsChain(ErrorCodeEnumChain.unknown_market_id_exception);
            }
        }


        log.info("--->get_rate success:" + coinMarketTicker.toString());
        return MessageResult.success(coinMarketTicker);
    }


    //Get coinmarketcap sparklines of eos and oct
    @CrossOrigin
    @GetMapping(value = "get_sparklines")
    public MessageResult get_sparklines() throws Exception
    {
        Sparklines sparklines = new Sparklines();
        sparklines.setSparkline_eos_png(Variables.COINMARKETCAP_SPARKLINES_EOS);
        sparklines.setSparkline_oct_png(Variables.COINMARKETCAP_SPARKLINES_OCT);
        log.info("--->get_sparklines success:" + sparklines.toString());
        return MessageResult.success(sparklines);
    }

}
