package com.oraclechain.eosio.controller;


import com.google.gson.Gson;
import com.oraclechain.eosio.chain.PackedTransaction;
import com.oraclechain.eosio.constants.Variables;
import com.oraclechain.eosio.dto.*;
import com.oraclechain.eosio.service.BlockServiceEos;
import com.oraclechain.eosio.utils.EosErrorUtils;
import com.oraclechain.eosio.utils.HttpClientUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;

@Slf4j
@RestController
public class TransactionController {



    @Resource
    private BlockServiceEos blockServiceEos;


    //This method expects a transaction in JSON format and will attempt to apply it to the api.
    @CrossOrigin
    @PostMapping("push_action")
    public MessageResult push_action(@RequestParam(value = "contract", required = true) String contract,
                                      @RequestParam(value = "action", required = true) String action,
                                      @RequestParam(value = "data", required = true) String data) throws Exception
    {
        /////////////params format
        //contract: eosio.token
        //action:   transfer
        //data:     {"from":"oraclechain4","to":"issaytseng11", "quantity":"0.0001 EOS", "memo":""}
        PackedTransaction packedTransaction = blockServiceEos.createTrx(
                Variables.eosPrivateKey,
                contract,
                action,
                data);
        String tmp_obj = new Gson().toJson(packedTransaction);
        String result= HttpClientUtils.ocPost( Variables.eosChainUrl+ "push_transaction", tmp_obj);
        return EosErrorUtils.handleEosResponse(result, "push_action");
    }


}
