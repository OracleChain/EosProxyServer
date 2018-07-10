package com.oraclechain.eosio.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.oraclechain.eosio.exceptions.*;
import com.oraclechain.eosio.constants.Variables;
import com.oraclechain.eosio.dto.MessageResult;
import com.oraclechain.eosio.service.RedisService;
import com.oraclechain.eosio.utils.EosErrorUtils;
import com.oraclechain.eosio.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@Slf4j
@RestController
public class TXRawController {



    //This method expects a transaction in JSON format and will attempt to apply it to the blockchain.
    @CrossOrigin
    @PostMapping(value = "push_transaction")
    public MessageResult push_transaction(@RequestBody String body) throws Exception {

        //拼接url
        StringBuilder url = new StringBuilder();
        url.append(Variables.eosChainUrl).append("push_transaction");
        String result= HttpClientUtils.post(
                url.toString(),
                body.toString(),
                "application/json",
                "UTF-8",
                Variables.conTimeOut,
                Variables.reqTimeOut);

        return EosErrorUtils.handleEosResponse(result, "push_transaction");

    }

    //This method expects a transaction in JSON format and will attempt to apply it to the blockchain.
    @CrossOrigin
    @PostMapping(value = "push_transactions")
    public MessageResult push_transactions(@RequestBody String body) throws Exception {

        //拼接url
        StringBuilder url = new StringBuilder();
        url.append(Variables.eosChainUrl).append("push_transactions");
        String result= HttpClientUtils.post(
                url.toString(),
                body.toString(),
                "application/json",
                "UTF-8",
                Variables.conTimeOut,
                Variables.reqTimeOut*2);


        return EosErrorUtils.handleEosResponse(result, "push_transactions");

    }




}
