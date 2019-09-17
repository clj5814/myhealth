package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Role;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RoleDao {
    List<Role> findRoleById(Integer id);

    void add(Role role);

    Page<Role> selectByCondition(String queryString);

    @Delete("delete from t_role where id=#{id}")
    void delete(Integer id);

    @Select("SELECT count(*) FROM t_role_menu WHERE menu_id =#{id}")
    long findCountByRoleId(Integer id);

    @Select("select * from t_role where id=#{id}")
    Role findById(Integer id);

    void edit(Role role);

}
