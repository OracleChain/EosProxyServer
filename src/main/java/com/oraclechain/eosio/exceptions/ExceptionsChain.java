package com.oraclechain.eosio.exceptions;

public class ExceptionsChain extends RuntimeException {


    public ExceptionsChain(ErrorCodeEnumChain errorCode) {
        this.errorCode = errorCode;
    }

    public ExceptionsChain(int errorCode) {
        this.errorCode = ErrorCodeEnumChain.getEnumById(errorCode);
    }


    private ErrorCodeEnumChain errorCode;
    public ErrorCodeEnumChain getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(ErrorCodeEnumChain errorCode) {
        this.errorCode = errorCode;
    }

}
