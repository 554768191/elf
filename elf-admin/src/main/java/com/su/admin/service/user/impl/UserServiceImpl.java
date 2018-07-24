package com.su.admin.service.user.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.su.admin.entity.ClientUserAgent;
import com.su.admin.entity.User;
import com.su.admin.service.user.UserService;
import com.su.common.Constants;
import com.su.common.entity.SearchParam;
import com.su.common.exception.CommonException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

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
    private RestTemplate restTemplate;


    @Override
    //@Cacheable(value="getUser")
    public User getByName(String account) {
        SearchParam params = new SearchParam();
        params.setName(account);
        JSONObject json = getList(params);
        if(json!=null && json.getInteger("code")==Constants.SUCCESS){
            json = json.getJSONObject("data");
            JSONArray array = json.getJSONArray("list");
            if(array!=null && array.size()>0){
                json = array.getJSONObject(0);
                User user = new User();
                user.setId(json.getInteger("id"));
                user.setAccount(account);
                user.setPassWord(json.getString("password"));
                return user;
            }
        }else if(json!=null && json.getInteger("code")!=Constants.SUCCESS){
            throw new CommonException(json.getInteger("code"), json.getString("msg"));
        }else{
            throw new CommonException(Constants.SERVER_ERROR, "调用用户服务出错");
        }
        return null;
    }


    @Override
    public void addLoginLog(HttpServletRequest request, String username, String message) {
        ClientUserAgent agentGetter = new ClientUserAgent(request);
        // 添加到登录日志
        HttpHeaders headers = new HttpHeaders();
        MediaType type = MediaType.parseMediaType("application/json; charset=UTF-8");
        headers.setContentType(type);
        headers.add("Accept", MediaType.APPLICATION_JSON.toString());

        JSONObject jsonObj = new JSONObject();
        jsonObj.put("level", "info");
        jsonObj.put("clientIp", agentGetter.getIpAddr());
        jsonObj.put("content", username + "登陆系统，" + message);
        jsonObj.put("browser", agentGetter.getBrowser());
        jsonObj.put("os", agentGetter.getOS());
        HttpEntity<String> formEntity = new HttpEntity(jsonObj.toString(), headers);

        String result = restTemplate.postForObject("http://system/log", formEntity, String.class);
        logger.info(result);

    }

    @Override
    public JSONObject getList(SearchParam params) {
        StringBuilder sb = new StringBuilder("http://system/user");
        if(params!=null){
            if(StringUtils.isNotEmpty(params.getName())){
                sb.append("?name=").append(params.getName());
            }
        }
        String string = restTemplate.getForEntity(sb.toString(), String.class).getBody();
        return JSONObject.parseObject(string);

    }

    @Override
    public JSONObject getPojo(int id) {
        String string = restTemplate.getForEntity("http://system/user/" + id,
                String.class).getBody();
        return JSONObject.parseObject(string);

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
