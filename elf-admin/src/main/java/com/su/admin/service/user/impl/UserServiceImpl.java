package com.su.admin.service.user.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.su.admin.entity.ClientUserAgent;
import com.su.admin.entity.User;
import com.su.admin.service.rest.RestService;
import com.su.admin.service.user.UserService;
import com.su.common.entity.SearchParam;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午5:37
 * @version
 */
@Service
public class UserServiceImpl implements UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private RestService restService;


    @Override
    //@Cacheable(value="getUser")
    public User getByName(String account) {
        SearchParam params = new SearchParam();
        params.setName(account);
        List<User> list = getList(params);
        if(list!=null && list.size()>0){
            return list.get(0);
        }

        return null;
    }


    @Override
    public void addLoginLog(HttpServletRequest request, String username, String message) {
        ClientUserAgent agentGetter = new ClientUserAgent(request);
        // 添加到登录日志
        JSONObject jsonObj = new JSONObject();
        jsonObj.put("level", "info");
        jsonObj.put("module", "admin");
        jsonObj.put("clientIp", agentGetter.getIpAddr());
        jsonObj.put("content", username + "登陆系统，" + message);
        jsonObj.put("browser", agentGetter.getBrowser());
        jsonObj.put("os", agentGetter.getOS());

        JSONObject result = restService.post("http://system/log", jsonObj);
        logger.info(result.toJSONString());

    }

    @Override
    public List<User> getList(SearchParam params) {
        StringBuilder sb = new StringBuilder("http://system/user");
        if(params!=null){
            if(StringUtils.isNotEmpty(params.getName())){
                sb.append("?name=").append(params.getName());
            }
        }
        JSONObject json = restService.get(sb.toString());
        if(json!=null){
            JSONArray array = json.getJSONArray("list");
            if(array!=null && array.size()>0){
                List<User> list = new ArrayList<>();
                User user;
                for(int i=0;i<array.size();i++){
                    json = array.getJSONObject(0);
                    user = new User();
                    user.setId(json.getInteger("id"));
                    user.setAccount(json.getString("account"));
                    user.setRoleId(json.getInteger("roleId"));
                    user.setIsSuper(json.getInteger("isSuper"));
                    user.setPassWord(json.getString("password"));
                    list.add(user);
                }
                return list;
            }
        }
        return null;

    }

    @Override
    public User getPojo(int id) {
        JSONObject json = restService.get("http://system/user/" + id);
        return null;
    }

    @Override
    public int insertPojo(User pojo) {
        int id = 0;
        pojo.setId(id);
        return id;
    }

    @Override
    public int updatePojo(User pojo) {
        //userMapper.update(pojo);
        return 0;
    }

    @Override
    public int deletePojo(int id) {
        return 0;
    }


}
