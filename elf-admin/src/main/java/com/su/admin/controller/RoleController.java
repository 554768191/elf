package com.su.admin.controller;

import com.su.admin.entity.Role;
import com.su.admin.service.role.RoleService;
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
@RequestMapping("/role")
public class RoleController {

    //private static final Logger logger = LoggerFactory.getLogger(RoleController.class);

    @Autowired
    RoleService roleService;
    /*
    @RequestMapping(method = RequestMethod.GET)
    public ResultMap getRoleList(SearchParam param){
        param.setOffset((param.getPage()-1)*param.getLimit());
        List<Role> list = roleService.getList(param);
        int total = roleService.getCount(param);
        return ResultMap.ok().put("count", total).put("data", list);
    }

    @RequestMapping(method = RequestMethod.POST)
    public ResultMap addRole(@RequestBody Role role){
        role = roleService.insertPojo(role);

        return ResultMap.ok().put("id", role.getId());
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.DELETE)
    public ResultMap deleteRole(@PathVariable int pid){
        roleService.deletePojo(pid);
        return ResultMap.ok();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public ResultMap updateRole(@RequestBody Role role){
        roleService.updatePojo(role);
        return ResultMap.ok();
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.GET)
    public ResultMap getRole(@PathVariable int pid){
        Role role = roleService.getPojo(pid);
        return ResultMap.ok().put("role", role);
    }
    */
}
