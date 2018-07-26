package com.su.admin.service.role.impl;

import com.alibaba.fastjson.JSONObject;
import com.su.admin.entity.RolePrivilege;
import com.su.admin.service.role.RoleService;
import com.su.common.entity.SearchParam;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午5:35
 * @version
 */
@Service
public class RoleServiceImpl implements RoleService {



    @Override
    public int deletePrivilege(int roleId) {
        return 0;
    }

    @Override
    public void batchInsertRolePrivilege(List<RolePrivilege> list) {
        //roleMapper.batchInsertRolePrivilege(list);
    }

    @Override
    public JSONObject getList(SearchParam params) {
        return null;
    }


    @Override
    public JSONObject getPojo(int id) {
        return null;
    }

    @Override
    public JSONObject insertPojo(JSONObject pojo) {
        return null;
    }

    @Override
    public JSONObject updatePojo(JSONObject pojo) {
        //roleMapper.update(pojo);
        return null;
    }

    @Override
    public JSONObject deletePojo(int id) {
        return null;
    }

}
