package com.oraclechain.eosio.dto;

import com.google.gson.JsonArray;
import lombok.Data;

@Data
public class BlockChainError {

    private Integer code;
    private String name;
    private String what;
    private JsonArray details;

    @Override
    public String toString() {
        return "CoinMarketTicker: [code=" + code
                + ", name=" + name
                + ", what=" + what
                + ", details=" + details
                + "]";
    }
}
