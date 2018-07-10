package com.oraclechain.eosio.dto;


import java.io.Serializable;
public class MessageResult implements Serializable {
    private static final long serialVersionUID = -58782578272943999L;
    //状态码
    private int code;
    //消息
    private String message;
    //结果数据
    private Object data;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    public MessageResult() {
    }

    public MessageResult(String message, int code, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static MessageResult success(String message, int code, Object data){
        return new MessageResult(message,code,data);
    }

    public static MessageResult success(String message, int code){
        return new MessageResult(message,code,null);
    }

    public static MessageResult success(Object data){
        return new MessageResult("ok",0,data);
    }

//    public static MessageResult success(){
//        return new MessageResult("ok",0,null);
//    }
//
//    public static MessageResult success(String message, Object data){
//        return new MessageResult(message,0, data);
//    }
//

    public static MessageResult error(String message,int code, Object data){
        return new MessageResult(message,code,data);
    }



    public static MessageResult error(String message,int code){
        return new MessageResult(message,code,null);
    }

    public static MessageResult error(String message){
        return new MessageResult(message,1,null);
    }

    public static MessageResult error(){
        return new MessageResult("未知错误,请联系管理员",1,null);
    }

}