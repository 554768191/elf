package com.su.system.service.user.impl;

import com.su.common.entity.SearchParam;
import com.su.system.entity.User;
import com.su.system.mapper.UserMapper;
import com.su.system.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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
    UserMapper userMapper;


    @Override
    public User getByName(String userName) {
        User user = userMapper.getByName(userName);
        return user;
    }


    @Override
    public List<User> getList(SearchParam params) {
        return userMapper.getList(params);
    }

    @Override
    public int getCount(SearchParam params) {
        return userMapper.getCount(params);
    }

    @Override
    public User getPojo(int id) {
        return userMapper.get(id);
    }

    @Override
    public User insertPojo(User pojo) {
        int id = userMapper.insert(pojo);
        pojo.setId(id);
        return pojo;
    }

    @Override
    public User updatePojo(User pojo) {
        userMapper.update(pojo);
        return pojo;
    }

    @Override
    public int deletePojo(int id) {
        return userMapper.delete(id);
    }

}
