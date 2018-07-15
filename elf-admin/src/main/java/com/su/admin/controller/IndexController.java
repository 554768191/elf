package com.su.admin.controller;

import com.alibaba.fastjson.JSONObject;
import com.su.admin.entity.Privilege;
import com.su.admin.service.privilege.PrivilegeService;
import com.su.common.entity.ResponseMessage;
import com.su.sso.service.auth.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.Iterator;
import java.util.List;

@RestController
@RequestMapping("/")
public class IndexController {

    @Autowired
    PrivilegeService privilegeService;

    @Resource
    AuthService authService;

    @Value("${app.version}")
    String version;

    @RequestMapping()
    public String welcome(){
        return "welcome to system, version is " + version;
    }

    @RequestMapping("/menu")
    public String getMenu(HttpServletRequest request){
        List<Privilege> list = privilegeService.getPrivileges();
        String token = authService.fetchToken(request);
        boolean flag = authService.isSuper(token);
        JSONObject data = new JSONObject();

        if(flag){
            data.put("menus", list);
            return ResponseMessage.ok(data);
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
        data.put("menus", list);
        return ResponseMessage.ok(data);
    }


}
