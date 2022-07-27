package com.ryuzu.common.config.shiro;

import com.ryuzu.common.config.JwtToken;
import com.ryuzu.manage.entity.User;
import com.ryuzu.manage.service.impl.PermissionServiceImpl;
import com.ryuzu.manage.service.impl.RoleServiceImpl;
import com.ryuzu.manage.service.impl.UserServiceImpl;
import com.ryuzu.common.util.JwtUtils;
import io.jsonwebtoken.Claims;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

import javax.annotation.Resource;

/**
 * @author Ryuzu
 * @date 2022/7/21 9:36
 */

public class ShiroRealm extends AuthorizingRealm {

    @Resource
    private UserServiceImpl userService;
    @Resource
    private RoleServiceImpl roleService;
    @Resource
    private PermissionServiceImpl permissionService;
    @Resource
    private JwtUtils jwtUtils;


    /**
     * 判断传入的token是否是jwtToken
     *
     * @param token
     * @return
     */
    @Override
    public boolean supports(AuthenticationToken token) {
        return token instanceof JwtToken;
    }

    /**
     * 获取用户身份信息
     *
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        User user = (User) principals.getPrimaryPrincipal();
        roleService.findRoleByUsername(user.getUsername()).stream().forEach(
                role -> {
                    authorizationInfo.addRole(role.getRole());
                    permissionService.findPermissionByRoleId(role.getId()).stream().forEach(
                            permission -> {
                                authorizationInfo.addStringPermission(permission.getPermission());
                            }
                    );
                });
        return authorizationInfo;
    }

    /**
     * 获取用户认证信息
     *
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        JwtToken jwtToken = (JwtToken) token;
        Claims claims = jwtUtils.getClaimsFromToken(jwtToken.getPrincipal().toString());
        if (claims != null) {
            Integer id = (Integer) claims.get("id");
            if (id != null) {
                User user = userService.getById(id);
                if (!"0".equals(user.getStatus())) {
                    return new SimpleAuthenticationInfo(user, jwtToken.getCredentials(), getName());
                }else{
                    throw new LockedAccountException("账号已被锁定,请联系管理员!");
                }
            }
        }
        throw new UnknownAccountException("用户名或密码错误!!");


    }
}
