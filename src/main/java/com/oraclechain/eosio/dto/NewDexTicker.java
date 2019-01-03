package com.oraclechain.eosio.dto;


import lombok.Data;

@Data
public class NewDexTicker {
//        "symbol": "oct_eos",
//        "contract": "octtothemoon",
//        "currency": "OCT",
//        "last": 0.0237,
//        "change": 0.0172,
//        "high": 0.026,
//        "low": 0.0233,
//        "amount": 889.6848,
//        "volume": 21.140800000000002
    private String symbol;
    private String contract;
    private String currency;
    private String last;
    private String change;
    private String high;
    private String low;
    private String amount;
    private String volume;


}
