package com.oraclechain.eosio.dto;


import lombok.Data;

@Data
public class ReqAssetsInfo {


    private String account_name;
    private String contract_name;
    private String token_symbol;
    private String coinmarket_id;


}
