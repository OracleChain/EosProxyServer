package com.oraclechain.eosio.exceptions;


public enum ErrorCodeEnumChain  {


    /**
     * exceptions code based on e87d245da
     *  chain_exception
     *   |- chain_type_exception
     *   |- fork_database_exception
     *   |- block_validate_exception
     *   |- transaction_exception
     *   |- action_validate_exception
     *   |- database_exception
     *   |- wasm_exception
     *   |- resource_exhausted_exception
     *   |- misc_exception
     *   |- plugin_exception
     *   |- wallet_exception
     *   |- whitelist_blacklist_exception
     *   |- controller_emit_signal_exception
     *   |- abi_exception
     *   |- contract_exception
     *   |- producer_exception
     *   |- reversible_blocks_exception
     *   |- block_log_exception
     *   |- resource_limit_exception
     *   |- mongo_db_exception
     *   |- contract_api_exception
     */

    //自定义区
    unknown_error_exception(3900000, "server error, please try again later"),
    unknown_market_id_exception(3900001, "unknown coinmarket_id"),
    not_supported_exception(3900002, "function not currently supported"),
    request_format_exception(3900003, "Request format error"),
    request_validation_exception(3900004, "Request validation error"),
    redis_connection_exception(3900005, "redis connection error"),
    mysql_connection_exception(3900006, "mysql connection error"),
    mq_execute_exception(3900007, "mq execute error, tx will try later"),


    //FROM libraries/chain/include/eosio/chain/exceptions.hpp
    name_type_exception( 3010001,"Invalid name" ),
    public_key_type_exception( 3010002,"Invalid public key" ),
    private_key_type_exception( 3010003,"Invalid private key" ),
    authority_type_exception( 3010004,"Invalid authority" ),
    action_type_exception( 3010005,"Invalid action" ),
    transaction_type_exception( 3010006,"Invalid transaction" ),
    abi_type_exception( 3010007,"Invalid ABI" ),
    block_id_type_exception( 3010008,"Invalid block ID" ),
    transaction_id_type_exception( 3010009,"Invalid transaction ID" ),
    packed_transaction_type_exception( 3010010,"Invalid packed transaction" ),
    asset_type_exception( 3010011,"Invalid asset" ),
    chain_id_type_exception( 3010012,"Invalid chain ID" ),
    fixed_key_type_exception( 3010013,"Invalid fixed key" ),
    symbol_type_exception( 3010014,"Invalid symbol" ),
    fork_database_exception( 3020000,"Fork database exception" ),
    fork_db_block_not_found( 3020001,"Block can not be found" ),
    block_validate_exception( 3030000,"Block exception" ),
    unlinkable_block_exception( 3030001,"Unlinkable block" ),
    block_tx_output_exception( 3030002,"Transaction outputs in block do not match transaction outputs from applying block" ),
    block_concurrency_exception( 3030003,"Block does not guarantee concurrent execution without conflicts" ),
    block_lock_exception( 3030004,"Shard locks in block are incorrect or mal-formed" ),
    block_resource_exhausted( 3030005,"Block exhausted allowed resources" ),
    block_too_old_exception( 3030006,"Block is too old to push" ),
    block_from_the_future( 3030007,"Block is from the future" ),
    wrong_signing_key( 3030008,"Block is not signed with expected key" ),
    wrong_producer( 3030009,"Block is not signed by expected producer" ),
    transaction_exception( 3040000,"Transaction exception" ),
    tx_decompression_error( 3040001,"Error decompressing transaction" ),
    tx_no_action( 3040002,"Transaction should have at least one normal action" ),
    tx_no_auths( 3040003,"Transaction should have at least one required authority" ),
    cfa_irrelevant_auth( 3040004,"Context-free action should have no required authority" ),
    expired_tx_exception( 3040005,"Expired Transaction" ),
    tx_exp_too_far_exception( 3040006,"Transaction Expiration Too Far" ),
    invalid_ref_block_exception( 3040007,"Invalid Reference Block" ),
    tx_duplicate( 3040008,"Duplicate transaction" ),
    deferred_tx_duplicate( 3040009,"Duplicate deferred transaction" ),
    cfa_inside_generated_tx( 3040010,"Context free action is not allowed inside generated transaction" ),
    tx_not_found( 3040011,"The transaction can not be found" ),
    too_many_tx_at_once( 3040012,"Pushing too many transactions at once" ),
    tx_too_big( 3040013,"Transaction is too big" ),
    unknown_transaction_compression( 3040014,"Unknown transaction compression" ),
    action_validate_exception( 3050000,"Action validate exception" ),
    account_name_exists_exception( 3050001,"Account name already exists" ),
    invalid_action_args_exception( 3050002,"Invalid Action Arguments" ),
    eosio_assert_message_exception( 3050003,"eosio_assert_message assertion failure" ),
    eosio_assert_code_exception( 3050004,"eosio_assert_code assertion failure" ),
    action_not_found_exception( 3050005,"Action can not be found" ),
    action_data_and_struct_mismatch( 3050006,"Mismatch between action data and its struct" ),
    unaccessible_api( 3050007,"Attempt to use unaccessible API" ),
    abort_called( 3050008,"Abort Called" ),
    inline_action_too_big( 3050009,"Inline Action exceeds maximum size limit" ),
    database_exception( 3060000,"Database exception" ),
    permission_query_exception( 3060001,"Permission Query Exception" ),
    account_query_exception( 3060002,"Account Query Exception" ),
    contract_table_query_exception( 3060003,"Contract Table Query Exception" ),
    contract_query_exception( 3060004,"Contract Query Exception" ),
    guard_exception( 3060100,"Guard Exception" ),
    database_guard_exception( 3060101,"Database usage is at unsafe levels" ),
    reversible_guard_exception( 3060102,"Reversible block log usage is at unsafe levels" ),
    wasm_exception( 3070000,"WASM Exception" ),
    page_memory_error( 3070001,"Error in WASM page memory" ),
    wasm_execution_error( 3070002,"Runtime Error Processing WASM" ),
    wasm_serialization_error( 3070003,"Serialization Error Processing WASM" ),
    overlapping_memory_error( 3070004,"memcpy with overlapping memory" ),
    binaryen_exception( 3070005,"binaryen exception" ),
    resource_exhausted_exception( 3080000,"Resource exhausted exception" ),
    ram_usage_exceeded( 3080001,"Account using more than allotted RAM usage" ),
    tx_net_usage_exceeded( 3080002,"Transaction exceeded the current network usage limit imposed on the transaction" ),
    block_net_usage_exceeded( 3080003,"Transaction network usage is too much for the remaining allowable usage of the current block" ),
    tx_cpu_usage_exceeded( 3080004,"Transaction exceeded the current CPU usage limit imposed on the transaction" ),
    block_cpu_usage_exceeded( 3080005,"Transaction CPU usage is too much for the remaining allowable usage of the current block" ),
    deadline_exception( 3080006,"Transaction took too long" ),
    greylist_net_usage_exceeded( 3080007,"Transaction exceeded the current greylisted account network usage limit" ),
    greylist_cpu_usage_exceeded( 3080008,"Transaction exceeded the current greylisted account CPU usage limit" ),
    leeway_deadline_exception( 3081001,"Transaction reached the deadline set due to leeway on account CPU limits" ),
    authorization_exception( 3090000,"Authorization exception" ),
    tx_duplicate_sig( 3090001,"Duplicate signature included" ),
    tx_irrelevant_sig( 3090002,"Irrelevant signature included" ),
    unsatisfied_authorization( 3090003,"Provided keys,permissions,and delays do not satisfy declared authorizations" ),
    missing_auth_exception( 3090004,"Missing required authority" ),
    irrelevant_auth_exception( 3090005,"Irrelevant authority included" ),
    insufficient_delay_exception( 3090006,"Insufficient delay" ),
    invalid_permission( 3090007,"Invalid Permission" ),
    unlinkable_min_permission_action( 3090008,"The action is not allowed to be linked with minimum permission" ),
    invalid_parent_permission( 3090009,"The parent permission is invalid" ),
    misc_exception( 3100000,"Miscellaneous exception" ),
    rate_limiting_state_inconsistent( 3100001,"Internal state is no longer consistent" ),
    unknown_block_exception( 3100002,"Unknown block" ),
    unknown_transaction_exception( 3100003,"Unknown transaction" ),
    fixed_reversible_db_exception( 3100004,"Corrupted reversible block database was fixed" ),
    extract_genesis_state_exception( 3100005,"Extracted genesis state from blocks.log" ),
    subjective_block_production_exception( 3100006,"Subjective exception thrown during block production" ),
    multiple_voter_info( 3100007,"Multiple voter info detected" ),
    unsupported_feature( 3100008,"Feature is currently unsupported" ),
    node_management_success( 3100009,"Node management operation successfully executed" ),
    plugin_exception( 3110000,"Plugin exception" ),
    missing_chain_api_plugin_exception( 3110001,"Missing Chain API Plugin" ),
    missing_wallet_api_plugin_exception( 3110002,"Missing Wallet API Plugin" ),
    missing_history_api_plugin_exception( 3110003,"Missing History API Plugin" ),
    missing_net_api_plugin_exception( 3110004,"Missing Net API Plugin" ),
    missing_chain_plugin_exception( 3110005,"Missing Chain Plugin" ),
    plugin_config_exception( 3110006,"Incorrect plugin configuration" ),
    wallet_exception( 3120000,"Wallet exception" ),
    wallet_exist_exception( 3120001,"Wallet already exists" ),
    wallet_nonexistent_exception( 3120002,"Nonexistent wallet" ),
    wallet_locked_exception( 3120003,"Locked wallet" ),
    wallet_missing_pub_key_exception( 3120004,"Missing public key" ),
    wallet_invalid_password_exception( 3120005,"Invalid wallet password" ),
    wallet_not_available_exception( 3120006,"No available wallet" ),
    wallet_unlocked_exception( 3120007,"Already unlocked" ),
    key_exist_exception( 3120008,"Key already exists" ),
    key_nonexistent_exception( 3120009,"Nonexistent key" ),
    unsupported_key_type_exception( 3120010,"Unsupported key type" ),
    invalid_lock_timeout_exception( 3120011,"Wallet lock timeout is invalid" ),
    secure_enclave_exception( 3120012,"Secure Enclave Exception" ),
    whitelist_blacklist_exception( 3130000,"Actor or contract whitelist/blacklist exception" ),
    actor_whitelist_exception( 3130001,"Authorizing actor of transaction is not on the whitelist" ),
    actor_blacklist_exception( 3130002,"Authorizing actor of transaction is on the blacklist" ),
    contract_whitelist_exception( 3130003,"Contract to execute is not on the whitelist" ),
    contract_blacklist_exception( 3130004,"Contract to execute is on the blacklist" ),
    action_blacklist_exception( 3130005,"Action to execute is on the blacklist" ),
    key_blacklist_exception( 3130006,"Public key in authority is on the blacklist" ),
    controller_emit_signal_exception( 3140000,"Exceptions that are allowed to bubble out of emit calls in controller" ),
    checkpoint_exception( 3140001,"Block does not match checkpoint" ),
    abi_exception( 3015000,"ABI exception" ),
    abi_not_found_exception( 3015001,"No ABI found" ),
    invalid_ricardian_clause_exception( 3015002,"Invalid Ricardian Clause" ),
    invalid_ricardian_action_exception( 3015003,"Invalid Ricardian Action" ),
    invalid_type_inside_abi( 3015004,"The type defined in the ABI is invalid" ), // Not to be confused with abi_type_exception
    duplicate_abi_type_def_exception( 3015005,"Duplicate type definition in the ABI" ),
    duplicate_abi_struct_def_exception( 3015006,"Duplicate struct definition in the ABI" ),
    duplicate_abi_action_def_exception( 3015007,"Duplicate action definition in the ABI" ),
    duplicate_abi_table_def_exception( 3015008,"Duplicate table definition in the ABI" ),
    duplicate_abi_err_msg_def_exception( 3015009,"Duplicate error message definition in the ABI" ),
    abi_serialization_deadline_exception( 3015010,"ABI serialization time has exceeded the deadline" ),
    abi_recursion_depth_exception( 3015011,"ABI recursive definition has exceeded the max recursion depth" ),
    abi_circular_def_exception( 3015012,"Circular definition is detected in the ABI" ),
    unpack_exception( 3015013,"Unpack data exception" ),
    pack_exception( 3015014,"Pack data exception" ),
    duplicate_abi_variant_def_exception( 3015015,"Duplicate variant definition in the ABI" ),
    unsupported_abi_version_exception( 3015016,"ABI has an unsupported version" ),
    contract_exception( 3160000,"Contract exception" ),
    invalid_table_payer( 3160001,"The payer of the table data is invalid" ),
    table_access_violation( 3160002,"Table access violation" ),
    invalid_table_iterator( 3160003,"Invalid table iterator" ),
    table_not_in_cache( 3160004,"Table can not be found inside the cache" ),
    table_operation_not_permitted( 3160005,"The table operation is not allowed" ),
    invalid_contract_vm_type( 3160006,"Invalid contract vm type" ),
    invalid_contract_vm_version( 3160007,"Invalid contract vm version" ),
    set_exact_code( 3160008,"Contract is already running this version of code" ),
    wast_file_not_found( 3160009,"No wast file found" ),
    abi_file_not_found( 3160010,"No abi file found" ),
    producer_exception( 3170000,"Producer exception" ),
    producer_priv_key_not_found( 3170001,"Producer private key is not available" ),
    missing_pending_block_state( 3170002,"Pending block state is missing" ),
    producer_double_confirm( 3170003,"Producer is double confirming known range" ),
    producer_schedule_exception( 3170004,"Producer schedule exception" ),
    producer_not_in_schedule( 3170006,"The producer is not part of current schedule" ),
    reversible_blocks_exception( 3180000,"Reversible Blocks exception" ),
    invalid_reversible_blocks_dir( 3180001,"Invalid reversible blocks directory" ),
    reversible_blocks_backup_dir_exist( 3180002,"Backup directory for reversible blocks already existg" ),
    gap_in_reversible_blocks_db( 3180003,"Gap in the reversible blocks database" ),
    block_log_exception( 3190000,"Block log exception" ),
    block_log_unsupported_version( 3190001,"unsupported version of block log" ),
    block_log_append_fail( 3190002,"fail to append block to the block log" ),
    block_log_not_found( 3190003,"block log can not be found" ),
    block_log_backup_dir_exist( 3190004,"block log backup dir already exists" ),
    http_exception( 3200000,"http exception" ),
    invalid_http_client_root_cert( 3200001,"invalid http client root certificate" ),
    invalid_http_response( 3200002,"invalid http response" ),
    resolved_to_multiple_ports( 3200003,"service resolved to multiple ports" ),
    fail_to_resolve_host( 3200004,"fail to resolve host" ),
    http_request_fail( 3200005,"http request fail" ),
    invalid_http_request( 3200006,"invalid http request" ),
    resource_limit_exception( 3210000,"Resource limit exception" ),
    mongo_db_exception( 3220000,"Mongo DB exception" ),
    mongo_db_insert_fail( 3220001,"Fail to insert new data to Mongo DB" ),
    mongo_db_update_fail( 3220002,"Fail to update existing data in Mongo DB" ),
    contract_api_exception( 3230000,"Contract API exception" ),
    crypto_api_exception( 3230001,"Crypto API Exception" ),
    db_api_exception( 3230002,"Database API Exception" ),
    arithmetic_exception( 3230003,"Arithmetic Exception" );



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
