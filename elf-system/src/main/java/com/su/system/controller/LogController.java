package com.su.system.controller;

import com.alibaba.fastjson.JSONObject;
import com.su.common.entity.ResponseMessage;
import com.su.common.entity.SearchParam;
import com.su.system.entity.Log;
import com.su.system.service.log.LogService;
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
@RequestMapping("/log")
public class LogController {

   // private static final Logger logger = LoggerFactory.getLogger(UserController.class);

    @Autowired
    LogService logService;

    @RequestMapping(method = RequestMethod.GET)
    public String getLogList(SearchParam param){
        param.setOffset((param.getPage()-1)*param.getLimit());
        List<Log> list = logService.getList(param);
        int total = logService.getCount(param);
        JSONObject json = new JSONObject();
        json.put("count", total);
        json.put("list", list);
        return ResponseMessage.ok(json);
    }

    @RequestMapping(method = RequestMethod.POST)
    public String addLog(@RequestBody Log log){
        log = logService.insertPojo(log);
        JSONObject json = new JSONObject();
        json.put("id", log.getId());
        return ResponseMessage.ok(json);
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.DELETE)
    public String deleteLog(@PathVariable int pid){
        logService.deletePojo(pid);
        //responseMessage.setData(result);
        return ResponseMessage.ok();
    }

    @RequestMapping(method = RequestMethod.PUT)
    public String updateRole(@RequestBody Log log){
        logService.updatePojo(log);
        return ResponseMessage.ok();
    }

    @RequestMapping(value = "/{pid}", method = RequestMethod.GET)
    public String getUser(@PathVariable int pid){
        Log log = logService.getPojo(pid);
        JSONObject json = new JSONObject();
        json.put("log", log);
        return ResponseMessage.ok(json);
    }

}
