package com.itheima.health;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Role;

import java.util.List;

public interface RoleService {
    void add(Role role, Integer[] menuIds, Integer[] permissionIds);

    PageResult findPage(String queryString, Integer currentPage, Integer pageSize);

    void delete(Integer id);

    Role findById(Integer id);

    void edit(Role role, Integer[] menuIds, Integer[] permissionIds);

    List<Integer> findMenuIdsById(Integer id);

    List<Integer> findPermissionIdsById(Integer id);

    List<Role> findAll();
}
