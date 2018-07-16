package com.su.admin.interceptor;

import com.su.common.Constants;
import com.su.common.exception.CommonException;
import com.su.sso.service.auth.AuthService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Desc
 * @author surongyao
 * @date 2018/7/12 下午4:38
 * @version
 */
@Component
public class LoginlerInterceptor extends HandlerInterceptorAdapter {

    private static final Logger logger = LoggerFactory.getLogger(LoginlerInterceptor.class);

    @Value("${auth.excludes}")
    String excludes;

    @Autowired
    AuthService authService;


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
                                       Object handler) throws Exception {

        // 跨域
        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setHeader("Access-Control-Allow-Headers", "origin, content-type, accept, x-requested-with");
        //response.setHeader("Access-Control-Max-Age", "60");
        response.setHeader("Access-Control-Allow-Methods", "GET, POST, DELETE, PUT, OPTIONS");
        response.setHeader("Access-Control-Allow-Credentials", "true");

        String uri = request.getRequestURI();
        String path = request.getContextPath();
        if(StringUtils.isNotEmpty(path)){
            uri = uri.replace(path, "");
        }
        if(uri.equals("/")){
            return true;
        }

        if(StringUtils.isNotEmpty(excludes)){
            String [] excludeArray = excludes.split(",");
            String [] uris = uri.split("/");
            for(String exclude:excludeArray){
                if(exclude.trim().equals(uris[1]) ){
                    return true;
                }
            }
        }

        boolean flag = authService.check(request);
        // todo 添加校验是否只读， 只读用户只可以执行get、options方法，post、delete、put方法没权限
        // request.getMethod()
        if(flag){
            logger.info("uri: [{}]校验通过", uri);
            return true;
        }
        logger.warn("uri: [{}]校验失败", uri);
        throw new CommonException(Constants.UN_AUTH, "auth failed");

    }
}
