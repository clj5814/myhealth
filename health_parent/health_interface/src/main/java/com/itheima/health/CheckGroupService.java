package com.itheima.health;

import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckGroup;

import java.util.List;

public interface CheckGroupService {

    void add(CheckGroup checkGroup, Integer[] checkitemIds);

    PageResult findPage(String queryString, Integer currentPage, Integer pageSize);

    CheckGroup findById(Integer id);

    void edit(CheckGroup checkGroup, Integer[] checkitemIds);

    List<Integer> findCheckitemIdsById(Integer id);

    void delete(Integer id);

    List<CheckGroup> findAll();
}
