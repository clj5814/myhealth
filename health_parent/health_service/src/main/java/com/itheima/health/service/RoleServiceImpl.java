package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.RoleService;
import com.itheima.health.dao.RoleDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Role;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    @Override
    public void add(Role role) {
        roleDao.add(role);
    }

    @Override
    public PageResult findPage(String queryString, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        Page<Role> page = roleDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void delete(Integer id) {
        long count = roleDao.findCountByRoleId(id);
        if (count > 0) {
            throw new RuntimeException("当前检查项被检查组引用，不能删除");
        }
        roleDao.delete(id);
    }

    @Override
    public Role findById(Integer id) {
        return roleDao.findById(id);
    }

    @Override
    public void edit(Role role) {
        roleDao.edit(role);
    }
    
}
