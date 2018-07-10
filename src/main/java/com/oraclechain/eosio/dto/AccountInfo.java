package com.oraclechain.eosio.dto;

import com.google.gson.JsonElement;
import lombok.Data;

@Data
public class AccountInfo {



    private String account_name;
    private long net_weight;
    private long cpu_weight;
    private long ram_quota;


    @Override
    public String toString() {
        return "AccountInfo: [account_name=" + account_name
                + ", net_weight=" + net_weight
                + ", cpu_weight=" + cpu_weight
                + ", ram_quota=" + ram_quota
                + "]";
    }

}
