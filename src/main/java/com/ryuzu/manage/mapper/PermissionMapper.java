package com.ryuzu.manage.mapper;

import com.ryuzu.manage.entity.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author ryuzu
 * @since 2022-07-20
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    /**
     * 查询角色的权限
     * @param roleId
     * @return
     */
    List<Permission> findPermissionByRoleId(Integer roleId);
}
