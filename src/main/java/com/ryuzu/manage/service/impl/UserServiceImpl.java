package com.ryuzu.manage.service.impl;

import com.ryuzu.manage.entity.User;
import com.ryuzu.manage.mapper.UserMapper;
import com.ryuzu.manage.service.IUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author ryuzu
 * @since 2022-07-20
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

}
