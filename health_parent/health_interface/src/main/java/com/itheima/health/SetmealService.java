package com.itheima.health;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Setmeal;

import java.util.List;
import java.util.Map;

public interface SetmealService {


    PageResult findPage(String queryString, Integer currentPage, Integer pageSize);

    void add(Setmeal setmeal,Integer[] checkgroupIds);

    List<Setmeal> findAll() throws Exception;

    Setmeal findById(Integer id) throws Exception;

    Map<String, Object> getSetmealReport();

}
