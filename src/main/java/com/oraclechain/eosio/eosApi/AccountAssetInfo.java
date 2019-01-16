package com.oraclechain.eosio.eosApi;

import lombok.Data;

@Data
public class AccountAssetInfo {
    private String account_name;
    private String eos_balance;
    private String eos_balance_usd;
    private String eos_balance_cny;
    private String eos_price_usd;
    private String eos_price_cny;
    private String eos_price_change_in_24h;
    private String eos_market_cap_usd;
    private String eos_market_cap_cny;
    private String oct_balance;
    private String oct_balance_usd;
    private String oct_balance_cny;
    private String oct_price_usd;
    private String oct_price_cny;
    private String oct_price_change_in_24h;
    private String oct_market_cap_usd;
    private String oct_market_cap_cny;
    private String eos_net_weight;
    private String eos_cpu_weight;
    private String eos_ram_quota;



    @Override
    public String toString() {
        return "AccountAssetInfo: [account_name=" + account_name
                + ", eos_balance=" + eos_balance
                + ", eos_balance_usd=" + eos_balance_usd
                + ", eos_balance_cny=" + eos_balance_cny
                + ", eos_price_usd=" + eos_price_usd
                + ", eos_price_cny=" + eos_price_cny
                + ", eos_price_change_in_24h=" + eos_price_change_in_24h
                + ", eos_market_cap_usd=" + eos_market_cap_usd
                + ", eos_market_cap_cny=" + eos_market_cap_cny
                + ", oct_balance=" + oct_balance
                + ", oct_balance_usd=" + oct_balance_usd
                + ", oct_balance_cny=" + oct_balance_cny
                + ", oct_price_usd=" + oct_price_usd
                + ", oct_price_cny=" + oct_price_cny
                + ", oct_price_change_in_24h=" + oct_price_change_in_24h
                + ", oct_market_cap_usd=" + oct_market_cap_usd
                + ", oct_market_cap_cny=" + oct_market_cap_cny
                + ", eos_net_weight=" + eos_net_weight
                + ", eos_cpu_weight=" + eos_cpu_weight
                + ", eos_ram_quota=" + eos_ram_quota
                + "]";
    }
}

