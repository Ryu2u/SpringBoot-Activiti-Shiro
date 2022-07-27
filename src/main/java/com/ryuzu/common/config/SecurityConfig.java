package com.ryuzu.common.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

/**
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
