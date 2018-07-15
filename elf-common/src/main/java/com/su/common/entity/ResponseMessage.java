package com.su.common.entity;

import com.alibaba.fastjson.JSONObject;


/**
 * 返回结果对象
 *
 * @author wangfan
 * @date 2017-6-10 上午10:10:07
 */
public class ResponseMessage {

    /**
     * 返回成功
     */
    public static String ok() {
        return msg(0, "成功！", null);
    }

    /**
     * 返回成功
     */
    public static String ok(int code) {
        return msg(code, "成功！", null);
    }

    /**
     * 返回成功
     */
    public static String ok(String message) {
        return msg(0, message, null);
    }

    /**
     * 返回成功
     */
    public static String ok(JSONObject data) {
        return msg(0, "成功！", data);
    }
    

    /**
     * 返回失败
     */
    public static String error() {
        return msg(-1,"失败！", null);
    }

    /**
     * 返回失败
     */
    public static String error(int code) {
        return msg(code,"失败！", null);
    }
    
    /**
     * 返回失败
     */
    public static String error(String message) {
        return msg(-1, message, null);
    }

    /**
     * 返回失败
     */
    public static String error(int code, String message) {
        return msg(code, message, null);
    }


    /**
     * 返回信息
     */
    public static String msg(int code, String message, JSONObject data) {
        JSONObject json = new JSONObject();
        json.put("code", code);
        json.put("msg", message);
        json.put("data", data);
        return json.toJSONString();
    }
    
    
    
}
