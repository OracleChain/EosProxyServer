package com.oraclechain.eosio.dto;


import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.gson.annotations.Expose;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class RedPacketTxInfo {

    @Expose
    private String contract;//目前支持eosio.token和octtothemoon
    @Expose
    private String to;
    @Expose
    private String amount;//格式如"1.0001 EOS"，或者"1.0001 OCT"
    @Expose
    private String memo;


    @Override
    public String toString() {
        return "RedPacketInfo: [contract=" + contract
                + ", to=" + to
                + ", amount=" + amount
                + ", memo=" + memo
                + "]";
    }
}
