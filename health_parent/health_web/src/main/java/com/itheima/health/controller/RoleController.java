package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.RoleService;
import com.itheima.health.common.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Role;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/role")
public class RoleController {
    @Reference
    private RoleService roleService;


    /**
     * 查询所有，回显用户编辑数据
     *
     * @return
     */
    @RequestMapping("/findAll")
//    @PreAuthorize("hasAnyAuthority('Role_QUERY')")
    public Result findAll() {
        try {
            List<Role> list = roleService.findAll();
            return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_ROLE_FAIL);
        }

    }

    /**
     * 编辑角色
     *
     * @param role
     * @return
     */
    @RequestMapping("/edit")
//    @PreAuthorize("hasAnyAuthority('Role_EDIT')")
    public Result edit(@RequestBody Role role,Integer[] menuIds,Integer[] permissionIds) {
        try {
            roleService.edit(role,menuIds,permissionIds);
            return new Result(true, MessageConstant.EDIT_ROLE_SUCCESS, role);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.EDIT_ROLE_FAIL);
        }
    }

    /**
     * 查询角色，回显编辑数据
     *
     * @param id
     * @return
     */
    @RequestMapping("/findById")
//    @PreAuthorize("hasAnyAuthority('Role_QUERY')")
    public Result findById(Integer id) {
        try {
            Role role = roleService.findById(id);
            return new Result(true, MessageConstant.QUERY_ROLE_SUCCESS,role);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_ROLE_FAIL);
        }

    }

    /**
     *编辑回显菜单id
     * @param id
     * @return
     */
    @RequestMapping("/findmenuIdsById")
    public List<Integer> findMenuIdsById(Integer id){
        return roleService.findMenuIdsById(id);
    }
    /**
     *编辑回显权限id
     * @param id
     * @return
     */
    @RequestMapping("/findpermissionIdsById")
    public List<Integer> findPermissionIdsById(Integer id){
        return roleService.findPermissionIdsById(id);
    }

    /**
     * 删除角色
     *
     * @param id
     * @return
     */
//    @PreAuthorize("hasAnyAuthority('Role_DELETE')")
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            roleService.delete(id);
            return new Result(true, MessageConstant.DELETE_ROLE_SUCCESS);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_ROLE_FAIL);
        }
    }

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
//    @PreAuthorize("hasAnyAuthority('Role_QUERY')")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = roleService.findPage(queryPageBean.getQueryString(), queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        return pageResult;
    }

    /**
     * 新增角色
     *
     * @param role
     * @return
     */
    @RequestMapping("/add")
//    @PreAuthorize("hasAnyAuthority('Role_ADD')")
    public Result add(@RequestBody Role role,Integer[] menuIds,Integer[] permissionIds) {
        try {
            roleService.add(role,menuIds,permissionIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_ROLE_FAIL);
        }
        return new Result(true, MessageConstant.ADD_ROLE_SUCCESS);
    }
}
