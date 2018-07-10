package com.oraclechain.eosio.utils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.oraclechain.eosio.api.EosChainInfo;
import com.oraclechain.eosio.chain.Action;
import com.oraclechain.eosio.chain.SignedTransaction;
import com.oraclechain.eosio.constants.Variables;
import com.oraclechain.eosio.crypto.ec.EosPrivateKey;
import com.oraclechain.eosio.crypto.ec.EosPublicKey;
import com.oraclechain.eosio.dto.*;
import com.oraclechain.eosio.types.TypeAccountName;
import com.oraclechain.eosio.types.TypeChainId;

import java.util.ArrayList;
import java.util.List;

public class EosTxUtils {


    public static SignedTransaction createTransaction(String contract,
                                                      String actionName,
                                                      String dataAsHex,
                                                      String[] permissions,
                                                      EosChainInfo chainInfo) {
        Action action = new Action(contract, actionName);
        action.setAuthorization(permissions);
        action.setData( dataAsHex );


        SignedTransaction txn = new SignedTransaction();
        txn.addAction(action);
        txn.putSignatures(new ArrayList<>());


        if (null != chainInfo) {
            txn.setReferenceBlock(chainInfo.getHeadBlockId());
            txn.setExpiration(chainInfo.getTimeAfterHeadBlockTime(Variables.TX_EXPIRATION_IN_MILSEC));
        }

        return txn;
    }


    public static String[] getActivePermission(String accountName) {
        return new String[]{accountName + "@active"};
    }


    public static SignedTransaction signTransaction(SignedTransaction tx_for_sign, String oracle_key, String chainId) {

        //签名
        EosPrivateKey private_key = new EosPrivateKey(oracle_key);
        tx_for_sign.sign(private_key, new TypeChainId(chainId));


        return tx_for_sign;
    }

}
