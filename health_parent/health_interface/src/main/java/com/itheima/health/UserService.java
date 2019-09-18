package com.itheima.health;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.User;

import java.util.List;

public interface UserService {


    User findUserByUsername(String username);

    void add(User user, Integer[] roleIds);

    PageResult findPage(String queryString, Integer currentPage, Integer pageSize);

    void delete(Integer id);

    User findById(Integer id);

    void edit(User user, Integer[] roleIds);

    List<Integer> findRoleIdsById(Integer id);

}
