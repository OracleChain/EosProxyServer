package com.oraclechain.eosio.constants;

public final class Variables {

    //EOS TOKEN
    public static final String EOS_TOKEN_CONTRACT_NAME = "eosio.token";
    public static final String EOS_TOKEN_CONTRACT_SYMBOL = "EOS";
    public static final String SYSTEM_CONTRACT_NAME_EOS = "eosio";
    //OCT TOKEN
    public static final String OCT_TOKEN_CONTRACT_NAME = "octtothemoon";
    public static final String OCT_TOKEN_CONTRACT_SYMBOL = "OCT";

    //区块链常量
    public static final Integer TX_EXPIRATION_IN_MILSEC = 30000;


    //THIRD PARTY
    public static final String COINMARKETCAP_ID_EOS = "eos";
    public static final String COINMARKETCAP_TICKER = "https://api.coinmarketcap.com/v1/ticker/";
    public static final String COINMARKETCAP_SPARKLINES_EOS = "https://s2.coinmarketcap.com/generated/sparklines/web/7d/usd/1765.png";
    public static final String COINMARKETCAP_SPARKLINES_OCT = "https://s2.coinmarketcap.com/generated/sparklines/web/7d/usd/1838.png";
    public static final String COINMARKETCAP_SPARKLINES_IQ = "https://s2.coinmarketcap.com/generated/sparklines/web/7d/usd/2930.png";
    public static final String NEWDEX_TICKER = "https://api.newdex.io/v1/ticker";


    //JAVA
    public static final Integer conTimeOut = 3000;
    public static final Integer reqTimeOut = 5000;
    public static final Integer moneyPrecision = 4;


    //REDIS
    public static final Integer redisCacheTimeout = 60 * 30;  //min
    public static final String redisKeyPrefixBlockchain = "Blockchain:";
    public static final String redisKeyPrefixPersonalHead = "Personal:";
    public static final String redisKeyEosCoinmarketcapMid = "ExchangeRate";
    public static final String redisKeyEosNewdexMid = "NewdexTicker";
    public static final String redisKeyPrefixPersonalTailFreeAccount = "xxx";
    public static final String redisKeyPrefixPersonalTailVipAccount = "xxx";

    //chain
    public static final String eosChainUrl = "http://api.oraclechain.io/v1/chain/";
    public static final String eosHistoryUrl = "http://api.oraclechain.io/v1/history/";
    public static final String eosAccount = "octgenerator";
    public static final String eosPrivateKey = "xxx";
    public static final String eosAccountVip = "vipgenerator";
    public static final String eosPrivateKeyVip = "xxx";

    public static final Integer SYSTEM_CONTRACT_BUYRAM_BYSIZE = 4000;
    public static final String SYSTEM_CONTRACT_DELEGATEBW_NET_BYEOS = "0.0050 EOS";
    public static final String SYSTEM_CONTRACT_DELEGATEBW_CPU_BYEOS = "0.0400 EOS";
    public static final Boolean SYSTEM_CONTRACT_DELEGATEBW_ISTRANSFER = false;

}
