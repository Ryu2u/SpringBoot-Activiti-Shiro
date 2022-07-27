package com.ryuzu.manage.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.mysql.cj.util.TimeUtil;
import com.ryuzu.common.util.RedisUtils;
import com.ryuzu.manage.dto.LoginDTO;
import com.ryuzu.manage.entity.User;
import com.ryuzu.common.result.ResultCode;
import com.ryuzu.common.result.ResultMsg;
import com.ryuzu.manage.service.impl.UserServiceImpl;
import com.ryuzu.common.util.JwtUtils;
import com.ryuzu.common.util.Md5Utils;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author Ryuzu
 * @date 2022/7/20 14:57
 */
@RestController
@Api(tags = "LoginController")
@Slf4j
public class LoginController {

    @Resource
    private UserServiceImpl userService;
    @Resource
    private JwtUtils jwtUtils;
    @Resource
    private RedisUtils redisUtils;

    @PostMapping("/login")
    @ApiOperation("登陆")
    public ResultMsg login(@RequestBody LoginDTO loginDTO) {
        String username = loginDTO.getUsername();
        String password = loginDTO.getPassword();
        log.info("正在登陆....");
        log.info("username: " + username);
        log.info("password: " + password);
        if (username == null || username == "" || password == null || password == "") {
            return ResultMsg.error(ResultCode.USER_LOGIN_ERROR.code(), ResultCode.USER_LOGIN_ERROR.msg());
        }
        password = Md5Utils.encrypt(username, password);
        User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
        if (user.getPassword().equals(password)) {
            log.info("登录成功！！！username = {},password = {}", username, password);
            // 生成jwtToken并返回
            String token = jwtUtils.generateToken(user, false);
            // 生成用于刷新的refreshToken
            String refreshToken = jwtUtils.generateToken(user, true);
            Map<String, String> map = new HashMap<>(2);
            map.put("Authorization", token);
            map.put("refreshToken", refreshToken);
            // 将token存到redis中
            redisUtils.set(user.getId() + "_token", token);
            redisUtils.expire(user.getId() + "_token", 20, TimeUnit.SECONDS);
            log.info("返回结果为：{}",ResultMsg.success(ResultCode.SUCCESS.code(), ResultCode.SUCCESS.msg(), map));
            return ResultMsg.success(ResultCode.SUCCESS.code(), ResultCode.SUCCESS.msg(), map);
        } else {
            log.warn("登录失败！！！username = {},password = {}", username, password);
            log.info("返回结果为：{}", ResultMsg.error(ResultCode.USER_LOGIN_ERROR.code(), ResultCode.USER_LOGIN_ERROR.msg()));
            return ResultMsg.error(ResultCode.USER_LOGIN_ERROR.code(), ResultCode.USER_LOGIN_ERROR.msg());
        }
    }

    @PostMapping("/refresh")
    @ApiOperation(("刷新token"))
    public ResultMsg refresh(String username, HttpServletRequest request) {
        log.info("正在刷新token....");
        String authorization = request.getHeader("Authorization");
        if (authorization.startsWith("Bearer")) {
            String refreshToken = authorization.substring("Bearer".length() + 1);
            if (jwtUtils.verityExpireTime(refreshToken)) {
                User user = userService.getOne(new QueryWrapper<User>().eq("username", username));
                Map<String, String> map = new HashMap<>(2);
                String token = jwtUtils.generateToken(user, false);
                refreshToken = jwtUtils.generateToken(user, true);
                map.put("token", token);
                map.put("refreshToken", refreshToken);
                return ResultMsg.success(ResultCode.SUCCESS.code(), ResultCode.SUCCESS.msg(), map);
            } else {
                return ResultMsg.error(200, "Token已过期，请重新登录！！");
            }
        } else {
            return ResultMsg.error(ResultCode.RESULE_DATA_NONE.code(), ResultCode.RESULE_DATA_NONE.msg());
        }

    }


    @PostMapping("/logout")
    @ApiOperation("退出登录")
    public ResultMsg logout() {
        SecurityUtils.getSubject().logout();
        return ResultMsg.ok();
    }

    @GetMapping("/test")
    @ApiOperation("测试异常")
    public ResultMsg testException() {
        log.info("进入测试异常方法：testException");
        int i = 1 / 0;
        return ResultMsg.ok();
    }

    @GetMapping("/index")
    @ApiOperation("index页面")
    public ModelAndView getIndex() {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("index");
        return modelAndView;
    }
}
