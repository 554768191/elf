package com.su.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.su.admin.service.role.RoleService;
import com.su.common.entity.ResponseMessage;
import com.su.common.entity.SearchParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午5:40
 * @version
 */
@RestController
@RequestMapping("/role")
public class RoleController {

    //private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    RoleService roleService;

    @RequestMapping(method = RequestMethod.GET)
    public String getRoleList(SearchParam param){
        param.setOffset((param.getPage()-1)*param.getLimit());
        JSONObject json = roleService.getList(param);
        return ResponseMessage.ok(json);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addRole(@RequestBody JSONObject role){
        JSONObject json = roleService.insertPojo(role);
        return ResponseMessage.ok(json);
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.DELETE)
    public String deleteRole(@PathVariable int pid){
        JSONObject json = roleService.deletePojo(pid);
        return ResponseMessage.ok(json);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String updateRole(@RequestBody JSONObject role){
        JSONObject json = roleService.updatePojo(role);
        return ResponseMessage.ok(json);
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.GET)
    public String getRole(@PathVariable int pid){
        JSONObject json = roleService.getPojo(pid);
        return ResponseMessage.ok(json);
    }

}
