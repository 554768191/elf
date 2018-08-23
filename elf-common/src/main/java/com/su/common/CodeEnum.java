package com.su.common;

/**
 * @Desc
 * @author surongyao
 * @date 2018/8/23 上午11:21
 * @version
 */
public enum CodeEnum {

    SUCCESS(0, "成功"),
    FAILED(-1, "失败"),
    ILLEGAL_PARAM(101, "参数非法"),
    EMPTY_PARAM(102, ""),

    UN_AUTH(201, ""),
    NO_USER(202, ""),
    NO_PERMISSION(203, ""),

    SQL_ERROR(301, ""),
    SQL_INDEX_CONFLICT(3011, "");

    private int code;
    private String msg;

    CodeEnum(int code, String msg){
        this.code = code;
        this.msg = msg;
    }

    public int getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

}
