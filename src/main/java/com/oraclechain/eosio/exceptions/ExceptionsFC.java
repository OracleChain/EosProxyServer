package com.oraclechain.eosio.exceptions;

public class ExceptionsFC extends RuntimeException {

    public ExceptionsFC(ErrorCodeEnumFC errorCode) {
        this.errorCode = errorCode;
    }
    public ExceptionsFC(int errorCode) {
        this.errorCode = ErrorCodeEnumFC.getEnumById(errorCode);
    }

    private ErrorCodeEnumFC errorCode;
    public ErrorCodeEnumFC getErrorCode() {
        return errorCode;
    }
    public void setErrorCode(ErrorCodeEnumFC errorCode) {
        this.errorCode = errorCode;
    }


}
