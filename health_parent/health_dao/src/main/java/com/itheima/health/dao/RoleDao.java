package com.itheima.health.dao;

import com.itheima.health.pojo.Role;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao {
      List<Role> findRoleById(Integer id);
}
