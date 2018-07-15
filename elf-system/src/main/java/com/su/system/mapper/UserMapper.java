package com.su.system.mapper;

import com.su.common.mapper.BaseMapper;
import com.su.system.entity.User;
import org.apache.ibatis.annotations.Mapper;


@Mapper
public interface UserMapper extends BaseMapper<User> {

	User getByName(String account);

}
