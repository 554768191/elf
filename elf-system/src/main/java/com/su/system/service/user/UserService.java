package com.su.system.service.user;

import com.su.system.entity.User;
import com.su.system.service.BaseService;

import javax.servlet.http.HttpServletRequest;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午5:29
 * @version
 */
public interface UserService extends BaseService<User> {

    User getByName(String userName);

    void addLoginLog(HttpServletRequest request, String username, String message);

}
