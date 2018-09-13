package com.oraclechain.eosio.eosApi;

import com.google.gson.annotations.Expose;

import java.util.List;

/**
 * Created by swapnibble on 2017-11-17.
 */

public class JsonToBinResponse {
    @Expose
    private String binargs;

    @Expose
    private List<String> required_scope;

    @Expose
    private List<String> required_auth;

    public String getBinargs() {
        return binargs;
    }

    public List<String> getRequiredScope(){
        return required_scope;
    }

    public List<String> getRequiredAuth(){
        return required_auth;
    }
}
