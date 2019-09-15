package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.CheckGroupService;
import com.itheima.health.dao.CheckGroupDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.CheckGroup;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = CheckGroupService.class)
@Transactional
public class CheckGroupServiceImpl implements CheckGroupService {
    @Autowired
    private CheckGroupDao checkGroupDao;

    @Override
    public void add(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.add(checkGroup);
        setCheckGroupAndCheckItem(checkGroup.getId(), checkitemIds);
    }

    @Override
    public PageResult findPage(String queryString, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        Page<CheckGroup> page = checkGroupDao.findByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public CheckGroup findById(Integer id) {
        return checkGroupDao.findById(id);
    }

    @Override
    public void edit(CheckGroup checkGroup, Integer[] checkitemIds) {
        checkGroupDao.edit(checkGroup);
        deleteCheckGroupAndCheckItem(checkGroup.getId());
        setCheckGroupAndCheckItem(checkGroup.getId(), checkitemIds);
    }

    @Override
    public List<Integer> findCheckitemIdsById(Integer id) {
        return checkGroupDao.findCheckitemIdsById(id);
    }

    @Override
    public void delete(Integer id) {
        long count = findCheckGroupAndCheckItemById(id);
        if (count > 0) {
            throw new RuntimeException("当前检查项被检查组引用，不能删除");
        } else {
            checkGroupDao.delete(id);
        }
    }

    @Override
    public List<CheckGroup> findAll() {
        return checkGroupDao.findAll();
    }

    private long findCheckGroupAndCheckItemById(Integer id) {
        return checkGroupDao.findCheckGroupAndCheckItemById(id);
    }

    private void deleteCheckGroupAndCheckItem(Integer id) {
        checkGroupDao.deleteCheckGroupAndCheckItem(id);
    }

    private void setCheckGroupAndCheckItem(Integer id, Integer[] checkitemIds) {
        if (checkitemIds != null && checkitemIds.length > 0) {
            for (Integer checkitemId : checkitemIds) {
                checkGroupDao.setCheckGroupAndCheckItem(id, checkitemId);
            }
        }
    }
}
