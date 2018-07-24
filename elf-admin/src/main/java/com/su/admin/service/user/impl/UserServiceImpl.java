package com.su.admin.service.user.impl;

import com.alibaba.fastjson.JSONObject;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;
import com.su.admin.entity.ClientUserAgent;
import com.su.admin.entity.User;
import com.su.admin.service.user.UserService;
import com.su.common.entity.Log;
import com.su.common.entity.SearchParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午5:37
 * @version
 */
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private RestTemplate restTemplate;


    @Override
    @HystrixCommand(fallbackMethod = "fallback")
    public User getByName(String account) {
        String string = restTemplate.getForEntity("http://system/user?name=admin", String.class).getBody();
        JSONObject json = JSONObject.parseObject(string);

        return null;
    }

    /**
     * hystrix fallback方法
     */
    public User fallback(String account) {
        System.out.println("异常发生，进入fallback方法");
        return null;
    }


    @Override
    public void addLoginLog(HttpServletRequest request, String username, String message) {
        ClientUserAgent agentGetter = new ClientUserAgent(request);
        // 添加到登录日志
        Log log = new Log();
        log.setLevel("info");
        log.setModule("system");
        log.setClientIp(agentGetter.getIpAddr());
        log.setContent(username + "登陆系统，" + message);
        log.setBrowser(agentGetter.getBrowser());
        log.setOs(agentGetter.getOS());
        //logMapper.insert(log);
    }

    @Override
    public List<User> getList(SearchParam params) {
        return null;
    }

    @Override
    public int getCount(SearchParam params) {
        return 0;
    }

    @Override
    public User getPojo(int id) {
        return null;
    }

    @Override
    public User insertPojo(User pojo) {
        int id = 0;
        pojo.setId(id);
        return pojo;
    }

    @Override
    public User updatePojo(User pojo) {
        //userMapper.update(pojo);
        return pojo;
    }

    @Override
    public int deletePojo(int id) {
        return 0;
    }


}
