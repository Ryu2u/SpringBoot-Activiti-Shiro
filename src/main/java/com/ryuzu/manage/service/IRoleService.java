package com.ryuzu.manage.service;

import com.ryuzu.manage.entity.Role;
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
public interface IRoleService extends IService<Role> {

    /**
     * 获取用户的角色权限
     * @param username
     * @return
     */
    List<Role> findRoleByUsername(String username);

}
