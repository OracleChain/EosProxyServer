package com.oraclechain.eosio.controller;


import com.google.gson.Gson;
import com.oraclechain.eosio.chain.PackedTransaction;
import com.oraclechain.eosio.constants.Variables;
import com.oraclechain.eosio.dto.*;
import com.oraclechain.eosio.service.BlockServiceEos;
import com.oraclechain.eosio.utils.EosErrorUtils;
import com.oraclechain.eosio.utils.EosTxUtils;
import com.oraclechain.eosio.utils.HttpClientUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;

@Slf4j
@RestController
public class TXAdvController {



    @Resource
    private BlockServiceEos blockServiceEos;




    //This method expects a transaction in JSON format and will attempt to apply it to the blockchain.
    @CrossOrigin
    @PostMapping(value = "push_message")
    public MessageResult push_message(@RequestParam(value = "contract", required = true) String contract,
                                      @RequestParam(value = "action", required = true) String action,
                                      @RequestParam(value = "message", required = true) String message) throws Exception
    {


        PackedTransaction packedTransaction = blockServiceEos.pushMessage(
                Variables.eosChainUrl,
                Variables.eosPrivateKey,
                contract,
                action,
                "{\"from\":\"eosio\",\"to\":\"eosio.token\",\"quantity\":\"1.0000 OCT\",\"memo\":\"\"}",
                EosTxUtils.getActivePermission(Variables.eosAccount));
        String tmp_obj = new Gson().toJson(packedTransaction);

        //send tx
        StringBuilder url = new StringBuilder();
        url.append(Variables.eosChainUrl).append("push_transaction");
        String result= HttpClientUtils.post(
                url.toString(),
                tmp_obj,
                "application/json",
                "UTF-8",
                Variables.conTimeOut,
                Variables.reqTimeOut
        );

        return EosErrorUtils.handleEosResponse(result, "push_message");
    }


}
