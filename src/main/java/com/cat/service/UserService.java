package com.cat.service;

import com.cat.pojo.User;

import javax.swing.*;

public interface UserService {
    /**
     * 用户登录
     * @param user 用户
     * @return 用户编号
     */
    User login(User user);

    /**
     * 用户验证
     * @param username 用户名
     * @return 执行结果
     */
    boolean verify(String username);
    /**
     * 用户注册
     * @param user 用户
     * @return 执行结果
     */
    int regist(User user);
}
