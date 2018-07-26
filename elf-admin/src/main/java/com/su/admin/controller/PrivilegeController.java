package com.su.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.su.admin.entity.ListData;
import com.su.admin.entity.Privilege;
import com.su.admin.service.privilege.PrivilegeService;
import com.su.common.entity.ResponseMessage;
import com.su.common.entity.SearchParam;
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
        ListData<Privilege> list = privilegeService.getList(param);
        JSONObject json = new JSONObject();
        json.put("count", list.getCount());
        json.put("list", list.getList());
        return ResponseMessage.ok(json);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addPrivilege(@RequestBody Privilege privilege){
        JSONObject jsonObject = privilegeService.insertPojo(privilege);
        return ResponseMessage.ok(jsonObject);
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.DELETE)
    public String deletePrivilege(@PathVariable int pid){
        JSONObject json = privilegeService.deletePojo(pid);
        return ResponseMessage.ok(json);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String updatePrivilege(@RequestBody Privilege privilege){
        JSONObject json = privilegeService.updatePojo(privilege);
        return ResponseMessage.ok(json);
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.GET)
    public String getPrivilege(@PathVariable int pid){
        JSONObject json = privilegeService.getPojo(pid);
        return ResponseMessage.ok(json);
    }

}
