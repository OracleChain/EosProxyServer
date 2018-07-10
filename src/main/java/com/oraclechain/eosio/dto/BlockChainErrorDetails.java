package com.oraclechain.eosio.dto;

import com.google.gson.JsonArray;
import lombok.Data;

@Data
public class BlockChainErrorDetails {


    private String file;
    private String method;
    private Integer line_number;
    private String message;

    @Override
    public String toString() {
        return "CoinMarketTicker: [file=" + file
                + ", method=" + method
                + ", line_number=" + line_number
                + ", message=" + message
                + "]";
    }
}
