package com.su.sso.service.auth.impl;


import com.su.common.CodeEnum;
import com.su.common.Constants;
import com.su.common.exception.CommonException;
import com.su.sso.SsoConstants;
import com.su.sso.cache.CacheData;
import com.su.sso.entity.SsoUser;
import com.su.sso.service.auth.AuthService;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.StringRedisTemplate;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.WeakHashMap;
import java.util.concurrent.TimeUnit;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/30 下午3:28
 * @version
 */
public class AuthServiceImpl implements AuthService {

    private static final Logger logger = LoggerFactory.getLogger(AuthServiceImpl.class);

    private StringRedisTemplate redisTemplate;

    public void setRedisTemplate(StringRedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    // 缓存token校验
    private WeakHashMap<String, CacheData> weakHashMap = new WeakHashMap<>();


    @Override
    public String fetchToken(HttpServletRequest request) {
        String token = request.getParameter("token");
        if(StringUtils.isEmpty(token)){
            // 如果没有cookie, 从header里取
            token = request.getHeader("token");
        }
        if(StringUtils.isEmpty(token)){
            Cookie[] cookies = request.getCookies();
            if(cookies!=null && cookies.length>0) {
                for (Cookie ck : cookies) {
                    if (ck.getName().equals("token")) {
                        token = ck.getValue();
                        break;
                    }
                }
            }
        }

        return token;
    }

    @Override
    public String generateToken(HttpServletRequest request, SsoUser ssoUser) {
        String token = DigestUtils.md5Hex(ssoUser.getAccount() + System.currentTimeMillis());

        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(token);

        ops.put("client", request.getRemoteAddr());
        ops.put("userAccount", ssoUser.getAccount());
        ops.put("is_super", ssoUser.getIsSuper()+"");
        ops.put("readOnly", ssoUser.getReadOnly()+"");

        //redisDao.hset(token, "client", request.getRemoteAddr());
        //redisDao.hset(token, "userAccount", ssoUser.getAccount());
        //redisDao.hset(token, "is_super", ssoUser.getIsSuper()+"");
        //redisDao.hset(token, "readOnly", ssoUser.getReadOnly()+"");

        if(ssoUser.getIsSuper()!=1){
            List<String> list = ssoUser.getPrivileges();
            StringBuilder sb = new StringBuilder();
            if(list!=null && list.size()>0){
                for(String p:list){
                    if(sb.length()>0){
                        sb.append(",").append(p);
                    }else{
                        sb.append(p);
                    }
                }
                //redisDao.hset(token, "privileges", sb.toString());
                ops.put("privileges", sb.toString());
            }
        }
        redisTemplate.expire(token, SsoConstants.TOKEN_CACHE_SECONDS, TimeUnit.SECONDS);
        return token;

    }


    @Override
    public boolean check(HttpServletRequest request) {

        String token = fetchToken(request);
        // 是否需要auth
        String clientIP = request.getRemoteAddr();
        String uri = request.getServletPath();
        String method = request.getMethod();
        if(logger.isDebugEnabled()){
            logger.debug("开始校验uri:{}, method:{}", uri, method);
        }

        if(weakHashMap.get(token + "_" + uri + "_" + method)!=null){
            CacheData cacheData = weakHashMap.get(token+"_"+uri);
            long now = System.currentTimeMillis();
            if(now - cacheData.getTime() < SsoConstants.MEMORY_CACHE_SECONDS*1000){
                cacheData.setTime(now);
                return (boolean) cacheData.getValue();
            }

        }

        if(StringUtils.isNotEmpty(token) && redisTemplate.hasKey(token)){

            BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(token);
            String cacheClientIP = ops.get("client");
            if(cacheClientIP.equals(clientIP)){
                String isSuper = ops.get("is_super");
                if(StringUtils.isNotEmpty(isSuper) && isSuper.equals("1")){
                    addCache(token + "_" + uri + "_" + method);
                    redisTemplate.expire(token, SsoConstants.TOKEN_CACHE_SECONDS, TimeUnit.SECONDS);
                    return true;
                }
                String privileges = ops.get("privileges");
                if(StringUtils.isNotEmpty(privileges)){
                    String [] ps = privileges.split(",");
                    for(String p:ps){
                        if(uri.indexOf(p)>=0){
                            // 添加校验是否只读， 只读用户只可以执行get、options方法，post、delete、put方法没权限

                            boolean b = isReadOnly(token);
                            if(b){
                                if(method.equalsIgnoreCase("post")
                                        || method.equalsIgnoreCase("put")
                                        || method.equalsIgnoreCase("delete")){
                                    if(logger.isDebugEnabled()){
                                        logger.debug("用户只读，没有写权限");
                                    }
                                    return false;
                                }
                            }
                            addCache(token + "_" + uri + "_" + method);
                            redisTemplate.expire(token, SsoConstants.TOKEN_CACHE_SECONDS, TimeUnit.SECONDS);
                            return true;
                        }
                    }
                }
                throw new CommonException(CodeEnum.NO_PERMISSION);
            }else{
                logger.warn("[{}]的用户token是[{}], 与缓存的clientIP不一致, 可能是伪造的请求", clientIP, token);
            }

        }else{
            throw new CommonException(CodeEnum.UN_AUTH);
        }

        return false;
    }

    @Override
    public void expireToken(String token) {
        redisTemplate.delete(token);
    }

    @Override
    public List<String> getPrivileges(String token) {
        if(StringUtils.isNotEmpty(token)){
            BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(token);
            String privileges = ops.get("privileges");
            if(StringUtils.isNotEmpty(privileges)){
                List<String> list = new ArrayList();
                String [] ps = privileges.split(",");
                for(String p:ps){
                    list.add(p);
                }
                return list;
            }
        }
        return null;
    }

    @Override
    public boolean isSuper(String token) {
        if(StringUtils.isNotEmpty(token)){
            BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(token);
            String isSuper = ops.get("is_super");
            if(StringUtils.isNotEmpty(isSuper) && isSuper.equals("1")){
                if(logger.isDebugEnabled()){
                    logger.debug("token:{}, 用户是超管", token);
                }
                return true;
            }
        }
        return false;
    }

    @Override
    public boolean isReadOnly(String token) {
        BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(token);
        String readOnly = ops.get("readOnly");
        if(StringUtils.isNotEmpty(readOnly) && readOnly.equals("1")){
            if(logger.isDebugEnabled()){
                logger.debug("token:{}, 只读用户", token);
            }
            return true;
        }
        return false;
    }


    @Override
    public String getUserAccount(String token) {
        if(StringUtils.isNotEmpty(token)){
            BoundHashOperations<String, String, String> ops = redisTemplate.boundHashOps(token);
            String user = ops.get("userAccount");
            if(StringUtils.isNotEmpty(user)){
                return user;
            }
        }
        return null;
    }

    private void addCache(String key){
        CacheData cacheData = new CacheData();
        cacheData.setTime(System.currentTimeMillis());
        cacheData.setValue(true);
        weakHashMap.put(key, cacheData);
    }

}
