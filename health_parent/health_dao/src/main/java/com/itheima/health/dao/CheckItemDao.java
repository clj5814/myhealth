package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.CheckItem;
import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckItemDao {
    void add(CheckItem checkItem);

    Page<CheckItem> selectByCondition(String queryString);
    @Delete("delete from t_checkitem where id=#{id}")
    void delete(Integer id);
    @Select("SELECT count(*) FROM t_checkgroup_checkitem WHERE checkitem_id =#{id}")
    long findCountByCheckItemId(Integer id);
    @Select("select * from t_checkitem where id=#{id}")
    CheckItem findById(Integer id);

    void edit(CheckItem checkItem);
    @Select("select * from t_checkitem")
    List<CheckItem> findAll();
    @Select("SELECT * from t_checkitem WHERE id in (SELECT checkitem_id from t_checkgroup_checkitem WHERE checkgroup_id=#{id})")
    List<CheckItem> findCheckItemListById(Integer id);
}
