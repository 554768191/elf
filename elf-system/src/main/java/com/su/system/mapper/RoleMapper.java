package com.su.system.mapper;

import com.su.common.mapper.BaseMapper;
import com.su.system.entity.Role;
import com.su.system.entity.RolePrivilege;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper extends BaseMapper<Role> {

    int deletePrivilege(int roleId);

    void batchInsertRolePrivilege(List<RolePrivilege> list);

}
