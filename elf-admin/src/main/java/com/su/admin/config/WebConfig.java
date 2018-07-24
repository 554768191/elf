package com.su.admin.config;

import com.su.admin.interceptor.LoginlerInterceptor;
import com.su.common.redis.RedisDao;
import com.su.sso.service.auth.AuthService;
import com.su.sso.service.auth.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.client.loadbalancer.LoadBalanced;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @Desc
 * @author surongyao
 * @date 2018/7/12 下午4:40
 * @version
 */
@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    @LoadBalanced
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Autowired
    private LoginlerInterceptor loginlerInterceptor;

    @Bean
    public RedisDao getRedisDao(){
        RedisDao redisDao = new RedisDao();
        redisDao.setRedisTemplate(redisTemplate);
        return redisDao;
    }

    @Bean
    public AuthService getAuthServive(){
        AuthServiceImpl authService = new AuthServiceImpl();
        authService.setRedisTemplate(redisTemplate);
        return authService;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(loginlerInterceptor).addPathPatterns("/**");
    }

}
