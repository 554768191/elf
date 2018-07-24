package com.su.system.service.log.impl;

import com.su.common.entity.SearchParam;
import com.su.system.entity.Log;
import com.su.system.mapper.LogMapper;
import com.su.system.service.log.LogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午5:37
 * @version
 */
@Service
public class LogServiceImpl implements LogService {

    @Autowired
    LogMapper logMapper;


    @Override
    public List<Log> getList(SearchParam params) {
        return logMapper.getList(params);
    }

    @Override
    public int getCount(SearchParam params) {
        return logMapper.getCount(params);
    }

    @Override
    public Log getPojo(int id) {
        return logMapper.get(id);
    }

    @Override
    public Log insertPojo(Log pojo) {
        int id = logMapper.insert(pojo);
        pojo.setId(id);
        return pojo;
    }

    @Override
    public Log updatePojo(Log pojo) {
        logMapper.update(pojo);
        return pojo;
    }

    @Override
    public int deletePojo(int id) {
        return logMapper.delete(id);
    }

}
