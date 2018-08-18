package com.su.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.su.admin.service.privilege.PrivilegeService;
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
@RequestMapping("/privilege")
public class PrivilegeController {

    //private static final Logger logger = LoggerFactory.getLogger(PrivilegeController.class);

    @Autowired
    private PrivilegeService privilegeService;


    @RequestMapping(method = RequestMethod.GET)
    public ResponseMessage getPrivilegeList(SearchParam param){
        param.setOffset((param.getPage()-1)*param.getLimit());
        JSONObject json = privilegeService.getList(param);
        return ResponseMessage.ok(json.getJSONArray("list")).put("count", json.getInteger("count"));
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResponseMessage addPrivilege(@RequestBody JSONObject privilege){
        JSONObject jsonObject = privilegeService.insertPojo(privilege);
        return ResponseMessage.ok(jsonObject);
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.DELETE)
    public ResponseMessage deletePrivilege(@PathVariable int pid){
        JSONObject json = privilegeService.deletePojo(pid);
        return ResponseMessage.ok(json);
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResponseMessage updatePrivilege(@RequestBody JSONObject privilege){
        JSONObject json = privilegeService.updatePojo(privilege);
        return ResponseMessage.ok(json);
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.GET)
    public ResponseMessage getPrivilege(@PathVariable int pid){
        JSONObject json = privilegeService.getPojo(pid);
        return ResponseMessage.ok(json);
    }

}
