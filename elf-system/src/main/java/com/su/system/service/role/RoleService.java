package com.su.system.service.role;

import com.su.system.entity.Role;
import com.su.system.service.BaseService;

import java.util.List;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午5:30
 * @version
 */
public interface RoleService extends BaseService<Role> {

    int updateRolePrivilege(int roleId, List<Integer> list);

}
