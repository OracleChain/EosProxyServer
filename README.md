[中文版](https://github.com/OracleChain/PocketEOS-ShieldServer/blob/master/README-cn.md)

# About

**PocketEOS-ShieldServer** is the backend server of PocketEOS(blackbox mode),which is developed by [OracleChain.io](https://oraclechain.io).

-------------------------------

# Menu
+ [x] [Overview](#1)
+ [x] [ENVIRONMENT](#2)
+ [x] [Exception Handling](#3)
+ [x] [Add Your Token Asset](#4)
+ [x] [Server Transaction](#5)
+ [x] [About OracleChain](#6)
+ [x] [LICENSE](#7)
+ [x] [THANKS](#8)

------------------------------

<h2 id="1">Overview</h2>

Pack EOS World in Mobile, Carry Blockchain Age with You！  
   
Now we opensourced our backend server too(apply to blackbox mode).

What we provide in this project：

&emsp;`1.Realtime market rates: Here we request the coinmarketcap for certain token rates. The rates were based on multiple exchange market.`

&emsp;`2.General EOS error code specification without importing EOSIO code base(EOSJS/EOS C++): We provide a gobal standard error code based on eos source code,which could make better user experience, and educate more about what really happened when error occurred.`

&emsp;`3.Server can pack you own transactions as you want, apply to scenes like:new account creating, transfer for operator, airdrop, all kinds of contract call, and etc.`

------------------------------
<h2 id="2">ENVIRONMENT</h2>

**Compile ShieldServer from source code：**

1. prepare a redis server for data cache of token rates

2. install IntelliJ IDEA + jdk1.8 + maven 4.0.0

3. clone our git repository.
>`git clone https://github.com/OracleChain/PocketEOS-ShieldServer.git`

4. import the project with IntelliJ IDEA

5. edit redis server configuration in src/main/resources/application.yml.

>`host: redis_server_ip`

>`port: redis_server_port`

>`password: redis_passwd`

6. edit server transaction parameters in src/main/java/com/oraclechain/eosio/constants/Variables.java.

>`public static final String eosAccount = "tx_account_name";`

>`public static final String eosPrivateKey = "tx_private_key";`

7. Run it.

**Compile PocketEOS client from source code：**

Compiling Android client:

&emsp;1. install Android Studio 3.0以上 + jdk1.8 +gradle 4.1

&emsp;2. clone our [git repository](https://github.com/OracleChain/PocketEOS-Android).

>`git clone --recurse-submodules https://github.com/OracleChain/PocketEOS-Android.git`

&emsp;3. import the project and change the configuration of backend server address according to your local PocketEOS-ShieldServer.

&emsp;4. run it.

Compiling IOS client:

&emsp;1. install XCODE 8.0

&emsp;2. clone our [git repository](https://github.com/OracleChain/PocketEOS-IOS):

>`git clone --recurse-submodules https://github.com/OracleChain/PocketEOS-IOS.git`

&emsp;3. import the project and change the configuration of backend server address according to your local PocketEOS-ShieldServer.

&emsp;4. run `command + R`

------------------------------

<h2 id="3">Exception Handling</h2>

We provide client a more stable environment, smooth version upgrading, universal error exceptions with resolving all EOS exceptions into one standard.

With the univeral error exceptions, you can make more internationalized error tips for users.

In EOS code base, the exceptions were handled in three layers.

1.the FC layer, handling the graphene tool exceptions.

2.the CHAIN layer, handling EOS chain logic exceptions.

3.In the CONTRACT layer, we can standardize a general error code specification in your contract code. And it's optional to add additional error code handling specific situations.

### FC Layer Exceptions

EOS is based on grephene framework, which defines the error code of FC layer in [FC expceptions file](https://github.com/EOSIO/fc/blob/df5a17ef0704d7dd96c444bfd9a70506bcfbc057/include/fc/exception/exception.hpp).

We unified the error code with a offset of 3990000.
![](https://github.com/OracleChain/PocketEOS-ShieldServer/raw/master/screenshots/shieldserver.02.png)

### Chain Layer Exceptions

When dealing with EOS blockchain rpc service, most of the exceptions were [CHAIN exceptions](https://github.com/EOSIO/eos/blob/master/libraries/chain/include/eosio/chain/exceptions.hpp), and we using it directly with some self defined code here.
![](https://github.com/OracleChain/PocketEOS-ShieldServer/raw/master/screenshots/shieldserver.01.png)

------------------------------

<h2 id="4">Add Your Token Asset</h2>

### How to add your own token

Let's take OracleChainToken contract for example, the contract address is "octtothemoon", with our symbol "OCT"

1.modify dto/AccountAssetInfo.java entity, which defines the response of assets query：

    private String oct_balance;
    private String oct_balance_usd;
    private String oct_balance_cny;
    private String oct_price_usd;
    private String oct_price_cny;
    private String oct_price_change_in_24h;
    private String oct_market_cap_usd;
    private String oct_market_cap_cny;


2.modify controller/QueryTabController.java, the get_account_asset interface, to make adaptions to new tokens：
        
    //get token balance for the user "octgenerator"
    BigDecimal oct_balance = blockServiceEos.getBalance(
        Variables.eosChainUrl,
        "octtothemoon",
        "OCT",
        "octgenerator");

    //resolve third party token rates with fiat currency, and we store the rate in redis(the way of cache is pretty simple, which should be improved in production env.)
    redis_key = Variables.redisKeyPrefixBlockchain+ Variables.redisKeyEosCoinmarketcapMid+ "oraclechain";
    CoinMarketTicker coinMarketTicker_oct = redisService.get(redis_key, CoinMarketTicker.class);
    if(coinMarketTicker_oct == null){
        try{
            req_url.append(Variables.COINMARKETCAP_TICKER).append("oraclechain").append("?convert=CNY");
            result = HttpClientUtils.get(req_url.toString(), "UTF-8");
            coinMarketTicker_oct  = JSON.parseArray(result, CoinMarketTicker.class).get(0);
            redisService.set(redis_key, coinMarketTicker_oct, Variables.redisCacheTimeout);
        }
        catch (Exception e)
        {
            throw new ExceptionsChain(ErrorCodeEnumChain.unknown_market_id_exception);
        }
    }
    
    //finally we send the token info with AccountAssetInfo entity
    AccountAssetInfo asset_info= new AccountAssetInfo();
    BigDecimal oct_usd_price = new BigDecimal(coinMarketTicker.getPrice_usd());
    BigDecimal oct_cny_price = new BigDecimal(coinMarketTicker.getPrice_cny());
    double oct_price_change_in_24h = Double.valueOf(coinMarketTicker.getPercent_change_24h());//.doubleValue();
    asset_info.setOct_balance(oct_balance.setScale(Variables.precision, RoundingMode.DOWN).toPlainString());
    asset_info.setOct_balance_usd(oct_balance.multiply(oct_usd_price).setScale(Variables.precision, RoundingMode.DOWN).toPlainString());
    asset_info.setOct_balance_cny(oct_balance.multiply(oct_cny_price).setScale(Variables.precision, RoundingMode.DOWN).toPlainString());
    asset_info.setOct_price_usd(oct_usd_price.toString());
    asset_info.setOct_price_cny(oct_cny_price.toString());
    asset_info.setOct_price_change_in_24h(Double.toString(oct_price_change_in_24h));
    asset_info.setOct_market_cap_usd(coinMarketTicker.getMarket_cap_usd());
    asset_info.setOct_market_cap_cny(coinMarketTicker.getMarket_cap_cny());
    

------------------------------

<h2 id="5">Server Transaction</h2>

### Make server side transactions




------------------------------
<h2 id="6">About OracleChain</h2>

As the world’s first application built on an EOS ecosphere, OracleChain needs to meet the demands of the Oracle (oracle machine) ecosystem by efficiently linking blockchain technology services with various real-life scenarios, thereby delving into this immense tens of billions of dollars valuation market.

As a decentralized Oracle technology platform based on the EOS platform, the autonomous Proof-of-Reputation & Deposit mechanism is adopted and used as a fundamental service for other blockchain applications.In addition to Oracle services that provide real-world data to the blockchain, Oracle services that provide cross-chain data are also offered. Given that OracleChain can accomplish the functions of several prediction market applications, such as Augur and Gnosis, OracleChain can also support smart contract businesses that require high-frequency access to outside data in certain scenarios, such as Robo-Advisor.

OracleChain will nurture and serve those blockchain applications that change the real world. Our mission is to “Link Data, Link World,” with the aim of becoming the infrastructure linking the real world with the blockchain world.

By achieving intra-chain and extra-chain data connectivity, we aspire to create a service provisioning platform that can most efficiently gain access to extra-chain data in the future blockchain world.


<h2 id="7">LICENSE</h2>

Released under GNU/LGPL Version 3

<h2 id="8">THANKS</h2>

ECDSA and chain utility are based on the source code of [EOSCommander](https://github.com/playerone-id/EosCommander),thx for the contribution of PLAYERONE.ID team!

If you found this project useful, vote us, vote oraclegogogo!!!😄
