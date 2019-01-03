[ENGLISH VERSION](https://github.com/OracleChain/EosProxyServer/blob/master/README.md)

# 关于

**EosProxyServer**是PocketEOS的**完整的**后端服务器，由[OracleChain团队](https://oraclechain.io)研发。

-------------------------------

# 目录
+ [简介](#1)
+ [开发和使用环境](#2)
+ [API使用](#3)
+ [异常捕获](#4)
+ [对接新币种](#5)
+ [创建自发交易](#6)
+ [有关欧链](#7)
+ [版权](#8)

------------------------------

<h2 id="1">简介</h2>      

本仓库为PocketEOS的完整后端代码，大家可以自行编译使用。

本项目提供什么：

&emsp;`1.接入实时汇率：这里我们主要参考了coinmarketcap，该网站对接了多个交易所，相对来说比较值得信赖；`

&emsp;`2.通用EOS错误代码规范：提供基于错误码的国际化标准，可以较大程度提升用户体验，同时便于教育用户了解EOS底层发生了什么；`

&emsp;`3.服务器中配置公司运营账号，通过接口验证后，发出交易，为用户创建账号。`

&emsp;`4.服务器可以发起交易：可以实现比如创建用户、运营转账、空投、各种合约调用等。`


------------------------------
<h2 id="2">开发和使用环境</h2>

**如何从源码编译EosProxyServer服务器：**

1. 准备一个redis服务器用于缓存第三方汇率

2. 安装IntelliJ IDEA + jdk1.8 + maven 4.0.0

3. 下载
&emsp;`git clone https://github.com/OracleChain/EosProxyServer.git`

4. 使用IntelliJ IDEA导入到工程

5. 编辑服务器配置文件 src/main/resources/application.yml.

&emsp;`host: redis_server_ip`

&emsp;`port: 6379`

&emsp;`password: redis_passwd`

6. 编辑服务器主动交易相关参数 src/main/java/com/oraclechain/eosio/constants/Variables.java.

&emsp;`public static final String eosAccount = "tx_account_name";`

&emsp;`public static final String eosPrivateKey = "tx_private_key";`

7. 编译运行.

8. 可以使用POSTMAN或浏览器进行测试：

![](https://raw.githubusercontent.com/OracleChain/EosProxyServer/master/screenshots/get_info.png)


------------------------------

<h2 id="3">API使用</h2>

------------------------------

<h2 id="4">异常捕获</h2>

为了给客户端提供一个稳定使用环境、平滑的版本切换、统一的错误定位和提示，我们全局捕获了EOS RPC接口中的异常，并且针对各种错误接口调用进行了封装。

通过返回完全可靠的错误代码，客户端就可以根据欧链定制的错误代码集，进行国际化，并且和EOS升级前后版本进行兼容。

EOS代码中，异常主要分为三层：

第一层为FC layer，主要处理graphene FC工具类产生的异常。

第二层为CHAIN layer，主要处理EOS逻辑代码中的异常。

第三层为contract layer，可在合约中形成规范，除了给用户提供统一的错误代码外，还可定制一些适用于特定合约的特定错误代码。

https://docs.google.com/spreadsheets/d/1uHeNDLnCVygqYK-V01CFANuxUwgRkNkrmeLm9MLqu9c/edit?usp=sharing

### FC层异常

EOS的底层框架使用的是graphene，而graphene抛出的错误被统一定制到了[FC exceptions文件](https://github.com/EOSIO/fc/blob/df5a17ef0704d7dd96c444bfd9a70506bcfbc057/include/fc/exception/exception.hpp)中。

我们这里为了把错误码统一起来，对FC error code进行了统一偏移.

![](https://github.com/OracleChain/EosProxyServer/raw/master/screenshots/shieldserver.02.png)
    
### 链层异常


EOS中的主要错误都是CHAIN exception，这里我们对错误代码进行了直接[引用](https://github.com/EOSIO/eos/blob/master/libraries/chain/include/eosio/chain/exceptions.hpp)

![](https://github.com/OracleChain/EosProxyServer/raw/master/screenshots/shieldserver.01.png)

------------------------------

<h2 id="5">对接新币种</h2>

### 对接新币种

下面以oraclechain token为例，合约地址为octtothemoon，货币符号为OCT

1.修改dto/AccountAssetInfo.java实体，用于下面的接口返回：

    private String oct_balance;
    private String oct_balance_usd;
    private String oct_balance_cny;
    private String oct_price_usd;
    private String oct_price_cny;
    private String oct_price_change_in_24h;
    private String oct_market_cap_usd;
    private String oct_market_cap_cny;


2.修改controller/QueryTabController.java中的接口get_account_asset，以支持返回更多币种和相应市场汇率等参数：
        
    //获取用户余额，此处传入
    BigDecimal oct_balance = blockServiceEos.getBalance(
        Variables.eosChainUrl,
        "octtothemoon",
        "OCT",
        "octgenerator");

    //获取第三方汇率，并且加入缓存（此处缓存的刷新方式比较简单粗暴，大家可以在流量更大之后修改）
    redis_key = Variables.redisKeyPrefixBlockchain+ Variables.redisKeyEosCoinmarketcapMid+ "oct";
    CoinMarketTicker coinMarketTicker_oct = redisService.get(redis_key, CoinMarketTicker.class);
    if(coinMarketTicker_oct == null){
        try{
            req_url.append(Variables.COINMARKETCAP_TICKER).append("oct").append("?convert=CNY");
            result = HttpClientUtils.get(req_url.toString(), "UTF-8");
            coinMarketTicker_oct  = JSON.parseArray(result, CoinMarketTicker.class).get(0);
            redisService.set(redis_key, coinMarketTicker_oct, Variables.redisCacheTimeout);
        }
        catch (Exception e)
        {
            throw new ExceptionsChain(ErrorCodeEnumChain.unknown_market_id_exception);
        }
    }
    
    //接下来设置需要返回的用户余额
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

<h2 id="6">创建自发交易</h2>

### 自发交易

我们可以在src/main/java/com/oraclechain/eosio/constants/Variables.java中指定一个服务器自发交易的用户名和对应active私钥。

然后可以使用push_action这个接口发起交易请求。该交易可以是创建账户、转账等任意合约调用。


------------------------------
<h2 id="7">有关欧链</h2>

OracleChain（欧链）作为全球第一个直面区块链生态Oracle（预言机）需求的基础应用，将区块链技术服务和现实生活中的多种需求场景直接高效对接，深耕这个百亿美金估值的巨大市场。

OracleChain是一个多区块链的去中心化Oracle技术平台，采用自主的PoRD机制，将现实世界数据引入区块链，并将此作为基础设施为其他区块链应用提供服务。
OracleChain将在区块链内提供现实世界数据的Oracle服务，同时还可以提供跨链数据的Oracle服务。基于OracleChain除了能实现Augur、Gnosis等预测市场（Prediction Market）应用的功能之外，还能支撑对链外数据有更高频率访问需求的智能合约业务，比如智能投顾等场景。

OracleChain将改变当前区块链应用的开发模式，建立全新的生态圈，服务于真正能改变现实世界的区块链应用。

OracleChain的使命是“让世界与区块链互联”，立志成为链接现实世界与区块链世界的基础设施，通过把外部数据引入区块链来实现链内链外的数据互通，OracleChian将是未来区块链世界中最高效的获取链外数据的服务提供平台。

<h2 id="7">版权</h2>

发布于 GNU/LGPL Version 3 许可证书下


<h2 id="8">感谢</h2>

椭圆曲线算法及签名工具：[EOSCommander](https://github.com/plactal/EosCommander)，感谢PLAYERONE.ID团队的贡献。

如果您觉得我们的开源项目对您有帮助，请为oraclegogogo投上一票，谢谢。
