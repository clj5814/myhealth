package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.UserService;
import com.itheima.health.dao.UserDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service(interfaceClass = UserService.class)
@Transactional
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;


    @Override
    public User findUserByUsername(String username) {
        return userDao.findUserByUsername(username);
    }

    /**
     * 新增角色
     *
     * @param user
     * @param roleIds
     */
    @Override
    public void add(User user, Integer[] roleIds) {
        userDao.add(user);
        setUserAndRole(user.getId(), roleIds);
    }

    /**
     * 新增角色，菜单中间表
     *
     * @param id
     * @param roleIds
     */
    private void setUserAndRole(Integer id, Integer[] roleIds) {
        if (roleIds != null && roleIds.length > 0) {
            for (Integer roleId : roleIds) {
                userDao.setUserAndRole(id, roleId);
            }
        }
    }

    @Override
    public PageResult findPage(String queryString, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        Page<User> page = userDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void delete(Integer id) {
        List<Integer> roleIdsList = userDao.findRoleIdsById(id);
        if (roleIdsList != null && roleIdsList.size() > 0) {
            throw new RuntimeException("当前用户项被角色项引用，不能删除");
        }
        userDao.delete(id);
    }

    @Override
    public User findById(Integer id) {
        return userDao.findById(id);
    }

    @Override
    public void edit(User user, Integer[] roleIds) {
        userDao.edit(user);
        deleteUserAndRole(user.getId());
        setUserAndRole(user.getId(), roleIds);
    }


    private void deleteUserAndRole(Integer id) {
        userDao.deleteUserAndRole(id);
    }

    @Override
    public List<Integer> findRoleIdsById(Integer id) {
        return userDao.findRoleIdsById(id);
    }


}
