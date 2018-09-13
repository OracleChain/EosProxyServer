package com.oraclechain.eosio.eosApi;


import com.google.gson.annotations.Expose;
import com.oraclechain.eosio.crypto.ec.EosPublicKey;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by swapnibble on 2017-11-15.
 */

public class RequiredKeysResponse {

    @Expose
    private List<String> required_keys ;

    public List<EosPublicKey> getKeys() {
        if ( null == required_keys ){
            return new ArrayList<>();
        }

        ArrayList<EosPublicKey> retKeys = new ArrayList<>(required_keys.size());
        for ( String pubKey: required_keys ){
            retKeys.add( new EosPublicKey( pubKey));
        }

        return retKeys;
    }
}
