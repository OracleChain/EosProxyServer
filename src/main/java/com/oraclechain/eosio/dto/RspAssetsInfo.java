package com.oraclechain.eosio.dto;


import com.google.gson.annotations.Expose;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

@Data
public class RspAssetsInfo {



    @Expose
    private List<UserAsset> user_asset_list = null;

    public void addUserAsset(UserAsset userAsset ){
        if ( null == user_asset_list) {
            user_asset_list = new ArrayList<>(1);
        }
        user_asset_list.add( userAsset);
    }


}
