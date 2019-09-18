package com.itheima.health;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Permission;

import java.util.List;

public interface PermissionService {
    void add(Permission permission);

    PageResult findPage(String queryString, Integer currentPage, Integer pageSize);

    void delete(Integer id);

    Permission findById(Integer id);

    void edit(Permission permission);
    List<Permission> findAll();
}
