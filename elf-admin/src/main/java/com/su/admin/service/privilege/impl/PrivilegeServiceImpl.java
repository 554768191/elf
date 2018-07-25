package com.su.admin.service.privilege.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.su.admin.entity.Privilege;
import com.su.admin.service.privilege.PrivilegeService;
import com.su.admin.service.rest.RestService;
import com.su.common.entity.SearchParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
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
    private RestService restService;

    @Override
    public List<String> getPrivilegeByRoleId(int roleId) {

        JSONObject json = restService.get("http://system/privilege/role/" + roleId);
        if(json!=null){
            JSONArray array = json.getJSONArray("list");
            if(array!=null && array.size()>0){
                List<String> list = new ArrayList<>();
                for(int i=0;i<array.size();i++){
                    list.add(array.getJSONObject(i).getString("link"));
                }
                return list;
            }
        }
        return null;
    }


    @Override
    public List<Privilege> getList(SearchParam params) {
        JSONObject json = restService.get("http://system/privilege");
        if(json!=null){
            JSONArray array = json.getJSONArray("list");
            if(array!=null && array.size()>0){
                /*
                Privilege p;
                for(int i=0;i<array.size();i++){
                    JSONObject j = array.getJSONObject(i);
                    JSONArray subArray = j.getJSONArray("subprivileges");
                    if(subArray!=null && subArray.size()>0){
                        p = new Privilege();
                        p.setId(j.getInteger("id"));
                        p.setCategory(j.getInteger("category"));
                        p.setHasChild(1);
                        p.setSeq(j.getInteger("seq"));
                        p.setLink(j.getString("link"));
                        p.setParentId(j.getInteger("parentId"));
                        p.setParentName(j.getString("parentName"));
                        p.setCreateTime(j.getString("createTime"));
                    }
                }
                */
                Gson gson = new Gson();
                List<Privilege> list = gson.fromJson(array.toJSONString(),
                        new TypeToken<List<Privilege>>() {}.getType());
                return list;
            }

        }
        return null;
    }


    @Override
    public Privilege getPojo(int id) {
        return null;
    }

    @Override
    public int insertPojo(Privilege pojo) {
        int id = 0;
        pojo.setId(id);
        return id;
    }

    @Override
    public int updatePojo(Privilege pojo) {
        //privilegeMapper.update(pojo);
        return 0;
    }

    @Override
    public int deletePojo(int id) {
        return 0;
    }

}
