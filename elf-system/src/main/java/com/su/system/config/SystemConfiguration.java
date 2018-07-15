package com.su.system.config;

import com.su.common.redis.RedisDao;
import com.su.sso.service.auth.AuthService;
import com.su.sso.service.auth.impl.AuthServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.core.StringRedisTemplate;

/**
 * @Desc
 * @author surongyao
 * @date 2018/6/5 上午8:41
 * @version
 */
@Configuration
public class SystemConfiguration {

    @Autowired
    private StringRedisTemplate redisTemplate;

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


}
