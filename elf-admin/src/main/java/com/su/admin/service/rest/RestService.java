package com.su.admin.service.rest;

import com.alibaba.fastjson.JSONObject;

/**
 * @Desc
 * @author surongyao
 * @date 2018/7/25 下午3:11
 * @version
 */
public interface RestService {

    JSONObject get(String url);

    JSONObject post(String url, JSONObject param);
}
