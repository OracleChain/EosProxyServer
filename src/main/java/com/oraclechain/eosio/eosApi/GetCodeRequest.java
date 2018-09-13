package com.oraclechain.eosio.eosApi;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class GetCodeRequest {
    @SerializedName("account_name")
    @Expose
    private String name;

    public GetCodeRequest(String accountName){
        name = accountName;
    }
}
