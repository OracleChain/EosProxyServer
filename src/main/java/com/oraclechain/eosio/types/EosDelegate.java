package com.oraclechain.eosio.types;

import com.google.gson.annotations.Expose;
import com.oraclechain.eosio.crypto.util.HexUtils;

public class EosDelegate {

    @Expose
    private String from;

    @Expose
    private String receiver;

    @Expose
    private String stake_net_quantity;

    @Expose
    private String stake_cpu_quantity;

    @Expose
    private int transfer;

    public String getActionName() {
        return "delegatebw";
    }


    public EosDelegate(String payer, String receiver, String stake_net_quantity, String stake_cpu_quantity, Boolean transfer) {
//        this.from = new TypeAccountName(payer);
//        this.receiver = new TypeAccountName(receiver);
        this.from = payer;
        this.receiver = receiver;
        this.stake_net_quantity = stake_net_quantity;
        this.stake_cpu_quantity = stake_cpu_quantity;
        if(transfer){
            this.transfer = 1;
        }
        else{
            this.transfer = 0;
        }
    }
//
//    @Override
//    public void pack(EosType.Writer writer) {
//        from.pack(writer);
//        receiver.pack(writer);
//        writer.putString(stake_net_quantity);
//        writer.putString(stake_cpu_quantity);
//        if(transfer != 0) transfer = 1;
//        writer.putVariableUInt(transfer);
//    }
//
//
//    public String getAsHex() {
//        EosType.Writer writer = new EosByteWriter(256);
//        pack(writer);
//        return HexUtils.toHex( writer.toBytes() );
//    }

}
