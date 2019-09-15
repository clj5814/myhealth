package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.UserService;
import com.itheima.health.dao.UserDao;
import com.itheima.health.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;


    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }
}
