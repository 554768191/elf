package com.su.system.controller;

import com.su.common.entity.ResultMap;
import com.su.sso.service.auth.AuthService;
import com.su.system.entity.Privilege;
import com.su.system.service.privilege.PrivilegeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/")
public class IndexController {

    @Autowired
    PrivilegeService privilegeService;

    @Autowired
    AuthService authService;

    @Value("${app.version}")
    String version;

    @RequestMapping(method = RequestMethod.GET)
    public String welcome(){
        return "welcome to system, version is " + version;
    }

    @RequestMapping("/menu")
    public ResultMap getMenu(HttpServletRequest request){
        List<Privilege> list = privilegeService.getPrivileges();
        String token = authService.fetchToken(request);
        boolean flag = authService.isSuper(token);
        if(flag){
            return ResultMap.ok().put("menus", list);
        }else{
            List<String> ps = authService.getPrivileges(token);
            for(Privilege parent:list){
                List<Privilege> subs = parent.getSubprivileges();
                Iterator<Privilege> it = subs.iterator();
                while(it.hasNext()){
                    Privilege x = it.next();
                    if(!ps.contains(x.getLink())){
                        subs.remove(x);
                    }
                }
            }
        }
        return ResultMap.ok().put("menus", list);
    }

}
