package com.itheima.health;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Menu;

import java.util.List;

public interface MenuService {
    void add(Menu menu);

    PageResult findPage(String queryString, Integer currentPage, Integer pageSize);

    void delete(Integer id);

    Menu findById(Integer id);

    void edit(Menu menu);
    List<Menu> findAll();
}
