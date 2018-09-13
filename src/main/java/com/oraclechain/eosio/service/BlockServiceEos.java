package com.oraclechain.eosio.service;

import com.oraclechain.eosio.chain.PackedTransaction;
import com.oraclechain.eosio.dto.CoinMarketTicker;
import com.oraclechain.eosio.dto.UserAsset;

import java.math.BigDecimal;

public interface BlockServiceEos {


    PackedTransaction createTrx(String oracleKey,
                                  String contract,
                                  String action,
                                  String message) throws Exception;

    BigDecimal getBalance(String baseUrl,
                          String contractName,
                          String tokenSymbol,
                          String accountName) throws Exception;


    CoinMarketTicker getTicker(String coinmarket_id) throws Exception;


    UserAsset getUserAssetInfo(String baseUrl,
                               String accountName,
                               String contractName,
                               String tokenSymbol,
                               String coinmarket_id) throws Exception;
}
