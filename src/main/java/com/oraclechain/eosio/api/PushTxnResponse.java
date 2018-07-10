package com.oraclechain.eosio.api;


import com.google.gson.annotations.Expose;

import com.oraclechain.eosio.chain.TransactionTrace;
import org.apache.commons.lang3.StringUtils;

public class PushTxnResponse {

    @Expose
    private String transaction_id;

    @Expose
    private TransactionTrace processed;

    public String getTransactionId() {
        return transaction_id;
    }

    public void setTransactionId(String transactionId) {
        this.transaction_id = transactionId;
    }

    public TransactionTrace getProcessed() {
        return processed;
    }

    public void setProcessed(TransactionTrace processed) {
        this.processed = processed;
    }

    @Override
    public String toString() {
        if (StringUtils.isEmpty(transaction_id) || (processed == null )) return "";

        return "transaction: " + transaction_id + "\n" + processed.toString();
    }
}
