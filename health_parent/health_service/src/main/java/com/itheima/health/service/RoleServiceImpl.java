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

import java.util.List;

@Service(interfaceClass = RoleService.class)
@Transactional
public class RoleServiceImpl implements RoleService {
    @Autowired
    private RoleDao roleDao;

    /**
     * 新增角色
     * @param role
     * @param menuIds
     * @param permissionIds
     */
    @Override
    public void add(Role role, Integer[] menuIds, Integer[] permissionIds) {
        roleDao.add(role);
        setRoleAndMenu(role.getId(), menuIds);
        setRoleAndPermission(role.getId(), permissionIds);
    }

    /**
     * 新增角色，权限中间表
     * @param id
     * @param permissionIds
     */
    private void setRoleAndPermission(Integer id, Integer[] permissionIds) {
        if (permissionIds != null && permissionIds.length > 0) {
            for (Integer permissionId : permissionIds) {
                roleDao.setRoleAndPermission(id, permissionId);
            }
        }
    }

    /**
     * 新增角色，菜单中间表
     * @param id
     * @param menuIds
     */
    private void setRoleAndMenu(Integer id, Integer[] menuIds) {
        if (menuIds != null && menuIds.length > 0) {
            for (Integer menuId : menuIds) {
                roleDao.setRoleAndMenu(id, menuId);
            }
        }
    }

    @Override
    public PageResult findPage(String queryString, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        Page<Role> page = roleDao.selectByCondition(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void delete(Integer id) {
        List<Integer> menuIdsList = roleDao.findMenuIdsById(id);
        List<Integer> permissionIdsList = roleDao.findPermissionIdsById(id);
        if (menuIdsList!=null && menuIdsList.size()>0) {
            throw new RuntimeException("当前角色项被菜单项引用，不能删除");
        }
        if (permissionIdsList!=null && permissionIdsList.size()>0) {
            throw new RuntimeException("当前角色项被权限项引用，不能删除");
        }
        roleDao.delete(id);
    }

    @Override
    public Role findById(Integer id) {
        return roleDao.findById(id);
    }

    @Override
    public void edit(Role role, Integer[] menuIds, Integer[] permissionIds) {
        roleDao.edit(role);
        deleteRoleAndMenu(role.getId());
        deleteRoleAndPermission(role.getId());
        setRoleAndMenu(role.getId(), menuIds);
        setRoleAndPermission(role.getId(), permissionIds);
    }

    private void deleteRoleAndPermission(Integer id) {
                roleDao.deleteRoleAndPermission(id);
    }

    private void deleteRoleAndMenu(Integer id) {
                roleDao.deleteRoleAndMenu(id);
    }

    @Override
    public List<Integer> findMenuIdsById(Integer id) {
        return roleDao.findMenuIdsById(id);
    }

    @Override
    public List<Integer> findPermissionIdsById(Integer id) {
        return roleDao.findPermissionIdsById(id);
    }

    @Override
    public List<Role> findAll() {
        return roleDao.findAll();
    }

}
