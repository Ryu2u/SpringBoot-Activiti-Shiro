package com.ryuzu.common.util;

import org.apache.shiro.crypto.hash.SimpleHash;

/**
 * MD5 工具类
 * 用于加密密码
 * 和校验密码
 * @author Ryuzu
 * @date 2022/7/20 16:10
 */
public class Md5Utils {
    /**
     * 加密的盐
     */
    private static final String SALT = "ryuzu";
    /**
     * 加密算法名称
     */
    private static final String ALGORITHM_NAME = "md5";
    /**
     * 迭代次数
     */
    private static final Integer HASH_ITERATIONS = 100;

    public static String encrypt(String username,String password){
        String encryptString = new SimpleHash(ALGORITHM_NAME, password, username + SALT, HASH_ITERATIONS).toHex();
        return encryptString;
    }




}
