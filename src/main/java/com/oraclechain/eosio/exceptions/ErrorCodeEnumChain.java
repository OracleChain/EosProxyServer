package com.oraclechain.eosio.exceptions;


public enum ErrorCodeEnumChain  {


    /**
     * exceptions code based on c9b7a2472
     * chain_exception
     * |- chain_type_exception
     * |- fork_database_exception
     * |- block_validate_exception
     * |- transaction_exception
     * |- action_validate_exception
     * |- database_exception
     * |- wasm_exception
     * |- resource_exhausted_exception
     * |- misc_exception
     * |- missing_plugin_exception
     * |- wallet_exception
     * |- list_exception
     */

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


    private static final long serialVersionUID = -277825782729700L;

    private int msg_id;
    private String msg;

    private ErrorCodeEnumChain(int msg_id, String msg) {
        this.msg = msg;
        this.msg_id = msg_id;
    }

    public int getMsg_id() {
        return msg_id;
    }

    public String getMsg() {
        return msg;
    }

    public static String getMsgById(int msg_id) {

        for (ErrorCodeEnumChain b : ErrorCodeEnumChain.values()) {
            if (b.msg_id == msg_id) {
                return b.msg;
            }
        }

        return unknown_error_exception.getMsg();
    }

    public static ErrorCodeEnumChain getEnumById(int msg_id) {

        for (ErrorCodeEnumChain b : ErrorCodeEnumChain.values()) {
            if (b.msg_id == msg_id) {
                return b;
            }
        }

        return unknown_error_exception;
    }

}
