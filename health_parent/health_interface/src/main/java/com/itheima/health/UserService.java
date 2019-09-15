package com.itheima.health;

import com.itheima.health.pojo.User;

public interface UserService {


    User findUserByUsername(String username);
}
