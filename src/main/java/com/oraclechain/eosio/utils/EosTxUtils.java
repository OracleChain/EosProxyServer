package com.oraclechain.eosio.utils;

import com.oraclechain.eosio.eosApi.EosChainInfo;
import com.oraclechain.eosio.chain.Action;
import com.oraclechain.eosio.chain.SignedTransaction;
import com.oraclechain.eosio.constants.Variables;
import com.oraclechain.eosio.crypto.ec.EosPrivateKey;
import com.oraclechain.eosio.eosTypes.TypeChainId;

import java.util.ArrayList;

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

        //sign
        EosPrivateKey private_key = new EosPrivateKey(oracle_key);
        tx_for_sign.sign(private_key, new TypeChainId(chainId));


        return tx_for_sign;
    }

}
