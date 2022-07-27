package com.ryuzu.manage.service.impl;

import com.ryuzu.manage.entity.Role;
import com.ryuzu.manage.mapper.RoleMapper;
import com.ryuzu.manage.service.IRoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ryuzu
 * @since 2022-07-20
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements IRoleService {

    @Resource
    private RoleMapper roleMapper;

    @Override
    public List<Role> findRoleByUsername(String username) {
        return roleMapper.findRoleByUsername(username);
    }
}
