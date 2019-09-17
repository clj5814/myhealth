package com.itheima.health;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Role;

public interface RoleService {
    void add(Role role);

    PageResult findPage(String queryString, Integer currentPage, Integer pageSize);

    void delete(Integer id);

    Role findById(Integer id);

    void edit(Role role);
}
