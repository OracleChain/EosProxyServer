package com.oraclechain.eosio.service;

import com.oraclechain.eosio.chain.PackedTransaction;
import com.oraclechain.eosio.dto.CoinMarketTicker;
import com.oraclechain.eosio.dto.UserAssetInfo;

import java.math.BigDecimal;

public interface BlockServiceEos {


    PackedTransaction pushMessage(String baseUrl,
                                         String oracleKey,
                                         String contract,
                                         String action,
                                         String message,
                                         String [] permissions) throws Exception;

    BigDecimal getBalance(String baseUrl,
                          String contractName,
                          String tokenSymbol,
                          String accountName) throws Exception;


}
