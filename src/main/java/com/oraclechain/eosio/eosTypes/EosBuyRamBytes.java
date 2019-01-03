package com.oraclechain.eosio.eosTypes;

import com.oraclechain.eosio.crypto.util.HexUtils;

public class EosBuyRamBytes implements EosType.Packer {

    private TypeAccountName payer;

    private TypeAccountName receiver;

    private int bytes;

    public String getActionName() {
        return "buyrambytes";
    }


    public EosBuyRamBytes(String payer, String receiver, int bytes) {
        this.payer = new TypeAccountName(payer);
        this.receiver = new TypeAccountName(receiver);
        this.bytes = bytes;
    }

    @Override
    public void pack(EosType.Writer writer) {
        payer.pack(writer);
        receiver.pack(writer);
        writer.putIntLE( bytes);
    }


    public String getAsHex() {
        EosType.Writer writer = new EosByteWriter(256);
        pack(writer);
        return HexUtils.toHex( writer.toBytes() );
    }

}
