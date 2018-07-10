package com.oraclechain.eosio.service.impl;

import com.alibaba.fastjson.JSON;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.oraclechain.eosio.api.EosChainInfo;
import com.oraclechain.eosio.api.GetCurrencyInfo;
import com.oraclechain.eosio.api.JsonToBinRequest;
import com.oraclechain.eosio.api.JsonToBinResponse;
import com.oraclechain.eosio.chain.PackedTransaction;
import com.oraclechain.eosio.chain.SignedTransaction;
import com.oraclechain.eosio.constants.Variables;
import com.oraclechain.eosio.crypto.ec.EosPublicKey;
import com.oraclechain.eosio.dto.CoinMarketTicker;
import com.oraclechain.eosio.dto.UserAssetInfo;
import com.oraclechain.eosio.exceptions.ErrorCodeEnumChain;
import com.oraclechain.eosio.exceptions.ExceptionsChain;
import com.oraclechain.eosio.service.RedisService;
import com.oraclechain.eosio.service.BlockServiceEos;
import com.oraclechain.eosio.types.*;
import com.oraclechain.eosio.utils.EosTxUtils;
import com.oraclechain.eosio.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;


@Slf4j
@Service
public class BlockServiceEosImpl implements BlockServiceEos {


    @Resource
    private RedisService redisService;



    public PackedTransaction pushMessage(String baseUrl,
                                                String oracleKey,
                                                String contract,
                                                String action,
                                                String message,
                                                String [] permissions) throws Exception
    {


        JsonToBinRequest json_obj = new JsonToBinRequest(contract, action, message);
        String tmp_obj = new Gson().toJson(json_obj);


        StringBuilder url = new StringBuilder();
        url.append(baseUrl).append("abi_json_to_bin");
        String result= HttpClientUtils.post(
                url.toString(),
                tmp_obj,
                "application/json",
                "UTF-8",
                Variables.conTimeOut,
                Variables.reqTimeOut);
        JsonToBinResponse bin_obj = new Gson().fromJson(result, JsonToBinResponse.class);


        //get info
        url = new StringBuilder();
        url.append(baseUrl).append("get_info");
        result = HttpClientUtils.get(url.toString(), "UTF-8");
        EosChainInfo chain_info = JSON.parseObject(result, EosChainInfo.class);
        SignedTransaction unsigned_tx = EosTxUtils.createTransaction(
                contract,
                action,
                bin_obj.getBinargs(),
                permissions,
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


        StringBuilder req_url = new StringBuilder();

        //获取EOS账号详情
        req_url.delete(0, req_url.length());
        req_url.append(Variables.eosChainUrl).append("get_currency_balance");
        StringBuilder body = new StringBuilder();

        GetCurrencyInfo currencyInfo = new GetCurrencyInfo();
        currencyInfo.setAccount(accountName);
        currencyInfo.setCode(contractName);
        currencyInfo.setSymbol(tokenSymbol);

//        String temp = JSON.toJSONString(currencyInfo);
        body.append(JSON.toJSONString(currencyInfo));
        String result= HttpClientUtils.post(
                req_url.toString(),
                body.toString(),
                "application/x-www-form-urlencoded",
                "UTF-8",
                Variables.conTimeOut,
                Variables.reqTimeOut);



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





}
