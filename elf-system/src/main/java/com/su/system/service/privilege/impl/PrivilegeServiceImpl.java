package com.su.system.service.privilege.impl;

import com.su.common.entity.SearchParam;
import com.su.system.entity.Privilege;
import com.su.system.mapper.PrivilegeMapper;
import com.su.system.service.privilege.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.List;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午5:32
 * @version
 */
@Service
public class PrivilegeServiceImpl implements PrivilegeService {

    @Autowired
    PrivilegeMapper privilegeMapper;

    @Override
    public List<Privilege> getPrivilegeByRoleId(int roleId) {
        return privilegeMapper.getPrivilegeByRoleId(roleId);
    }

    @Override
    public List<Privilege> getPrivilegeByParentId(int parentId) {
        return privilegeMapper.getPrivilegeByParentId(parentId);
    }


    @Override
    public List<Privilege> getList(SearchParam params) {
        List<Privilege> list = getPrivilegeByParentId(0);
        if(!CollectionUtils.isEmpty(list)){
            for(Privilege p:list){
                List<Privilege> subList = getPrivilegeByParentId(p.getId());
                p.setSubprivileges(subList);
            }
        }
        return list;

    }

    @Override
    public int getCount(SearchParam params) {
        return privilegeMapper.getCount(params);
    }

    @Override
    public Privilege getPojo(int id) {
        return privilegeMapper.get(id);
    }

    @Override
    public int insertPojo(Privilege pojo) {
        return privilegeMapper.insert(pojo);
    }

    @Override
    public int updatePojo(Privilege pojo) {
        return privilegeMapper.update(pojo);
    }

    @Override
    public int deletePojo(int id) {
        return privilegeMapper.delete(id);
    }

}
