package com.su.sso.service.auth;


import com.su.sso.entity.SsoUser;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/30 下午3:26
 * @version
 */
public interface AuthService {

    /**
     * HttpServletRequest中拿到token
     * @param
     * @return
     * @throws Exception
     */
    String fetchToken(HttpServletRequest request);

    /**
     * 生成token
     * @param
     * @return
     * @throws Exception
     */
    String generateToken(HttpServletRequest request, SsoUser ssoUser);

    /**
     * 判断token是否合法
     * @param
     * @return
     * @throws Exception
     */
    boolean check(HttpServletRequest request);

    /**
     * 将token过期
     * @param
     * @return
     * @throws Exception
     */
    void expireToken(String token);

    /**
     * 获取权限列表
     * @param
     * @return
     * @throws Exception
     */
    List<String> getPrivileges(String token);

    /**
     * 是否超管
     * @param
     * @return
     * @throws Exception
     */
    boolean isSuper(String token);


    /**
     * 是否只读
     * @param
     * @return
     * @throws Exception
     */
    boolean isReadOnly(String token);


    /**
     * 根据token得到用户名
     * @param
     * @return
     * @throws Exception
     */
    String getUserAccount(String token);

}
