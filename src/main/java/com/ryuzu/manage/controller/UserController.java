package com.ryuzu.manage.controller;


import com.ryuzu.common.result.ResultMsg;
import com.ryuzu.manage.entity.User;
import com.ryuzu.manage.service.impl.UserServiceImpl;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import org.apache.catalina.security.SecurityUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * <p>
 * 前端控制器
 * </p>
 *
 * @author ryuzu
 * @since 2022-07-20
 */
@RestController
@RequestMapping("/user")
@Api(tags = "UserController")
public class UserController {

    @Resource
    private UserServiceImpl userService;

    @GetMapping("/info")
    @ApiOperation("获取当前用户信息")
    public User getUserInfo() {
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        return user;
    }
}
