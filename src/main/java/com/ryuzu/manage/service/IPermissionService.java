package com.ryuzu.manage.service;

import com.ryuzu.manage.entity.Permission;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author ryuzu
 * @since 2022-07-20
 */
public interface IPermissionService extends IService<Permission> {
    /**
     * 查询角色的权限
     * @param roleId
     * @return
     */
    List<Permission> findPermissionByRoleId(Integer roleId);

}
