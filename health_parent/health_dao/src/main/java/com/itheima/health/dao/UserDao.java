package com.itheima.health.dao;

import com.itheima.health.pojo.User;
import org.springframework.stereotype.Repository;

@Repository
public interface UserDao {
    User findUserByUsername(String username);
}
