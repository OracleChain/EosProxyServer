[ENGLISH VERSION](https://github.com/OracleChain/PocketEOS-ShieldServer/blob/master/README.md)

# 关于

**PocketEOS-ShieldServer**是一个PocketEOS的后端服务器（黑盒模式），由[OracleChain团队](https://oraclechain.io)研发。

-------------------------------

# 目录
+ [x] [简介](#1)
+ [x] [开发和使用环境](#2)
+ [x] [异常捕获](#3)
+ [ ] [合约异常规范](#4)
+ [x] [对接新币种](#5)
+ [ ] [自发交易](#6)
+ [x] [有关欧链](#7)
+ [x] [版权](#8)

------------------------------

<h2 id="1">简介</h2>      

&emsp;&emsp;口袋里的EOS世界大门，将区块链生活随身携带！ 

&emsp;&emsp;现在，我们把PocketEOS的后端代码也开源出来（适用于黑匣子模式），大家可以自行编译使用。

------------------------------
<h2 id="2">开发和使用环境</h2>

**如何从源码编译服务器：**

1. 准备一个redis服务器用于缓存第三方汇率

2. 安装IntelliJ IDEA + jdk1.8 + maven 4.0.0

3. 下载我们的repo
>`git clone https://github.com/OracleChain/PocketEOS-ShieldServer.git`

4. 使用IntelliJ IDEA导入到工程

5. 编辑服务器配置文件 src/main/resources/application.yml.

>`host: redis_server_ip`

>`port: 6379`

>`password: redis_passwd`

6. 编辑服务器主动交易相关参数 src/main/java/com/oraclechain/eosio/constants/Variables.java.

>`public static final String eosAccount = "tx_account_name";`

>`public static final String eosPrivateKey = "tx_private_key";`

7. 编译运行.

**如何从源码编译PocketEOS客户端：**

如何编译安卓客户端:

&emsp;1. 安装Android Studio 3.0以上 + jdk1.8 +gradle 4.1

&emsp;2. 下载repo

>`git clone --recurse-submodules https://github.com/OracleChain/PocketEOS-Android.git`

&emsp;3. 导入工程，并且根据你启动PocketEOS-ShieldServer地址，修改服务器配置文件.

&emsp;4. 编译运行.

如何编译IOS客户端:

&emsp;1. 安装 XCODE 8.0

&emsp;2. 下载repo:

>`git clone --recurse-submodules https://github.com/OracleChain/PocketEOS-IOS.git`

&emsp;3. 导入工程，并且根据你启动PocketEOS-ShieldServer地址，修改服务器配置文件.

&emsp;4. 使用`command + R` 编译运行


**依赖库:**

C语言版的椭圆曲线算法实现来自micro-ecc，欧链在椭圆曲线secp256k1上实现了Schoenmakers在crypto99上提出的Publicly Verifiable Secret Sharing

&emsp;https://github.com/kmackay/micro-ecc

&emsp;https://github.com/songgeng87/PubliclyVerifiableSecretSharing

&emsp;你也可以在我们的chainkit库中，找到工具类完整的封装。

&emsp;https://github.com/OracleChain/chainkit

JAVA语言版的椭圆曲线算法及blockchain工具类，来自于[EOSCommander](https://github.com/plactal/EosCommander)，感谢PLACTAL.io为社区的付出。

**相关问题反馈，请加欧链官方Telegram群组:**

中文群：https://t.me/OracleChainChatCN

英文群：https://t.me/OracleChainChat

------------------------------

<h2 id="3">异常捕获</h2>

为了给客户端提供一个稳定使用环境、平滑的版本切换、统一的错误定位和提示，我们全局捕获了EOS RPC接口中的异常，并且针对各种错误接口调用进行了封装。

通过返回完全可靠的错误代码，客户端就可以根据欧链定制的错误代码集，进行国际化，并且和EOS升级前后版本进行兼容。

EOS代码中，异常主要分为三层：

第一层为FC layer，主要处理graphene FC工具类产生的异常。

第二层为CHAIN layer，主要处理EOS逻辑代码中的异常。

第三层为contract layer，可在合约中形成规范，除了给用户提供统一的错误代码外，还可定制一些适用于特定合约的特定错误代码。

### FC层异常

EOS的底层框架使用的是graphene，而graphene抛出的错误被统一定制到了[FC exceptions文件](https://github.com/EOSIO/fc/blob/df5a17ef0704d7dd96c444bfd9a70506bcfbc057/include/fc/exception/exception.hpp)中。

我们这里为了把错误码统一起来，对FC error code进行了统一偏移：

    unspecified_exception_code(3990000, "unspecified"),
    unhandled_exception_code(3990001, "unhandled 3rd party exceptions"), ///< for unhandled 3rd party exceptions
    timeout_exception_code(3990002, "Timeout"),
    file_not_found_exception_code(3990003, "File Not Found"),
    parse_error_exception_code(3990004, "Parse Error"),
    invalid_arg_exception_code(3990005, "Invalid Argument"),
    key_not_found_exception_code(3990006, "Key Not Found"),
    bad_cast_exception_code(3990007, "Bad Cast"),
    out_of_range_exception_code(3990008, "Out of Range"),
    canceled_exception_code(3990009, "Canceled"),
    assert_exception_code(3990010, "Assert Exception"),
    eof_exception_code(3990011, "End Of File"),
    std_exception_code(3990013, "STD Exception"),//self made
    invalid_operation_exception_code(3990014, "Invalid Operation"),
    unknown_host_exception_code(3990015, "Unknown Host"),
    null_optional_code(3990016, "null optional"),
    udt_error_code(3990017, "UDT exceptions"),
    aes_error_code(3990018, "AES exceptions"),
    overflow_code(3990019, "Integer Overflow"),
    underflow_code(3990020, "Integer Underflow"),
    divide_by_zero_code(3990021, "Integer Divide By Zero");
    
### 链层异常


EOS中的主要错误都是CHAIN exception，这里我们对错误代码进行了直接[引用](https://github.com/EOSIO/eos/blob/master/libraries/chain/include/eosio/chain/exceptions.hpp)


    //自定义区
    unknown_error_exception(3900000, "server error, please try again later"),
    unknown_market_id_exception(3900001, "unknown coinmarket_id"),
    not_supported_exception(3900002, "API not currently supported"),


    //FROM libraries/chain/include/eosio/chain/exceptions.hpp
    chain_type_exception(3010000, "chain type exception"),
    name_type_exception(3010001, "Invalid name"),
    public_key_type_exception(3010002, "Invalid public key"),
    private_key_type_exception(3010003, "Invalid private key"),
    authority_type_exception(3010004, "Invalid authority"),
    action_type_exception(3010005, "Invalid action"),
    transaction_type_exception(3010006, "Invalid transaction"),
    abi_type_exception(3010007, "Invalid ABI"),
    abi_not_found_exception(3010008, "No ABI found"),
    block_id_type_exception(3010009, "Invalid block ID"),
    transaction_id_type_exception(3010010, "Invalid transaction ID"),
    packed_transaction_type_exception(3010011, "Invalid packed transaction"),
    asset_type_exception(3010012, "Invalid asset"),

    fork_database_exception(3020000, "fork database exception"),
    unlinkable_block_exception(3020001, "unlinkable block"),


    block_validate_exception(3030000, "block exception"),
    block_tx_output_exception(3030001, "transaction outputs in block do not match transaction outputs from applying block"),
    block_concurrency_exception(3030002, "block does not guarantee concurrent execution without conflicts"),
    block_lock_exception(3030003, "shard locks in block are incorrect or mal-formed"),
    block_resource_exhausted(3030004, "block exhausted allowed resources"),
    block_too_old_exception(3030005, "block is too old to push"),


    transaction_exception(3040000, "transaction exception"),
    tx_decompression_error(3040001, "Error decompressing transaction"),
    tx_no_action(3040002, "transaction should have at least one normal action"),
    tx_no_auths(3040003, "transaction should have at least one required authority"),
    cfa_irrelevant_auth(3040004, "context-free action should have no required authority"),
    expired_tx_exception(3040005, "Expired Transaction"),
    tx_exp_too_far_exception(3040006, "Transaction Expiration Too Far"),
    invalid_ref_block_exception(3040007, "Invalid Reference Block"),
    tx_duplicate(3040008, "duplicate transaction"),
    deferred_tx_duplicate(3040009, "duplicate deferred transaction"),


    action_validate_exception(3050000, "action exception"),
    account_name_exists_exception(3050001, "account name already exists"),
    invalid_action_args_exception(3050002, "Invalid Action Arguments"),
    eosio_assert_message_exception(3050003, "eosio_assert_message assertion failure"),
    eosio_assert_code_exception(3050004, "eosio_assert_code assertion failure"),


    database_exception(3060000, "database exception"),
    permission_query_exception(3060001, "Permission Query Exception"),
    account_query_exception(3060002, "Account Query Exception"),
    contract_table_query_exception(3060003, "Contract Table Query Exception"),
    contract_query_exception(3060004, "Contract Query Exception"),


    wasm_exception(3070000, "WASM Exception"),
    page_memory_error(3070001, "exceptions in WASM page memory"),
    wasm_execution_error(3070002, "Runtime Error Processing WASM"),
    wasm_serialization_error(3070003, "Serialization Error Processing WASM"),
    overlapping_memory_error(3070004, "memcpy with overlapping memory"),


    resource_exhausted_exception(3080000, "resource exhausted exception"),
    ram_usage_exceeded(3080001, "account using more than allotted RAM usage"),
    tx_net_usage_exceeded(3080002, "transaction exceeded the current network usage limit imposed on the transaction"),
    block_net_usage_exceeded(3080003, "transaction network usage is too much for the remaining allowable usage of the current block"),
    tx_cpu_usage_exceeded(3080004, "transaction exceeded the current CPU usage limit imposed on the transaction"),
    block_cpu_usage_exceeded(3080005, "transaction CPU usage is too much for the remaining allowable usage of the current block"),
    deadline_exception(3080006, "transaction took too long"),
    leeway_deadline_exception(3081001, "transaction reached the deadline set due to leeway on account CPU limits"),


    authorization_exception(3090000, "Authorization exception"),
    tx_duplicate_sig(3090001, "duplicate signature included"),
    tx_irrelevant_sig(3090002, "irrelevant signature included"),
    unsatisfied_authorization(3090003, "provided keys, permissions, and delays do not satisfy declared authorizations"),
    missing_auth_exception(3090004, "missing required authority"),
    irrelevant_auth_exception(3090005, "irrelevant authority included"),
    insufficient_delay_exception(3090006, "insufficient delay"),


    misc_exception(3100000, "Miscellaneous exception"),
    rate_limiting_state_inconsistent(3100001, "internal state is no longer consistent"),
    unknown_block_exception(3100002, "unknown block"),
    unknown_transaction_exception(3100003, "unknown transaction"),
    fixed_reversible_db_exception(3100004, "corrupted reversible block database was fixed"),
    extract_genesis_state_exception(3100005, "extracted genesis state from blocks.log"),


    missing_plugin_exception(3110000, "missing plugin exception"),
    missing_chain_api_plugin_exception(3110001, "Missing Chain API Plugin"),
    missing_wallet_api_plugin_exception(3110002, "Missing Wallet API Plugin"),
    missing_history_api_plugin_exception(3110003, "Missing History API Plugin"),
    missing_net_api_plugin_exception(3110004, "Missing Net API Plugin"),


    wallet_exception(3120000, "wallet exception"),
    wallet_exist_exception(3120001, "Wallet already exists"),
    wallet_nonexistent_exception(3120002, "Nonexistent wallet"),
    wallet_locked_exception(3120003, "Locked wallet"),
    wallet_missing_pub_key_exception(3120004, "Missing public key"),
    wallet_invalid_password_exception(3120005, "Invalid wallet password"),
    wallet_not_available_exception(3120006, "No available wallet"),
    wallet_unlocked_exception(3120007, "Already unlocked"),


    whitelist_blacklist_exception(3130000, "actor or contract whitelist/blacklist exception"),
    actor_whitelist_exception(3130001, "Authorizing actor of transaction is not on the whitelist"),
    actor_blacklist_exception(3130002, "Authorizing actor of transaction is on the blacklist"),
    contract_whitelist_exception(3130003, "Contract to execute is not on the whitelist"),
    contract_blacklist_exception(3130004, "Contract to execute is on the blacklist"),
    action_blacklist_exception(3130005, "Action to execute is on the blacklist"),
    key_blacklist_exception(3130006, "Public key in authority is on the blacklist");

------------------------------

<h2 id="4">合约异常规范</h2>

### 合约异常规范


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
    redis_key = Variables.redisKeyPrefixBlockchain+ Variables.redisKeyEosCoinmarketcapMid+ "eos";
    CoinMarketTicker coinMarketTicker_oct = redisService.get(redis_key, CoinMarketTicker.class);
    if(coinMarketTicker_oct == null){
        try{
            req_url.append(Variables.COINMARKETCAP_TICKER).append("eos").append("?convert=CNY");
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
    asset_info.setOct_balance(oct_balance.setScale(8, RoundingMode.DOWN).toPlainString());
    asset_info.setOct_balance_usd(oct_balance.multiply(oct_usd_price).setScale(8, RoundingMode.DOWN).toPlainString());
    asset_info.setOct_balance_cny(oct_balance.multiply(oct_cny_price).setScale(8, RoundingMode.DOWN).toPlainString());
    asset_info.setOct_price_usd(oct_usd_price.toString());
    asset_info.setOct_price_cny(oct_cny_price.toString());
    asset_info.setOct_price_change_in_24h(Double.toString(oct_price_change_in_24h));
    asset_info.setOct_market_cap_usd(coinMarketTicker.getMarket_cap_usd());
    asset_info.setOct_market_cap_cny(coinMarketTicker.getMarket_cap_cny());
    

------------------------------

<h2 id="6">自发交易</h2>

### 自发交易


------------------------------
<h2 id="7">有关欧链</h2>

OracleChain（欧链）作为全球第一个直面区块链生态Oracle（预言机）需求的基础应用，将区块链技术服务和现实生活中的多种需求场景直接高效对接，深耕这个百亿美金估值的巨大市场。

OracleChain是一个多区块链的去中心化Oracle技术平台，采用自主的PoRD机制，将现实世界数据引入区块链，并将此作为基础设施为其他区块链应用提供服务。
OracleChain将在区块链内提供现实世界数据的Oracle服务，同时还可以提供跨链数据的Oracle服务。基于OracleChain除了能实现Augur、Gnosis等预测市场（Prediction Market）应用的功能之外，还能支撑对链外数据有更高频率访问需求的智能合约业务，比如智能投顾等场景。

OracleChain将改变当前区块链应用的开发模式，建立全新的生态圈，服务于真正能改变现实世界的区块链应用。

OracleChain的使命是“让世界与区块链互联”，立志成为链接现实世界与区块链世界的基础设施，通过把外部数据引入区块链来实现链内链外的数据互通，OracleChian将是未来区块链世界中最高效的获取链外数据的服务提供平台。

<h2 id="8">版权</h2>

**License**

Released under GNU/LGPL Version 3
