
package com.oraclechain.eosio.chain;

import com.google.gson.annotations.Expose;

/**
 * Created by swapnibble on 2018-04-04.
 */
public class TransactionReceiptHeader {

//    enum status_enum {
//        executed  = 0, ///< succeed, no exceptions handler executed
//        soft_fail = 1, ///< objectively failed (not executed), exceptions handler executed
//        hard_fail = 2, ///< objectively failed and exceptions handler objectively failed thus no state change
//        delayed   = 3  ///< transaction delayed
//    };

    @Expose
    public String status ;

    @Expose
    public long max_cpu_usage_ms;   ///< total billed CPU usage (microseconds)

    @Expose
    public long max_net_usage_words;///<  total billed NET usage, so we can reconstruct resource state when skipping context free data... hard failures...
}

