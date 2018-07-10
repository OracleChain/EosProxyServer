package com.oraclechain.eosio.dto;

public class EosRefValue<T> {
    public T data;

    public  EosRefValue(){
        data = null;
    }

    public  EosRefValue(T initialVal ){
        data = initialVal;
    }
}
