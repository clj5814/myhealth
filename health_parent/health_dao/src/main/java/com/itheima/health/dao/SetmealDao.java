package com.itheima.health.dao;

import com.github.pagehelper.Page;
import com.itheima.health.pojo.Setmeal;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface SetmealDao {

    Page<Setmeal> findPage(String queryString);

    void add(Setmeal setmeal);

    void setSetmealAndCheckGroup(@Param("id") Integer id, @Param("checkgroupId") Integer checkgroupId);
    @Select("SELECT * FROM t_setmeal")
    List<Setmeal> findAll();

    Setmeal findById(Integer id);
    @Select("SELECT s.name,count(o.id) value from t_order o,t_setmeal s WHERE o.setmeal_id=s.id GROUP BY s.`name`")
    List<Map<String, Object>> getSetmealReport();
}
