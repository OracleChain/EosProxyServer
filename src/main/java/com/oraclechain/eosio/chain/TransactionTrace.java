
package com.oraclechain.eosio.chain;

import com.google.gson.JsonElement;
import com.google.gson.annotations.Expose;

import java.util.List;

public class TransactionTrace {

    @Expose
    private String id;

    @Expose
    private TransactionReceiptHeader receipt;

    @Expose
    private long elapsed;

    @Expose
    private long net_usage; // uint64_t

    @Expose
    private boolean scheduled = false;

    @Expose
    private List<ActionTrace> action_traces;

    @Expose
    private JsonElement failed_dtrx_trace;

    @Expose
    private JsonElement except;

    @Override
    public String toString(){
        if ( receipt == null) {
            return "empty receipt";
        }

        String result = ": " + receipt.status;

        if ( receipt.max_net_usage_words < 0 ) {
            result +=  "<unknown>";
        }
        else {
            result += (receipt.max_net_usage_words * 8 );
        }
        result += " bytes ";


        if ( receipt.max_cpu_usage_ms < 0 ) {
            result +=  "<unknown>";
        }
        else {
            result += (receipt.max_net_usage_words * 8 );
        }
        result += " us\n";

        return result;
    }
}