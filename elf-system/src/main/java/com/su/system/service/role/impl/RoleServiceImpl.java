package com.su.system.service.role.impl;

import com.su.common.entity.SearchParam;
import com.su.system.entity.Role;
import com.su.system.entity.RolePrivilege;
import com.su.system.mapper.RoleMapper;
import com.su.system.service.role.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
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

    @Autowired
    RoleMapper roleMapper;

    @Override
    public int deletePrivilege(int roleId) {
        return roleMapper.deletePrivilege(roleId);
    }

    @Override
    public void batchInsertRolePrivilege(List<RolePrivilege> list) {
        roleMapper.batchInsertRolePrivilege(list);
    }

    @Override
    public List<Role> getList(SearchParam params) {
        return roleMapper.getList(params);
    }

    @Override
    public int getCount(SearchParam params) {
        return roleMapper.getCount(params);
    }

    @Override
    public Role getPojo(int id) {
        return roleMapper.get(id);
    }

    @Override
    public Role insertPojo(Role pojo) {
        int id = roleMapper.insert(pojo);
        pojo.setId(id);
        return pojo;
    }

    @Override
    public Role updatePojo(Role pojo) {
        roleMapper.update(pojo);
        return pojo;
    }

    @Override
    public int deletePojo(int id) {
        return roleMapper.deletePrivilege(id);
    }

}
