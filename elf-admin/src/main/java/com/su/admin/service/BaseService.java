package com.su.admin.service;



import com.alibaba.fastjson.JSONObject;
import com.su.common.entity.SearchParam;

import java.util.List;

/**
 * Created by cloud on 16/1/18.
 */
public interface BaseService<T> {

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
    int insertPojo(T pojo);

    /**
     * update pojo.
     *
     * @param pojo
     * @return int
     * @throws
     */
    int updatePojo(T pojo);

    /**
     * delete pojo.
     *
     * @param id
     * @return int
     * @throws
     */
    int deletePojo(int id);

}
