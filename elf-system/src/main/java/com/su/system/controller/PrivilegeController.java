package com.su.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.su.common.entity.ResponseMessage;
import com.su.common.entity.SearchParam;
import com.su.system.entity.Privilege;
import com.su.system.service.privilege.PrivilegeService;

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
 * @date 2018/5/25 下午5:40
 * @version
 */
@RestController
@RequestMapping("/privilege")
public class PrivilegeController {

    //private static final Logger logger = LoggerFactory.getLogger(PrivilegeController.class);

    @Autowired
    private PrivilegeService privilegeService;

    @RequestMapping(method = RequestMethod.GET)
    public String getPrivilegeList(SearchParam param){
        param.setOffset((param.getPage()-1)*param.getLimit());
        List<Privilege> list = privilegeService.getList(param);
        int total = privilegeService.getCount(param);
        JSONObject json = new JSONObject();
        json.put("count", total);
        json.put("list", list);
        return ResponseMessage.ok(json);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addPrivilege(@RequestBody Privilege privilege){
        privilege = privilegeService.insertPojo(privilege);
        JSONObject json = new JSONObject();
        json.put("id", privilege.getId());
        return ResponseMessage.ok(json);
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.DELETE)
    public String deletePrivilege(@PathVariable int pid){
        privilegeService.deletePojo(pid);
        return ResponseMessage.ok();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String updatePrivilege(@RequestBody Privilege privilege){
        privilegeService.updatePojo(privilege);
        return ResponseMessage.ok();
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.GET)
    public String getPrivilege(@PathVariable int pid){
        Privilege privilege = privilegeService.getPojo(pid);
        JSONObject json = new JSONObject();
        json.put("privilege", privilege);
        return ResponseMessage.ok(json);
    }

    @RequestMapping(value = "/role/{roleId}", method = RequestMethod.GET)
    public String getPrivilegeListByRole(@PathVariable int roleId){
        List<Privilege> list = privilegeService.getPrivilegeByRoleId(roleId);
        JSONObject json = new JSONObject();
        json.put("list", list);
        return ResponseMessage.ok(json);
    }

}
