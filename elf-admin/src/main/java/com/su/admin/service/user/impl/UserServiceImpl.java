package com.su.admin.service.user.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.su.admin.entity.ClientUserAgent;
import com.su.admin.service.log.LogService;
import com.su.admin.service.rest.RestService;
import com.su.admin.service.user.UserService;
import com.su.common.entity.SearchParam;
import com.su.sso.entity.SsoUser;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;

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

    @Autowired
    private LogService logService;

    @Override
    //@Cacheable(value="getUser")
    public SsoUser getByName(String account) {
        SearchParam params = new SearchParam();
        params.setName(account);
        JSONObject json = getList(params);
        if(json!=null){
            JSONArray array = json.getJSONArray("list");
            if(array!=null && array.size()>0){
                SsoUser user= new SsoUser();;
                json = array.getJSONObject(0);
                user.setId(json.getInteger("id"));
                user.setAccount(json.getString("account"));
                user.setRoleId(json.getInteger("roleId"));
                user.setIsSuper(json.getInteger("isSuper"));
                user.setPassWord(json.getString("password"));
                return user;
            }
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

        JSONObject result = logService.insertPojo(jsonObj);
        logger.info(result.toJSONString());

    }

    @Override
    public JSONObject getList(SearchParam params) {
        StringBuilder sb = new StringBuilder("http://system/user");
        if(params!=null){
            if(StringUtils.isNotEmpty(params.getName())){
                sb.append("?name=").append(params.getName());
            }
        }
        return restService.get(sb.toString());
    }

    @Override
    public JSONObject getPojo(int id) {
        JSONObject json = restService.get("http://system/user/" + id);
        return null;
    }

    @Override
    public JSONObject insertPojo(JSONObject pojo) {
        return null;
    }

    @Override
    public JSONObject updatePojo(JSONObject pojo) {
        //userMapper.update(pojo);
        return null;
    }

    @Override
    public JSONObject deletePojo(int id) {
        return null;
    }


}
