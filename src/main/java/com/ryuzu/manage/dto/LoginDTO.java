package com.ryuzu.manage.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Ryuzu
 * @date 2022/7/25 10:34
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class LoginDTO implements Serializable {
    private static final long serialVersionUID = -1290639213488396253L;
    /**
     * 用户名
     */
    private String username;
    /**
     * 密码
     */
    private String password;

}
