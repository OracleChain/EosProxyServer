package com.oraclechain.eosio.service;

import com.oraclechain.eosio.dto.ExchangeRate;
import com.oraclechain.eosio.dto.UserAsset;

import java.math.BigDecimal;

public interface BlockServiceEos {

    ExchangeRate getBaseTicker(String coinmarket_id) throws Exception;

    ExchangeRate getRate(String external_id) throws Exception;

    BigDecimal getBalance(String baseUrl,
                          String contractName,
                          String tokenSymbol,
                          String accountName) throws Exception;

    UserAsset getUserAssetInfo(String baseUrl,
                               String accountName,
                               String contractName,
                               String tokenSymbol,
                               String coinmarket_id) throws Exception;
}
