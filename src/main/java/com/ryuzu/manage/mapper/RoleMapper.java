package com.ryuzu.manage.mapper;

import com.ryuzu.manage.entity.Role;
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
public interface RoleMapper extends BaseMapper<Role> {

    /**
     * 获取用户的角色权限
     * @param username
     * @return
     */
    List<Role> findRoleByUsername(String username);


}
