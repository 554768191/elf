package com.su.system.aop;

import com.su.common.Constants;
import com.su.common.exception.AppException;
import com.su.sso.service.auth.AuthService;
import org.apache.commons.lang3.StringUtils;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author surongyao
 * @date 2018-05-28 19:33
 * @desc
 */
@Aspect
@Component
public class AuthAspect {

    private static final Logger logger = LoggerFactory.getLogger(AuthAspect.class);

    @Autowired
    AuthService authService;


    @Value("${auth.excludes}")
    String excludes;

    @Pointcut("execution(public * com.su.system.controller.*.*(..))")
    public void auth(){}

    @Before("auth()")
    public void doBefore(JoinPoint joinPoint) {
        // 接收到请求
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        HttpServletResponse response = attributes.getResponse();

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
            return;
        }

        if(StringUtils.isNotEmpty(excludes)){
            String [] excludeArray = excludes.split(",");
            String [] uris = uri.split("/");
            for(String exclude:excludeArray){
                if(exclude.trim().equals(uris[1]) ){
                    return;
                }
            }
        }

        boolean flag = authService.check(request);
        // todo 添加校验是否只读， 只读用户只可以执行get、options方法，post、delete、put方法没权限
        // request.getMethod()
        if(flag){
            logger.info("uri: [{}]校验通过", uri);
            return;
        }
        logger.warn("uri: [{}]校验失败", uri);
        throw new AppException(Constants.UN_AUTH, "auth failed");
    }

}
