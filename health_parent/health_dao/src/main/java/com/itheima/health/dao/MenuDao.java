package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Menu;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MenuDao {

    void add(Menu menu);

    Page<Menu> selectByCondition(String queryString);

    @Delete("delete from t_menu where id=#{id}")
    void delete(Integer id);

    @Select("SELECT count(*) FROM t_role_menu WHERE menu_id =#{id}")
    long findCountByMenuId(Integer id);

    @Select("select * from t_menu where id=#{id}")
    Menu findById(Integer id);

    void edit(Menu menu);

    @Select("select * from t_menu")
    List<Menu> findAll();
    /*@Select("SELECT * from t_permission WHERE id in (SELECT checkitem_id from t_checkgroup_checkitem WHERE checkgroup_id=#{id})")
    List<Permission> findPermissionListById(Integer id);*/
}
