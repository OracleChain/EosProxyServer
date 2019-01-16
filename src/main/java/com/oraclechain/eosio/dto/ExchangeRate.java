package com.oraclechain.eosio.dto;

import lombok.Data;

@Data
public class ExchangeRate {
//                "id": "oraclechain",
//                "name": "OracleChain",
//                "symbol": "OCT",
//                "rank": "357",
//                "price_usd": "0.851606",
//                "price_btc": "0.00007631",
//                "24h_volume_usd": "179394.0",
//                "market_cap_usd": "25548180.0",
//                "available_supply": "30000000.0",
//                "total_supply": "30000000.0",
//                "max_supply": null,
//                "percent_change_1h": "-0.48",
//                "percent_change_24h": "5.61",
//                "percent_change_7d": "11.73",
//                "last_updated": "1516886057",
//                "price_cny": "5.3843845341",
//                "24h_volume_cny": "1134240.80986",
//                "market_cap_cny": "161531536.0"
    private String id;
    private String price_usd;
    private String price_cny;
    private String percent_change_24h;
    private String market_cap_usd;
    private String market_cap_cny;


    public ExchangeRate(){
        id= "0";
        price_usd = "0";
        price_cny = "0";
        percent_change_24h = "0";
        market_cap_usd = "0";
        market_cap_cny = "0";

    }


    @Override
    public String toString() {
        return "ExchangeRate: [id=" + id
                + ", price_usd=" + price_usd
                + ", price_cny=" + price_cny
                + ", percent_change_24h=" + percent_change_24h
                + ", market_cap_usd=" + market_cap_usd
                + ", market_cap_cny=" + market_cap_cny
                + "]";
    }
}
