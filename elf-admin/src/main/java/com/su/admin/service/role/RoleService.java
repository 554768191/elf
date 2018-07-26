package com.su.admin.service.role;



import com.su.admin.entity.RolePrivilege;
import com.su.admin.service.BaseService;

import java.util.List;

/**
 * @Desc
 * @author surongyao
 * @date 2018/5/25 下午5:30
 * @version
 */
public interface RoleService extends BaseService {

    int deletePrivilege(int roleId);

    void batchInsertRolePrivilege(List<RolePrivilege> list);

}
