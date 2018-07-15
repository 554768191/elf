package com.su.admin.service.privilege.impl;

import com.su.admin.entity.Privilege;
import com.su.admin.service.privilege.PrivilegeService;
import com.su.common.entity.SearchParam;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午5:32
 * @version
 */
@Service
public class PrivilegeServiceImpl implements PrivilegeService {



    @Override
    public List<Privilege> getPrivilegeByRoleId(int roleId) {
        return null;
    }

    @Override
    public List<Privilege> getPrivilegeByParentId(int parentId) {
        return null;
    }

    @Override
    public List<Privilege> getPrivileges() {
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
    public List<Privilege> getList(SearchParam params) {
        List<Privilege> pList = getPrivilegeByParentId(0);
        Map<Integer, String> maps = new HashMap<>();
        for(Privilege p:pList){
            maps.put(p.getId(), p.getPrivilegeName());
        }
        List<Privilege> list = null;
        for(Privilege p:list){
            if(p.getParentId()>0){
                p.setParentName(maps.get(p.getParentId()));
            }
        }
        return list;
    }

    @Override
    public int getCount(SearchParam params) {
        return 0;
    }

    @Override
    public Privilege getPojo(int id) {
        return null;
    }

    @Override
    public Privilege insertPojo(Privilege pojo) {
        int id = 0;
        pojo.setId(id);
        return pojo;
    }

    @Override
    public Privilege updatePojo(Privilege pojo) {
        //privilegeMapper.update(pojo);
        return pojo;
    }

    @Override
    public int deletePojo(int id) {
        return 0;
    }

}
