package com.ryuzu.common.config;

import org.apache.shiro.authc.AuthenticationToken;

/**
 * @author Ryuzu
 * @date 2022/7/21 9:39
 */
public class JwtToken implements AuthenticationToken {

    private String token;

    public JwtToken(String token) {
        this.token = token;
    }

    @Override
    public Object getPrincipal() {
        return token;
    }

    @Override
    public Object getCredentials() {
        return token;
    }
}
