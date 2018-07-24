package com.oraclechain.eosio.constants;

public final class Variables {

    //TOKEN
    public static final String EOS_TOKEN_CONTRACT_NAME = "eosio.token";
    public static final String EOS_TOKEN_CONTRACT_SYMBOL = "EOS";
    public static final String OCT_TOKEN_CONTRACT_NAME = "octtothemoon";
    public static final String OCT_TOKEN_CONTRACT_SYMBOL = "OCT";

    //区块链常量
    public static final Integer TX_EXPIRATION_IN_MILSEC = 30000;


    //THIRD PARTY
    public static final String COINMARKETCAP_TICKER = "https://api.coinmarketcap.com/v1/ticker/";
    public static final String COINMARKETCAP_SPARKLINES_EOS = "https://s2.coinmarketcap.com/generated/sparklines/web/7d/usd/1765.png";
    public static final String COINMARKETCAP_SPARKLINES_OCT = "https://s2.coinmarketcap.com/generated/sparklines/web/7d/usd/1838.png";


    //JAVA
    public static final Integer conTimeOut = 3000;
    public static final Integer reqTimeOut = 5000;
    public static final Integer precision = 4;


    //REDIS
    public static final Integer redisCacheTimeout = 60 * 30;  //min
    public static final String redisKeyPrefixBlockchain = "Blockchain";
    public static final String redisKeyEosCoinmarketcapMid = "CoinMarketTicker";


    //chain
    public static final String eosChainUrl = "http://dns1-rpc.oraclechain.io:58888/v1/chain/";
    public static final String eosAccountUrl = "http://dns1-rpc.oraclechain.io:58888/v1/account_history/";
    public static final String eosAccount = "tx_account_name";
    public static final String eosPrivateKey = "tx_private_key";

}
