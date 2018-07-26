package com.su.admin.service;

import com.alibaba.fastjson.JSONObject;
import com.su.common.entity.SearchParam;


/**
 * Created by cloud on 16/1/18.
 */
public interface BaseService {

    /**
     * get list.
     *
     * @param params
     * @return List
     * @throws
     */
    JSONObject getList(SearchParam params);

    /**
     * get pojo.
     *
     * @param id
     * @return T
     * @throws
     */
    JSONObject getPojo(int id);

    /**
     * add pojo.
     *
     * @param pojo
     * @return int
     * @throws
     */
    JSONObject insertPojo(JSONObject pojo);

    /**
     * update pojo.
     *
     * @param pojo
     * @return int
     * @throws
     */
    JSONObject updatePojo(JSONObject pojo);

    /**
     * delete pojo.
     *
     * @param id
     * @return int
     * @throws
     */
    JSONObject deletePojo(int id);

}
