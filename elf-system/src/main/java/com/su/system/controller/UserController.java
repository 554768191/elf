package com.su.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.su.common.entity.ResponseMessage;
import com.su.common.entity.SearchParam;
import com.su.system.entity.User;
import com.su.system.service.user.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

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

   // private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    UserService userService;

    @RequestMapping(method = RequestMethod.GET)
    public String getUserList(SearchParam param){
        param.setOffset((param.getPage()-1)*param.getLimit());
        List<User> list = userService.getList(param);
        int total = userService.getCount(param);
        JSONObject json = new JSONObject();
        json.put("count", total);
        json.put("data", list);
        return ResponseMessage.ok(json);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addUser(@RequestBody User user){
        user = userService.insertPojo(user);
        JSONObject json = new JSONObject();
        json.put("id", user.getId());
        return ResponseMessage.ok(json);
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.DELETE)
    public String deleteUser(@PathVariable int pid){
        userService.deletePojo(pid);
        //responseMessage.setData(result);
        return ResponseMessage.ok();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String updateRole(@RequestBody User user){
        userService.updatePojo(user);
        return ResponseMessage.ok();
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.GET)
    public String getUser(@PathVariable int pid){
        User user = userService.getPojo(pid);
        JSONObject json = new JSONObject();
        json.put("user", user);
        return ResponseMessage.ok(json);
    }

}
