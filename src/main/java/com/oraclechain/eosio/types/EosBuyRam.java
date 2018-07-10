package com.oraclechain.eosio.types;

import com.google.gson.annotations.Expose;

public class EosBuyRam {

    private String payer;

    private String receiver;

    private String quant;

    public String getActionName() {
        return "buyram";
    }

    public EosBuyRam(String payer, String receiver, String quant ) {
//        this.payer = new TypeAccountName(payer);
//        this.receiver = new TypeAccountName(receiver);
        this.payer = payer;
        this.receiver = receiver;
        this.quant = quant;
    }
}
