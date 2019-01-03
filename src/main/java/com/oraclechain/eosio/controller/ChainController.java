package com.oraclechain.eosio.controller;

import com.oraclechain.eosio.constants.Variables;
import com.oraclechain.eosio.dto.*;
import com.oraclechain.eosio.utils.EosErrorUtils;
import com.oraclechain.eosio.utils.HttpClientUtils;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


@Slf4j
@RestController
public class ChainController {



    //Get latest information related to a node
    @CrossOrigin
    @GetMapping("get_info")
    public MessageResult get_info() throws Exception {

        String result= HttpClientUtils.ocGet(Variables.eosChainUrl+ "get_info" );
        return EosErrorUtils.handleEosResponse(result, "get_info");
    }


    //Get information related to a block.
    @CrossOrigin
    @PostMapping("get_block")
    public MessageResult get_block(@RequestBody String body) throws Exception {

        String result= HttpClientUtils.ocPost(Variables.eosChainUrl+ "get_block", body );
        return EosErrorUtils.handleEosResponse(result, "get_block");
    }


    //Get required keys to sign a transaction from list of your keys.
    @CrossOrigin
    @PostMapping("get_required_keys")
    public MessageResult get_required_keys(@RequestBody String body) throws Exception {

        String result= HttpClientUtils.ocPost(Variables.eosChainUrl+ "get_required_keys", body );
        return EosErrorUtils.handleEosResponse(result, "get_required_keys");
    }


    //Serialize json to binary hex.
    @CrossOrigin
    @PostMapping("abi_json_to_bin")
    public MessageResult abi_json_to_bin(@RequestBody String body) throws Exception {

        String result= HttpClientUtils.ocPost(Variables.eosChainUrl+ "abi_json_to_bin", body );
        return EosErrorUtils.handleEosResponse(result, "abi_json_to_bin");
    }

    //Deserialize binary hex to json.
    @CrossOrigin
    @PostMapping(value = "abi_bin_to_json")
    public MessageResult abi_bin_to_json(@RequestBody String body) throws Exception {

        String result= HttpClientUtils.ocPost(Variables.eosChainUrl+ "abi_bin_to_json", body );
        return EosErrorUtils.handleEosResponse(result, "abi_bin_to_json");
    }

    //Get information related to an account.
    @CrossOrigin
    @PostMapping("get_account")
    public MessageResult get_account(@RequestBody String body) throws Exception {

        String result= HttpClientUtils.ocPost(Variables.eosChainUrl+ "get_account", body );
        return EosErrorUtils.handleEosResponse(result, "get_account");
    }


    //Fetch smart contract data from an account.
    @CrossOrigin
    @PostMapping("get_table_rows")
    public MessageResult get_table_rows(@RequestBody String body) throws Exception {

        String result= HttpClientUtils.ocPost(Variables.eosChainUrl+ "get_table_rows", body );
        return EosErrorUtils.handleEosResponse(result, "get_table_rows");
    }


    //Get asset information
    @CrossOrigin
    @PostMapping("get_currency_stats")
    public MessageResult get_currency_stats(@RequestBody String body) throws Exception {

        String result= HttpClientUtils.ocPost(Variables.eosChainUrl+ "get_currency_stats", body );
        return EosErrorUtils.handleEosResponse(result, "get_currency_stats");
    }

    //Get balance information
    @CrossOrigin
    @PostMapping("get_currency_balance")
    public MessageResult get_currency_balance(@RequestBody String body) throws Exception {

        String result= HttpClientUtils.ocPost(Variables.eosChainUrl+ "get_currency_balance", body );
        return EosErrorUtils.handleEosResponse(result, "get_currency_balance");
    }


}