package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Permission;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PermissionDao {
    List<Permission> findPermissionById(Integer id);
    void add(Permission permission);

    Page<Permission> selectByCondition(String queryString);
    @Delete("delete from t_permission where id=#{id}")
    void delete(Integer id);
    @Select("SELECT count(*) FROM t_role_permission WHERE permission_id =#{id}")
    long findCountByPermissionId(Integer id);
    @Select("select * from t_permission where id=#{id}")
    Permission findById(Integer id);

    void edit(Permission permission);
   /* @Select("select * from t_permission")
    List<Permission> findAll();*/
    /*@Select("SELECT * from t_permission WHERE id in (SELECT checkitem_id from t_checkgroup_checkitem WHERE checkgroup_id=#{id})")
    List<Permission> findPermissionListById(Integer id);*/
}
