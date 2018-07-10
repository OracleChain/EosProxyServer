package com.oraclechain.eosio.api;

import lombok.Data;

@Data
public class GetCurrencyInfo
{

    private String code;
    private String account;
    private String symbol;


    @Override
    public String toString() {
        return "GetCurrencyInfo: [code=" + code
                + ", account=" + account
                + ", symbol=" + symbol
                + "]";
    }
}
