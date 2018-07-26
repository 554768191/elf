package com.su.admin.service.privilege.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.su.admin.entity.ListData;
import com.su.admin.entity.Privilege;
import com.su.admin.service.privilege.PrivilegeService;
import com.su.admin.service.rest.RestService;
import com.su.common.entity.SearchParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpMethod;
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
    public ListData<Privilege> getList(SearchParam params) {
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
                ListData<Privilege> listData = new ListData<>();
                listData.setList(list);
                listData.setCount(json.getInteger("count"));
                return listData;
            }

        }
        return null;
    }


    @Override
    public JSONObject getPojo(int id) {
        JSONObject json = restService.get("http://system/privilege/" + id);
        if(json!=null && json.getJSONObject("privilege")!=null){
            //json = json.getJSONObject("privilege");
            return json;
        }
        return null;
    }

    @Override
    public JSONObject insertPojo(Privilege pojo) {
        Gson gson = new Gson();
        return restService.post("http://system/privilege", gson.toJson(pojo));
    }

    @Override
    public JSONObject updatePojo(Privilege pojo) {
        Gson gson = new Gson();
        return restService.exchange("http://system/privilege", HttpMethod.PUT, gson.toJson(pojo));
    }

    @Override
    public JSONObject deletePojo(int id) {
        return restService.exchange("http://system/privilege", HttpMethod.DELETE, id+"");
    }

}
