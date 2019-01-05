package com.cat.service.impl;

import com.cat.dao.UserMapper;
import com.cat.pojo.User;
import com.cat.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service("userService")
@Transactional
public class UserServiceImpl implements UserService {
    @Resource(name = "userMapper")
    private UserMapper userMap;
    @Override
    @Transactional(readOnly = true)
    public User login(User user) {
        return userMap.login(user);
    }

    @Override
    @Transactional(readOnly = true)
    public boolean verify(String username) {
        int result = userMap.verify(username);
        if(result>0){
            return true;
        }
        return false;
    }
    @Override
    @Transactional(readOnly = false,propagation = Propagation.REQUIRED,isolation = Isolation.DEFAULT)
    public int regist(User user) {
        return userMap.regist(user);
    }
}
