package com.oraclechain.eosio.crypto.ec;


import com.oraclechain.eosio.crypto.util.HexUtils;

import java.math.BigInteger;

public class CurveParam {
    public static final int SECP256_K1 = 0;
    public static final int SECP256_R1 = 1;

    private final int curveParamType;
    private final EcCurve curve;
    private final EcPoint G;
    private final BigInteger n;
    //private final BigInteger h;

    private final BigInteger HALF_CURVE_ORDER;

    public CurveParam( int curveParamType, String pInHex, String aInHex, String bInHex, String GxInHex, String GyInHex, String nInHex ){
        this.curveParamType = curveParamType;
        BigInteger p = new BigInteger(pInHex, 16); //p
        BigInteger b = new BigInteger(bInHex , 16);
        BigInteger a = new BigInteger( aInHex, 16);
        curve = new EcCurve(p, a, b);

        G = curve.decodePoint( HexUtils.toBytes("04" + GxInHex + GyInHex)  );
        n = new BigInteger(nInHex, 16);
        //h = BigInteger.ONE;

        HALF_CURVE_ORDER = n.shiftRight(1);
    }

    public int getCurveParamType() {
        return curveParamType;
    }

    public boolean isType(int paramType ) {
        return curveParamType == paramType;
    }


    public EcPoint G() {
        return this.G;
    }

    public BigInteger n() {
        return this.n;
    }

    public BigInteger halfCurveOrder() {
        return HALF_CURVE_ORDER;
    }

    public EcCurve getCurve() {
        return curve;
    }
}
