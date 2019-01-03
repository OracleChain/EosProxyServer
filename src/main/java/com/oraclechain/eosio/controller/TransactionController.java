package com.oraclechain.eosio.controller;


import com.google.gson.Gson;
import com.oraclechain.eosio.chain.PackedTransaction;
import com.oraclechain.eosio.chain.SignedTransaction;
import com.oraclechain.eosio.constants.Variables;
import com.oraclechain.eosio.crypto.ec.EosPublicKey;
import com.oraclechain.eosio.dto.*;
import com.oraclechain.eosio.eosApi.EosChainInfo;
import com.oraclechain.eosio.eosApi.EosNewAccount;
import com.oraclechain.eosio.eosApi.JsonToBinRequest;
import com.oraclechain.eosio.eosApi.JsonToBinResponse;
import com.oraclechain.eosio.eosTypes.EosBuyRam;
import com.oraclechain.eosio.eosTypes.EosBuyRamBytes;
import com.oraclechain.eosio.eosTypes.EosDelegate;
import com.oraclechain.eosio.eosTypes.TypePublicKey;
import com.oraclechain.eosio.exceptions.ErrorCodeEnumChain;
import com.oraclechain.eosio.exceptions.ExceptionsChain;
import com.oraclechain.eosio.service.BlockServiceEos;
import com.oraclechain.eosio.service.RedisService;
import com.oraclechain.eosio.utils.EosErrorUtils;
import com.oraclechain.eosio.utils.EosTxUtils;
import com.oraclechain.eosio.utils.HttpClientUtils;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;


import javax.annotation.Resource;
import java.math.BigDecimal;
import java.math.RoundingMode;

@Slf4j
@RestController
public class TransactionController {



    @Resource
    private RedisService redisService;
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
        //abi to bin
        JsonToBinRequest jsonToBinRequest = new JsonToBinRequest(contract, action, data);
        String jsonToBinRequestJson = new Gson().toJson(jsonToBinRequest);
        String result= HttpClientUtils.ocPost( Variables.eosChainUrl+ "abi_json_to_bin", jsonToBinRequestJson);
        EosErrorUtils.handleEosResponse(result, "abi_json_to_bin");
        JsonToBinResponse jsonToBinResponse = new Gson().fromJson(result, JsonToBinResponse.class);

        //get info
        result= HttpClientUtils.ocGet( Variables.eosChainUrl+ "get_info");
        EosErrorUtils.handleEosResponse(result, "get_info");
        EosChainInfo chain_info = new Gson().fromJson(result, EosChainInfo.class);

        //create and sign
        SignedTransaction unsigned_tx = EosTxUtils.createTransaction(
                contract,
                action,
                jsonToBinResponse.getBinargs(),
                EosTxUtils.getActivePermission(Variables.eosAccount),
                chain_info
        );
        SignedTransaction signed_tx = EosTxUtils.signTransaction(
                unsigned_tx,
                Variables.eosPrivateKey,
                chain_info.getChain_id()
        );

        //send out
        result= HttpClientUtils.ocPost(Variables.eosChainUrl+ "push_transaction", new Gson().toJson(new PackedTransaction(signed_tx)) );
        return EosErrorUtils.handleEosResponse(result, "push_action");
    }


    //This method expects a transaction in JSON format and will attempt to apply it to the api.
    @CrossOrigin
    @PostMapping("push_transaction")
    public MessageResult push_transaction(@RequestBody String body) throws Exception {

        String result= HttpClientUtils.ocPost( Variables.eosChainUrl+ "push_transaction", body);
        return EosErrorUtils.handleEosResponse(result, "push_transaction");
    }

    //This method expects a transactionS in JSON format and will attempt to apply it to the api.
    @CrossOrigin
    @PostMapping("push_transactions")
    public MessageResult push_transactions(@RequestBody String body) throws Exception {

        String result= HttpClientUtils.ocPost( Variables.eosChainUrl+ "push_transactions", body);
        return EosErrorUtils.handleEosResponse(result, "push_transactions");
    }



    //create free account by operation account(the method call is verified in redis key, you can change other method)
    @CrossOrigin
    @PostMapping(value = "create_account")
    public MessageResult create_account(@RequestParam(value = "account_name", required = true) String account_name,
                                        @RequestParam(value = "active_key", required = true) String active_key,
                                        @RequestParam(value = "owner_key", required = true) String owner_key,
                                        @RequestParam(value = "nonce", required = true) String nonce) throws Exception {

        //参数检查
        if (nonce.length() != 32 || active_key.length() != 53  || owner_key.length() != 53 ) {
            throw new ExceptionsChain(ErrorCodeEnumChain.request_format_exception);
        }


        //对Personal服务器nonce验证
        String redis_key = Variables.redisKeyPrefixPersonalHead + Variables.redisKeyPrefixPersonalTailFreeAccount;
        String redis_value = redisService.get(redis_key);
        if (!nonce.equals(redis_value) || redis_value == null) {
            throw new ExceptionsChain(ErrorCodeEnumChain.request_validation_exception);
        }

        //创建账号交易
        StringBuilder url = new StringBuilder();
        url.append(Variables.eosChainUrl).append("get_info");
        String result = HttpClientUtils.get(url.toString(), "UTF-8");
        EosChainInfo chain_info = new Gson().fromJson(result, EosChainInfo.class);

        //new account
        EosNewAccount newAccountData = new EosNewAccount(
                Variables.eosAccount,
                account_name,
                TypePublicKey.from( new EosPublicKey(owner_key) ),
                TypePublicKey.from( new EosPublicKey(active_key) ));
        SignedTransaction unsigned_tx = EosTxUtils.createTransaction(
                Variables.SYSTEM_CONTRACT_NAME_EOS,
                newAccountData.getActionName(),
                newAccountData.getAsHex(),
                EosTxUtils.getActivePermission(newAccountData.getCreatorName()),
                chain_info
        );

        //buy ram
        EosBuyRamBytes buyRamBytes = new EosBuyRamBytes(
                newAccountData.getCreatorName(),
                account_name,
                Variables.SYSTEM_CONTRACT_BUYRAM_BYSIZE);
        unsigned_tx = EosTxUtils.addAction(unsigned_tx,
                Variables.SYSTEM_CONTRACT_NAME_EOS,
                buyRamBytes.getActionName(),
                buyRamBytes.getAsHex(),
                EosTxUtils.getActivePermission(newAccountData.getCreatorName()),
                chain_info
        );

        //delegatebw
        EosDelegate delegate = new EosDelegate(
                newAccountData.getCreatorName(),
                account_name,
                Variables.SYSTEM_CONTRACT_DELEGATEBW_NET_BYEOS,
                Variables.SYSTEM_CONTRACT_DELEGATEBW_CPU_BYEOS,
                Variables.SYSTEM_CONTRACT_DELEGATEBW_ISTRANSFER);
        String delegate_string = new Gson().toJson(delegate);
        JsonToBinRequest json_obj1 = new JsonToBinRequest(
                Variables.SYSTEM_CONTRACT_NAME_EOS,
                delegate.getActionName(),
                delegate_string
        );
        String tmp_obj1 = new Gson().toJson(json_obj1);
        url = new StringBuilder();
        url.append(Variables.eosChainUrl).append("abi_json_to_bin");
        result= HttpClientUtils.post(
                url.toString(),
                tmp_obj1,
                "application/json",
                "UTF-8",
                Variables.conTimeOut,
                Variables.reqTimeOut);
        JsonToBinResponse bin_obj1 = new Gson().fromJson(result, JsonToBinResponse.class);
        unsigned_tx = EosTxUtils.addAction(unsigned_tx,
                Variables.SYSTEM_CONTRACT_NAME_EOS,
                delegate.getActionName(),
                bin_obj1.getBinargs(),
                EosTxUtils.getActivePermission(newAccountData.getCreatorName()),
                chain_info
        );

        //sign and pack
        SignedTransaction signed_tx = EosTxUtils.signTransaction(unsigned_tx, Variables.eosPrivateKey, chain_info.getChain_id());
        System.out.println("-------------->create account tx after sign:"+ new Gson().toJson(signed_tx));
        String tmp_obj = new Gson().toJson(new PackedTransaction(signed_tx));


        //send tx for new account
        url = new StringBuilder();
        url.append(Variables.eosChainUrl).append("push_transaction");
        result = HttpClientUtils.post(
                url.toString(),
                tmp_obj,
                "application/json",
                "UTF-8",
                Variables.conTimeOut,
                Variables.reqTimeOut
        );
        return EosErrorUtils.handleEosResponse(result, "create_account");
    }



    //create vip account with a invite code(the method call is verified by redis key, you can change other method)
    @CrossOrigin
    @PostMapping(value = "create_vip_account")
    public MessageResult create_vip_account(@RequestParam(value = "account_name", required = true) String account_name,
                                            @RequestParam(value = "active_key", required = true) String active_key,
                                            @RequestParam(value = "owner_key", required = true) String owner_key,
                                            @RequestParam(value = "nonce", required = true) String nonce,
                                            @RequestParam(value = "cpu", required = true) BigDecimal cpu,
                                            @RequestParam(value = "net", required = true) BigDecimal net,
                                            @RequestParam(value = "ram", required = true) BigDecimal ram) throws Exception {

        //参数检查
        if (nonce.length() != 32 || active_key.length() != 53  || owner_key.length() != 53) {
            throw new ExceptionsChain(ErrorCodeEnumChain.request_format_exception);
        }

        //对Personal服务器nonce验证
        String redis_key = Variables.redisKeyPrefixPersonalHead + Variables.redisKeyPrefixPersonalTailVipAccount;
        String redis_value = redisService.get(redis_key);
        if (!nonce.equals(redis_value) || redis_value == null) {
            throw new ExceptionsChain(ErrorCodeEnumChain.request_validation_exception);
        }

        //创建账号交易
        //get info
        StringBuilder url = new StringBuilder();
        url.append(Variables.eosChainUrl).append("get_info");
        String result = HttpClientUtils.get(url.toString(), "UTF-8");
        EosChainInfo chain_info = new Gson().fromJson(result, EosChainInfo.class);
        EosNewAccount newAccountData = new EosNewAccount(
                Variables.eosAccountVip,
                account_name,
                TypePublicKey.from( new EosPublicKey(owner_key) ),
                TypePublicKey.from( new EosPublicKey(active_key) ));
        SignedTransaction unsigned_tx = EosTxUtils.createTransaction(
                Variables.SYSTEM_CONTRACT_NAME_EOS,
                newAccountData.getActionName(),
                newAccountData.getAsHex(),
                EosTxUtils.getActivePermission(newAccountData.getCreatorName()),
                chain_info
        );


        //buyram
        EosBuyRam buyRam = new EosBuyRam(
                newAccountData.getCreatorName(),
                account_name,
                ram.setScale(4, RoundingMode.DOWN).toPlainString() + " EOS");
        String buyram_string =  new Gson().toJson(buyRam);

        JsonToBinRequest json_obj = new JsonToBinRequest(
                Variables.SYSTEM_CONTRACT_NAME_EOS,
                buyRam.getActionName(),
                buyram_string
        );
        String tmp_obj = new Gson().toJson(json_obj);
        url = new StringBuilder().append(Variables.eosChainUrl).append("abi_json_to_bin");
        result= HttpClientUtils.post(
                url.toString(),
                tmp_obj,
                "application/json",
                "UTF-8",
                Variables.conTimeOut,
                Variables.reqTimeOut);
        JsonToBinResponse bin_obj = new Gson().fromJson(result, JsonToBinResponse.class);
        unsigned_tx = EosTxUtils.addAction(unsigned_tx,
                Variables.SYSTEM_CONTRACT_NAME_EOS,
                buyRam.getActionName(),
                bin_obj.getBinargs(),
                EosTxUtils.getActivePermission(newAccountData.getCreatorName()),
                chain_info
        );



        //delegatebw
        EosDelegate delegate = new EosDelegate(
                newAccountData.getCreatorName(),
                account_name,
                net.setScale(4, RoundingMode.DOWN).toPlainString() + " EOS",
                cpu.setScale(4, RoundingMode.DOWN).toPlainString() + " EOS",
                Variables.SYSTEM_CONTRACT_DELEGATEBW_ISTRANSFER);
        String delegate_string = new Gson().toJson(delegate);
        JsonToBinRequest json_obj1 = new JsonToBinRequest(
                Variables.SYSTEM_CONTRACT_NAME_EOS,
                delegate.getActionName(),
                delegate_string
        );
        String tmp_obj1 = new Gson().toJson(json_obj1);
        url = new StringBuilder();
        url.append(Variables.eosChainUrl).append("abi_json_to_bin");
        result= HttpClientUtils.post(
                url.toString(),
                tmp_obj1,
                "application/json",
                "UTF-8",
                Variables.conTimeOut,
                Variables.reqTimeOut);
        JsonToBinResponse bin_obj1 = new Gson().fromJson(result, JsonToBinResponse.class);
        unsigned_tx = EosTxUtils.addAction(unsigned_tx,
                Variables.SYSTEM_CONTRACT_NAME_EOS,
                delegate.getActionName(),
                bin_obj1.getBinargs(),
                EosTxUtils.getActivePermission(newAccountData.getCreatorName()),
                chain_info
        );


        //sign tx for new account
        SignedTransaction signed_tx = EosTxUtils.signTransaction(unsigned_tx, Variables.eosPrivateKeyVip, chain_info.getChain_id());
        System.out.println("-------------->create account tx after sign:"+ new Gson().toJson(signed_tx));
        tmp_obj = new Gson().toJson(new PackedTransaction(signed_tx));


        //send tx for new account
        url = new StringBuilder();
        url.append(Variables.eosChainUrl).append("push_transaction");
        result = HttpClientUtils.post(
                url.toString(),
                tmp_obj,
                "application/json",
                "UTF-8",
                Variables.conTimeOut,
                Variables.reqTimeOut
        );
        return EosErrorUtils.handleEosResponse(result, "create_vip_account");
    }




}
