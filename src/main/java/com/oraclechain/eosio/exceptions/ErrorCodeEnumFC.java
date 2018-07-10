package com.oraclechain.eosio.exceptions;


public enum ErrorCodeEnumFC {

    /**
     * exceptions code based on c9b7a2472
     */

    //FROM libraries/fc/include/fc/exception/exception.hpp
    unspecified_exception_code(3990000, "unspecified"),
    unhandled_exception_code(3990001, "unhandled 3rd party exceptions"), ///< for unhandled 3rd party exceptions
    timeout_exception_code(3990002, "Timeout"),
    file_not_found_exception_code(3990003, "File Not Found"),
    parse_error_exception_code(3990004, "Parse Error"),
    invalid_arg_exception_code(3990005, "Invalid Argument"),
    key_not_found_exception_code(3990006, "Key Not Found"),
    bad_cast_exception_code(3990007, "Bad Cast"),
    out_of_range_exception_code(3990008, "Out of Range"),
    canceled_exception_code(3990009, "Canceled"),
    assert_exception_code(3990010, "Assert Exception"),
    eof_exception_code(3990011, "End Of File"),
    std_exception_code(3990013, "STD Exception"),//self made
    invalid_operation_exception_code(3990014, "Invalid Operation"),
    unknown_host_exception_code(3990015, "Unknown Host"),
    null_optional_code(3990016, "null optional"),
    udt_error_code(3990017, "UDT exceptions"),
    aes_error_code(3990018, "AES exceptions"),
    overflow_code(3990019, "Integer Overflow"),
    underflow_code(3990020, "Integer Underflow"),
    divide_by_zero_code(3990021, "Integer Divide By Zero");


    private int msg_id;
    private String msg;


    private ErrorCodeEnumFC(int msg_id, String msg) {
        this.msg = msg;
        this.msg_id = msg_id;
    }

    public int getMsg_id() {
        return msg_id;
    }

    public String getMsg() {
        return msg;
    }

    public static String getMsgById(int msg_id) {

        for (ErrorCodeEnumFC b : ErrorCodeEnumFC.values()) {
            if (b.msg_id == msg_id) {
                return b.msg;
            }
        }
        return unspecified_exception_code.getMsg();
    }

    public static ErrorCodeEnumFC getEnumById(int msg_id) {

        for (ErrorCodeEnumFC b : ErrorCodeEnumFC.values()) {
            if (b.msg_id == msg_id + 3990000) {
                return b;
            }
        }

        return unspecified_exception_code;
    }

}
