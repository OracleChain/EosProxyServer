package com.oraclechain.eosio.eosApi;


import com.google.gson.annotations.Expose;

public class Permission {

    @Expose
    private String name;

    @Expose
    private String parent;

    @Expose
    private RequiredAuth required_auth;


    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParent() {
        return parent;
    }

    public void setParent(String parent) {
        this.parent = parent;
    }

    public RequiredAuth getRequiredAuth() {
        return required_auth;
    }

    public void setRequiredAuth(RequiredAuth requiredAuth) {
        this.required_auth = requiredAuth;
    }

}
