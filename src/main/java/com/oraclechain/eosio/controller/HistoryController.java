package com.oraclechain.eosio.controller;

import com.oraclechain.eosio.constants.Variables;
import com.oraclechain.eosio.dto.MessageResult;
import com.oraclechain.eosio.utils.EosErrorUtils;
import com.oraclechain.eosio.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
public class HistoryController {

    //Get information related to an account.
    @CrossOrigin
    @PostMapping("get_key_accounts")
    public MessageResult get_key_accounts(@RequestBody String body) throws Exception {

        String result= HttpClientUtils.ocPost(Variables.eosHistoryUrl+ "get_key_accounts", body );
        return EosErrorUtils.handleEosResponse(result, "get_key_accounts");
    }


    //Get information related to an account.
    @CrossOrigin
    @PostMapping("get_actions")
    public MessageResult get_actions(@RequestBody String body) throws Exception {

        String result= HttpClientUtils.ocPost(Variables.eosHistoryUrl+ "get_actions", body );
        return EosErrorUtils.handleEosResponse(result, "get_actions");
    }

    //Get information related to an account.
    @CrossOrigin
    @PostMapping("get_transaction")
    public MessageResult get_transaction(@RequestBody String body) throws Exception {

        String result= HttpClientUtils.ocPost(Variables.eosHistoryUrl+ "get_transaction", body );
        return EosErrorUtils.handleEosResponse(result, "get_transaction");
    }



}