package com.oraclechain.eosio.dto;


import lombok.Data;

@Data
public class Sparklines {
    private String sparkline_eos_png;
    private String sparkline_oct_png;


    @Override
    public String toString() {
        return "Sparklines: [sparkline_eos_png=" + sparkline_eos_png
                + ", sparkline_oct_png=" + sparkline_oct_png
                + "]";
    }
}
