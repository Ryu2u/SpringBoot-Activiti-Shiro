package com.ryuzu.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
 * 由于activiti7默认集成了security
 * 所以需要配置security,放行所有请求,并且不需要登录验证
 * 由shiro来处理
 * @author Ryuzu
 * @date 2022/7/26 14:44
 */
@Configuration
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.authorizeRequests()
                .anyRequest()
                .permitAll()
                .and()
                .logout()
                // 配置所有请求security放行,不拦截
                .permitAll()
                .and()
                .headers()
                .frameOptions()
                // 解决frame because it set 'X-Frame-Options' to 'deny'
                .sameOrigin()
                .and()
                .csrf()
                .disable();
    }
}
