package com.oraclechain.eosio.dto;

import lombok.Data;

@Data
public class UserAssetInfo {

        private String account_name;
        private String balance;
        private String balance_usd;
        private String balance_cny;
        private String asset_price_usd;
        private String asset_price_cny;
        private String asset_price_change_in_24h;
        private String asset_market_cap_usd;
        private String asset_market_cap_cny;

}
