package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.MenuService;
import com.itheima.health.common.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Menu;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/menu")
public class MenuController {
    @Reference
    private MenuService menuService;

    /**
     * 查找所有
     * @return
     *//*
    @RequestMapping("/findAll")
//    @PreAuthorize("hasAnyAuthority('Menu_QUERY')")
    public Result findAll(){
        try {
            List<Menu> list=menuService.findAll();
            return new Result(true,MessageConstant.QUERY_Menu_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_Menu_FAIL);
        }
    }*/

    /* */

    /**
     * 编辑权限
     *
     * @param Menu
     * @return
     */
    @RequestMapping("/edit")
//    @PreAuthorize("hasAnyAuthority('Menu_EDIT')")
    public Result edit(@RequestBody Menu Menu) {
        try {
            menuService.edit(Menu);
            return new Result(true, MessageConstant.EDIT_Menu_SUCCESS, Menu);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.EDIT_Menu_FAIL);
        }
    }

    /**
     * 查询权限
     *
     * @param id
     * @return
     */
    @RequestMapping("/fingById")
//    @PreAuthorize("hasAnyAuthority('Menu_QUERY')")
    public Result findById(Integer id) {
        try {
            Menu menu = menuService.findById(id);
            return new Result(true, MessageConstant.QUERY_Menu_SUCCESS, menu);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_Menu_FAIL);
        }

    }

    /**
     * 删除权限
     *
     * @param id
     * @return
     */
//    @PreAuthorize("hasAnyAuthority('Menu_DELETE')")
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            System.out.println(id);
            menuService.delete(id);
            return new Result(true, MessageConstant.DELETE_Menu_SUCCESS);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_Menu_FAIL);
        }
    }

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
//    @PreAuthorize("hasAnyAuthority('Menu_QUERY')")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = menuService.findPage(queryPageBean.getQueryString(), queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        return pageResult;
    }

    /**
     * 新增权限
     *
     * @param menu
     * @return
     */
    @RequestMapping("/add")
//    @PreAuthorize("hasAnyAuthority('Menu_ADD')")
    public Result add(@RequestBody Menu menu) {
        try {
            menuService.add(menu);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_Menu_FAIL);
        }
        return new Result(true, MessageConstant.ADD_Menu_SUCCESS);
    }
}
