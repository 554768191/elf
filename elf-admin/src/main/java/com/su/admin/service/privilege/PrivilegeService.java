package com.su.admin.service.privilege;


import com.su.admin.entity.Privilege;
import com.su.admin.service.BaseService;

import java.util.List;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午5:30
 * @version
 */
public interface PrivilegeService extends BaseService<Privilege> {

    List<Privilege> getPrivilegeByRoleId(int roleId);

    List<Privilege> getPrivilegeByParentId(int parentId);

    List<Privilege> getPrivileges();

}
