package com.oraclechain.eosio.controller;


import com.oraclechain.eosio.constants.Variables;
import com.oraclechain.eosio.dto.MessageResult;
import com.oraclechain.eosio.utils.EosErrorUtils;
import com.oraclechain.eosio.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
public class QueryTXController {


    //Retrieve a transaction from the blockchain.
    @CrossOrigin
    @PostMapping(value = "get_transaction")
    public MessageResult get_transaction(@RequestBody String body) throws Exception {

        //拼接url
        StringBuilder url = new StringBuilder();
        url.append(Variables.eosAccountUrl).append("get_transaction");
        String result= HttpClientUtils.post(
                url.toString(),
                body.toString(),
                "application/json",
                "UTF-8",
                Variables.conTimeOut,
                Variables.reqTimeOut);

        return EosErrorUtils.handleEosResponse(result, "get_transaction");
    }





    // Retrieve all transactions with specific account name referenced in their scope.
    @CrossOrigin
    @PostMapping(value = "get_transactions")
    public MessageResult get_transactions(@RequestBody String body) throws Exception {

        //拼接url
        StringBuilder url = new StringBuilder();
        url.append(Variables.eosAccountUrl).append("get_transactions");
        String result= HttpClientUtils.post(
                url.toString(),
                body.toString(),
                "application/json",
                "UTF-8",
                Variables.conTimeOut,
                Variables.reqTimeOut);

        return EosErrorUtils.handleEosResponse(result, "get_transactions");
    }
}
