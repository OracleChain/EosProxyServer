[中文版](https://github.com/OracleChain/PocketEOS-ShieldServer/blob/master/README-cn.md)

# About

**PocketEOS-ShieldServer** is the backend server of PocketEOS(blackbox mode),which is developed by [OracleChain.io](https://oraclechain.io).

-------------------------------

# Menu
+ [Overview](#1)
+ [ENVIRONMENT](#2)
+ [Exception Handling](#3)
+ [Contract Error Code Specification](#4)
+ [Add Your Token Asset](#5)
+ [Server Transaction](#6)
+ [About OracleChain](#7)
+ [LICENSE](#8)

------------------------------

<h2 id="1">Overview</h2>

&emsp;&emsp;Pack EOS World in Mobile, Carry Blockchain Age with You！  
   
&emsp;&emsp;Now we opensourced our backend server too(apply to blackbox mode).      

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

>`port: 6379`

>`password: redis_passwd`

6. edit server transaction parameters in src/main/java/com/oraclechain/eosio/constants/Variables.java.

>`public static final String eosAccount = "tx_account_name";`

>`public static final String eosPrivateKey = "tx_private_key";`

7. Run it.

**Compile PocketEOS client from source code：**

Compiling Android client:

&emsp;1. install Android Studio 3.0以上 + jdk1.8 +gradle 4.1

&emsp;2. clone our git repository.

>`git clone --recurse-submodules https://github.com/OracleChain/PocketEOS-Android.git`

&emsp;3. change the configuration of backend server address according to your local PocketEOS-ShieldServer.

&emsp;4. import the project and run it.

Compiling IOS client:

&emsp;1. install XCODE 8.0

&emsp;2. clone our git repository:

>`git clone --recurse-submodules https://github.com/OracleChain/PocketEOS-IOS.git`

&emsp;3. change the configuration of backend server address according to your local PocketEOS-ShieldServer.

&emsp;4. import the project and run `command + R`

**CLIENT DOWNLOAD & TRY**

&emsp;[PocketEOS](https://pocketeos.com/)


**DEPENDENCYS:**

Our C code of ECDSA is based on micro-ecc.And OracleChain build a Publicly Verifiable Secret Sharing on secp256k1 which is published by Schoenmakers on crypto99 conference.

https://github.com/kmackay/micro-ecc

https://github.com/songgeng87/PubliclyVerifiableSecretSharing

You can also found it in our chainkit repository

https://github.com/OracleChain/chainkit

Our JAVA code of ECDSA and blockchain utility are based on the source code of [EOSCommander](https://github.com/plactal/EosCommander),thx for the PLACTAL.io team

**Any questions pls join our official Telegram Group below:**

中文群：https://t.me/OracleChainChatCN

ENGLISH GROUP：https://t.me/OracleChainChat

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


### Chain Layer Exceptions

When dealing with EOS blockchain rpc service, most of the exceptions were [CHAIN exceptions](https://github.com/EOSIO/eos/blob/master/libraries/chain/include/eosio/chain/exceptions.hpp), and we using it directly with some self defined code here.

    //self defined code
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

<h2 id="4">Contract Error Code Specification</h2>

### Contract Layer Exceptions Specification



------------------------------

<h2 id="5">Add Your Token Asset</h2>

### How to add your own token


------------------------------

<h2 id="6">Server Transaction</h2>

### Make server side transactions


------------------------------
<h2 id="7">About OracleChain</h2>

As the world’s first application built on an EOS ecosphere, OracleChain needs to meet the demands of the Oracle (oracle machine) ecosystem by efficiently linking blockchain technology services with various real-life scenarios, thereby delving into this immense tens of billions of dollars valuation market.

As a decentralized Oracle technology platform based on the EOS platform, the autonomous Proof-of-Reputation & Deposit mechanism is adopted and used as a fundamental service for other blockchain applications.In addition to Oracle services that provide real-world data to the blockchain, Oracle services that provide cross-chain data are also offered. Given that OracleChain can accomplish the functions of several prediction market applications, such as Augur and Gnosis, OracleChain can also support smart contract businesses that require high-frequency access to outside data in certain scenarios, such as Robo-Advisor.

OracleChain will nurture and serve those blockchain applications that change the real world. Our mission is to “Link Data, Link World,” with the aim of becoming the infrastructure linking the real world with the blockchain world.

By achieving intra-chain and extra-chain data connectivity, we aspire to create a service provisioning platform that can most efficiently gain access to extra-chain data in the future blockchain world.


<h2 id="8">LICENSE</h2>

**License**

Released under GNU/LGPL Version 3
