package com.su.admin.controller;

import com.su.admin.entity.Privilege;
import com.su.admin.entity.User;
import com.su.admin.service.privilege.PrivilegeService;
import com.su.admin.service.user.UserService;
import com.su.common.Constants;
import com.su.common.entity.ResponseMessage;
import com.su.common.redis.RedisDao;
import com.su.common.utils.CaptchaUtil;
import com.su.common.utils.RegexUtil;
import com.su.sso.entity.SsoUser;
import com.su.sso.service.auth.AuthService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.ArrayList;
import java.util.List;

@RestController
public class LoginController {

    private static final Logger logger = LoggerFactory.getLogger(LoginController.class);

    @Autowired
    UserService userService;

    @Autowired
    AuthService authService;

    @Autowired
    PrivilegeService privilegeService;

    @Autowired
    private RedisDao redisDao;


    @RequestMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response, String codeKey) throws Exception{
        response.setHeader("Cache-Control", "no-store");
        response.setHeader("Pragma", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        //生产验证码字符串并保存到session中
        CaptchaUtil cu = new CaptchaUtil(200, 60, 5);
        String createText = cu.getCode();
        //request.getSession().setAttribute("verifyCode", createText);
        logger.info("生成验证码[{}]", createText);
        if(StringUtils.isNotEmpty(codeKey)){
            redisDao.set(codeKey, createText);
        }
        ServletOutputStream responseOutputStream = response.getOutputStream();
        cu.write(responseOutputStream);
        responseOutputStream.flush();
        responseOutputStream.close();
    }


    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String login(HttpServletRequest request, HttpServletResponse response, String account,
                        String password, String captcha, String verkey){

        String text = null; //(String) session.getAttribute("verifyCode");
        if(StringUtils.isNotEmpty(verkey)){
            text = redisDao.get(verkey);
        }

        if(StringUtils.isEmpty(text) || !text.equalsIgnoreCase(captcha)){
            return ResponseMessage.error(Constants.ILLEGAL_PARAM, "验证码不正确");
        }

        //校验用户名密码
        if(!RegexUtil.isRegexMatch(account, "^[A-Za-z0-9@#$-_.]{1,64}$")){
            return ResponseMessage.error(Constants.ILLEGAL_PARAM, "用户名格式不合法");
        }

        User user = userService.getByName(account);

        if(user!=null){
            if(password!=null && user.getPassWord().equals(password)){
                SsoUser ssoUser = new SsoUser();
                ssoUser.setAccount(account);
                ssoUser.setIsSuper(user.getIsSuper());
                //ssoUser.setReadOnly(user.get);
                ssoUser.setRoleId(user.getRoleId());
                ssoUser.setPassWord(password);
                if(user.getIsSuper()!=1){
                    List<String> ps = new ArrayList<>();
                    List<Privilege> list = privilegeService.getPrivilegeByRoleId(user.getRoleId());
                    if(list!=null && list.size()>0){
                        for(Privilege p:list){
                            ps.add(p.getLink());
                        }
                        ssoUser.setPrivaleges(ps);
                    }
                }
                String token = authService.generateToken(request, ssoUser);
                Cookie cookie = new Cookie("token", token);
                cookie.setPath("/");
                cookie.setMaxAge(-1);
                response.addCookie(cookie);
                // 添加到登录日志
                userService.addLoginLog(request, account, "登录成功");

                return ResponseMessage.ok("登录成功");//.put("token", "").put("user", user);
            }else{
                userService.addLoginLog(request, account, "密码错误");
                return ResponseMessage.error(Constants.ILLEGAL_PARAM, "密码错误");
            }
        }else{
            return ResponseMessage.error(-14, "用户不存在");
        }

    }


    @RequestMapping(value = "/logout", method = RequestMethod.POST)
    public String logout(HttpServletRequest request){
        String token = authService.fetchToken(request);
        if(StringUtils.isNotEmpty(token)){
            authService.expireToken(token);
        }

        return ResponseMessage.ok();
    }



}
