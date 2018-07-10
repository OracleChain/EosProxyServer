package com.oraclechain.eosio.controller;

import com.oraclechain.eosio.constants.Variables;
import com.oraclechain.eosio.dto.*;
import com.oraclechain.eosio.utils.EosErrorUtils;
import com.oraclechain.eosio.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
public class ChainAPIController {




    //Get latest information related to a node
    @CrossOrigin
    @GetMapping(value = "get_info")
    public MessageResult get_info() throws Exception {

        //拼接url
        StringBuilder url = new StringBuilder();
        url.append(Variables.eosChainUrl).append("get_info");
        String result = HttpClientUtils.get(url.toString(), "UTF-8");

        return EosErrorUtils.handleEosResponse(result, "get_info");
    }


    //Get information related to a block.
    @CrossOrigin
    @PostMapping(value = "get_block")
    public MessageResult get_block(@RequestParam(value = "block_num_or_id", required = true) String block_num_or_id) throws Exception {

        //拼接url
        StringBuilder url = new StringBuilder();
        url.append(Variables.eosChainUrl).append("get_block");
        StringBuilder body = new StringBuilder();
        body.append("{\"block_num_or_id\":" + block_num_or_id + "}");
        String result= HttpClientUtils.post(
                url.toString(),
                body.toString(),
                "application/x-www-form-urlencoded",
                "UTF-8",
                Variables.conTimeOut,
                Variables.reqTimeOut);


        return EosErrorUtils.handleEosResponse(result, "get_block");
    }






    //Get required keys to sign a transaction from list of your keys.
    @CrossOrigin
    @PostMapping(value = "get_required_keys")
    public MessageResult get_required_keys(@RequestBody String body) throws Exception {

        //拼接url
        StringBuilder url = new StringBuilder();
        url.append(Variables.eosChainUrl).append("get_required_keys");
        String result= HttpClientUtils.post(
                url.toString(),
                body.toString(),
                "application/json",
                "UTF-8",
                Variables.conTimeOut,
                Variables.reqTimeOut);



        return EosErrorUtils.handleEosResponse(result, "get_required_keys");
    }


    //Serialize json to binary hex.
    @CrossOrigin
    @PostMapping(value = "abi_json_to_bin")
    public MessageResult abi_json_to_bin(@RequestBody String body) throws Exception {

        //拼接url
        StringBuilder url = new StringBuilder();
        url.append(Variables.eosChainUrl).append("abi_json_to_bin");
        String result= HttpClientUtils.post(
                url.toString(),
                body.toString(),
                "application/json",
                "UTF-8",
                Variables.conTimeOut,
                Variables.reqTimeOut);


        return EosErrorUtils.handleEosResponse(result, "abi_json_to_bin");
    }

}