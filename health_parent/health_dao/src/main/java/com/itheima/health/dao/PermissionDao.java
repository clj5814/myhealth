package com.itheima.health.dao;

import com.itheima.health.pojo.Permission;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionDao {
   List<Permission> findPermissionById(Integer id);
}
