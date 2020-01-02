package com.zmall.common;

public enum ResponseCode {

    /**
     * SUCCESS 成功
     * ERROR {@link ServerResponse} ServerResponse.createByError()默认返回1，表示用户未登录
     * NEED_LOGIN 表示用户未登录需要强制登录
     * ILLEGAL_ARGUMENT 参数错误
     * NO_PERMISSION 用户无权限操作
     * _500 服务器内部错误
     */
    SUCCESS(0,"SUCCESS"),
    ERROR(1,"ERROR"),
    NEED_LOGIN(10,"NEED_LOGIN"),
    ILLEGAL_ARGUMENT(2,"ILLEGAL_ARGUMENT"),
    NO_PERMISSION(3,"NO_PERMISSION"),
    _500(500,"SERVER ERROR,INTERFACE EXCEPTION");


    private final int code;
    private final String desc;

    ResponseCode(int code,String desc){
        this.code = code;
        this.desc = desc;
    }

    public int getCode(){
        return code;
    }
    public String getDesc(){
        return desc;
    }
}
