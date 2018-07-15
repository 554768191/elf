package com.su.sso.exception;

public class SsoException extends RuntimeException {

    /**
     * 错误编码
     */
    private int errorCode;

    /**
     * 构造一个基本异常.
     *
     * @param message  信息描述
     * @param cause  根异常类（可以存入任何异常）
     */
    public SsoException(String message, Throwable cause){
        super(message, cause);
    }

    public SsoException(String message){
        super(message);
    }

    /**
     * 构造一个基本异常.
     *
     * @param errorCode 错误编码
     * @param message 信息描述
    */
    public SsoException(int errorCode, String message) {
        super(message);
        setErrorCode(errorCode);
    }

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

}
