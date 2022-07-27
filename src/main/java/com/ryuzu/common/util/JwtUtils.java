package com.ryuzu.common.util;

import com.ryuzu.manage.entity.User;
import io.jsonwebtoken.*;
import org.apache.shiro.authc.pam.UnsupportedTokenException;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Ryuzu
 * @date 2022/7/20 17:28
 */
@Component
public class JwtUtils {

    /**
     * 发行者
     */
    private static final String SUB_SUBJECT = "sub";
    /**
     * 创建时间
     */
    private static final String CREATE_TIME = "create";
    /**
     * 失效时间(60秒)
     */
    private static final long EXPIRE = 1000 * 60;
    /**
     * 刷新token失效时间
     * 30分钟
     */
    private static final long REFRESH_EXPIRE = 1000 * 60*30;
    /**
     * 加密盐
     */
    private static final String SECRET_SALT = "ryuzu";

    /**
     * 传进一个用户名
     * 返回生成的token
     *  true 生成刷新token
     * @param user
     * @param isRefresh 是否是否是刷新token
     * @return
     */
    public String generateToken(User user, Boolean isRefresh) {
        String username = user.getUsername();
        Map<String, Object> claims = new HashMap<>(16);
        claims.put("id", user.getId());
        claims.put(SUB_SUBJECT, username);
        claims.put(CREATE_TIME, new Date());
        if (!isRefresh) {
            return Jwts.builder()
                    .addClaims(claims)
                    .setExpiration(new Date(System.currentTimeMillis() + EXPIRE))
                    .signWith(SignatureAlgorithm.HS512, SECRET_SALT)
                    .compact();
        } else {
            // 生成刷新token,过期时间更长
            return Jwts.builder()
                    .addClaims(claims)
                    .setExpiration(new Date(System.currentTimeMillis() + REFRESH_EXPIRE))
                    .signWith(SignatureAlgorithm.HS512, SECRET_SALT)
                    .compact();
        }
    }

    /**
     * 验证token
     *
     * @param token
     * @return
     */
    public Boolean verityToken(String token, String username) {
        if (verityExpireTime(token)) {
            String tokenUser = getUsernameFromToken(token);
            if (tokenUser.equals(username)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 验证token是否过期
     *
     * @param token
     * @return
     */
    public boolean verityExpireTime(String token) {
        Claims claims = getClaimsFromToken(token);
        if (claims != null) {
            Date expire = claims.getExpiration();
            Date now = new Date();
            if (now.before(expire)) {
                // 没过期
                return true;
            } else {
                return false;
            }
        }
        throw new UnsupportedTokenException("Token已失效!");
    }

    /**
     * 从Token中获取用户名
     *
     * @param token
     * @return
     */
    public String getUsernameFromToken(String token) {
        Claims claims = getClaimsFromToken(token);
        String username = claims.getSubject();
        return username;
    }

    /**
     * 从token中获取Claims
     *
     * @param token
     * @return
     */
    public Claims getClaimsFromToken(String token) {
        Claims claims;
        try {
            claims = Jwts.parser()
                    .setSigningKey(SECRET_SALT)
                    .parseClaimsJws(token)
                    .getBody();
        } catch (Exception e) {
            claims = null;
            e.printStackTrace();
        }
        return claims;
    }

    /**
     * token是否可以刷新
     * @param username
     * @param refreshToken
     * @return
     */
    public Boolean canRefreshToken(String refreshToken,String username) {
        return verityToken(refreshToken,username);
    }


}
