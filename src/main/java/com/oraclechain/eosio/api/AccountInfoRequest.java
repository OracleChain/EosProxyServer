package com.oraclechain.eosio.api;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by swapnibble on 2017-09-14.
 */

public class AccountInfoRequest {

    @Expose
    private String account_name;

    public AccountInfoRequest(String name) {
        setName(name);
    }

    public String getName() {
        return account_name;
    }

    public void setName(String name) {
        this.account_name = name;
    }
}
