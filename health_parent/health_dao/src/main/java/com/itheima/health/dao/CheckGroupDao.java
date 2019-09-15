package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckGroup;
import org.apache.ibatis.annotations.Delete;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckGroupDao {
    void add(CheckGroup checkGroup);

    void setCheckGroupAndCheckItem(@Param(value = "groupId") Integer id,@Param(value = "itemId") Integer checkitemId);

    Page<CheckGroup> findByCondition(String queryString);

    @Select("SELECT*from t_checkgroup WHERE id = #{id}")
    CheckGroup findById(Integer id);

    void edit(CheckGroup checkGroup);
    @Delete("delete from t_checkgroup_checkitem WHERE checkgroup_id = #{id}")
    void deleteCheckGroupAndCheckItem(Integer id);

    List<Integer> findCheckitemIdsById(Integer id);
    @Delete("delete from t_checkgroup WHERE id = #{id}")
    void delete(Integer id);

    long findCheckGroupAndCheckItemById(Integer id);

    List<CheckGroup> findAll();
    List<CheckGroup> findCheckGroupListBySetmealId(Integer id);
}
