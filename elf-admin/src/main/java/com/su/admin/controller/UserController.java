package com.su.admin.controller;

import com.su.admin.entity.User;
import com.su.admin.service.user.UserService;
import com.su.common.entity.SearchParam;
import com.su.common.exception.AppException;
import com.su.sso.service.auth.AuthService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午5:39
 * @version
 */
@RestController
@RequestMapping("/user")
public class UserController {

    private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

//    @Autowired
//    AuthService authService;

    /*
    @RequestMapping("/psw")
    public ResultMap changePwd(HttpServletRequest request, String oldPsw, String newPsw){
        String token = authService.fetchToken(request);
        String account = authService.getUserAccount(token);
        if(StringUtils.isAnyEmpty(oldPsw, newPsw)){
            throw new IllegalArgumentException("参数为空");
        }
        User user = userService.getByName(account);
        if(user!=null){
            if(oldPsw.equalsIgnoreCase(user.getPassWord())){
                user.setPassWord(newPsw);
                userService.updatePojo(user);
            }else{
                throw new AppException("旧密码错误");
            }
        }else{
            throw new AppException("用户不存在");
        }
        logger.info("用户[{}]，修改密码，旧密码[{}]、新密码[{}]", account, oldPsw, newPsw);

        return ResultMap.ok();
    }


    @RequestMapping(method = RequestMethod.GET)
    public ResultMap getUserList(SearchParam param){
        param.setOffset((param.getPage()-1)*param.getLimit());
        List<User> list = userService.getList(param);
        int total = userService.getCount(param);
        return ResultMap.ok().put("count", total).put("data", list);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResultMap addUser(@RequestBody User user){
        user = userService.insertPojo(user);
        return ResultMap.ok().put("id", user.getId());
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.DELETE)
    public ResultMap deleteUser(@PathVariable int pid){
        userService.deletePojo(pid);
        //responseMessage.setData(result);
        return ResultMap.ok();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResultMap updateRole(@RequestBody User user){
        userService.updatePojo(user);
        return ResultMap.ok();
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.GET)
    public ResultMap getUser(@PathVariable int pid){
        User user = userService.getPojo(pid);
        return ResultMap.ok().put("user", user);
    }
    */
}
