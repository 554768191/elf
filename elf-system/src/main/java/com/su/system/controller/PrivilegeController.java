package com.su.system.controller;

import com.su.common.entity.ResultMap;
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
    public ResultMap getPrivilegeList(SearchParam param){
        param.setOffset((param.getPage()-1)*param.getLimit());
        List<Privilege> list = privilegeService.getList(param);
        int total = privilegeService.getCount(param);
        return ResultMap.ok().put("count", total).put("data", list);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResultMap addPrivilege(@RequestBody Privilege privilege){
        privilege = privilegeService.insertPojo(privilege);
        return ResultMap.ok().put("id", privilege.getId());
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.DELETE)
    public ResultMap deletePrivilege(@PathVariable int pid){
        privilegeService.deletePojo(pid);
        return ResultMap.ok();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResultMap updatePrivilege(@RequestBody Privilege privilege){
        privilegeService.updatePojo(privilege);
        return ResultMap.ok();
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.GET)
    public ResultMap getPrivilege(@PathVariable int pid){
        Privilege privilege = privilegeService.getPojo(pid);
        return ResultMap.ok().put("privilege", privilege);
    }

}
