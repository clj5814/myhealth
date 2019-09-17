package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.PermissionService;
import com.itheima.health.common.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Permission;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/permission")
public class PermissionController {
    @Reference
    private PermissionService permissionService;

    /**
     * 查找所有
     * @return
     *//*
    @RequestMapping("/findAll")
//    @PreAuthorize("hasAnyAuthority('Permission_QUERY')")
    public Result findAll(){
        try {
            List<Permission> list=permissionService.findAll();
            return new Result(true,MessageConstant.QUERY_Permission_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_Permission_FAIL);
        }
    }*/

    /* */

    /**
     * 编辑权限
     *
     * @param Permission
     * @return
     */
    @RequestMapping("/edit")
//    @PreAuthorize("hasAnyAuthority('PERMISSION_EDIT')")
    public Result edit(@RequestBody Permission Permission) {
        try {
            permissionService.edit(Permission);
            return new Result(true, MessageConstant.EDIT_Permission_SUCCESS, Permission);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.EDIT_Permission_FAIL);
        }
    }

    /**
     * 查询权限
     *
     * @param id
     * @return
     */
    @RequestMapping("/fingById")
//    @PreAuthorize("hasAnyAuthority('PERMISSION_QUERY')")
    public Result findById(Integer id) {
        try {
            Permission Permission = permissionService.findById(id);
            return new Result(true, MessageConstant.QUERY_Permission_SUCCESS, Permission);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_Permission_FAIL);
        }

    }

    /**
     * 删除权限
     *
     * @param id
     * @return
     */
//    @PreAuthorize("hasAnyAuthority('PERMISSION_DELETE')")
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            System.out.println(id);
            permissionService.delete(id);
            return new Result(true, MessageConstant.DELETE_Permission_SUCCESS);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_Permission_FAIL);
        }
    }

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
//    @PreAuthorize("hasAnyAuthority('PERMISSION_QUERY')")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = permissionService.findPage(queryPageBean.getQueryString(), queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        return pageResult;
    }

    /**
     * 新增权限
     *
     * @param Permission
     * @return
     */
    @RequestMapping("/add")
//    @PreAuthorize("hasAnyAuthority('PERMISSION_ADD')")
    public Result add(@RequestBody Permission Permission) {
        try {
            permissionService.add(Permission);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_Permission_FAIL);
        }
        return new Result(true, MessageConstant.ADD_Permission_SUCCESS);
    }
}
