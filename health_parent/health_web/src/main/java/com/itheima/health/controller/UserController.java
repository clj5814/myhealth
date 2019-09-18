package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.UserService;
import com.itheima.health.common.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/user")
public class UserController {
    @Reference
    private UserService userService;

    @RequestMapping("/getUsername")
    public Result getUsername() {
        try {
            User user = (User) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
            return new Result(true, MessageConstant.GET_USERNAME_SUCCESS, user.getUsername());
        } catch (Exception e) {
            return new Result(false, MessageConstant.GET_USERNAME_FAIL);
        }
    }

    /**
     * 编辑用户
     *
     * @param user
     * @param roleIds
     * @return
     */
    @RequestMapping("/edit")
//    @PreAuthorize("hasAnyAuthority('User_EDIT')")
    public Result edit(@RequestBody User user, Integer[] roleIds) {
        try {
            userService.edit(user, roleIds);
            return new Result(true, MessageConstant.EDIT_USER_SUCCESS, user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.EDIT_USER_FAIL);
        }
    }

    /**
     * 查询用户，回显编辑数据
     *
     * @param id
     * @return
     */
    @RequestMapping("/findById")
//    @PreAuthorize("hasAnyAuthority('User_QUERY')")
    public Result findById(Integer id) {
        try {
            User user = userService.findById(id);
            return new Result(true, MessageConstant.QUERY_USER_SUCCESS, user);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true, MessageConstant.QUERY_USER_FAIL);
        }

    }

    /**
     * 编辑回显用户id
     *
     * @param id
     * @return
     */
    @RequestMapping("/findRoleIdsById")
    public List<Integer> findRoleIdsById(Integer id) {
        return userService.findRoleIdsById(id);
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
//    @PreAuthorize("hasAnyAuthority('User_DELETE')")
    @RequestMapping("/delete")
    public Result delete(Integer id) {
        try {
            userService.delete(id);
            return new Result(true, MessageConstant.DELETE_USER_SUCCESS);
        } catch (RuntimeException e) {
            return new Result(false, e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.DELETE_USER_FAIL);
        }
    }

    /**
     * 分页查询
     *
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
//    @PreAuthorize("hasAnyAuthority('User_QUERY')")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = userService.findPage(queryPageBean.getQueryString(), queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        return pageResult;
    }

    /**
     * 新增用户
     *
     * @param user
     * @return
     */
    @RequestMapping("/add")
//    @PreAuthorize("hasAnyAuthority('User_ADD')")
    public Result add(@RequestBody User user, @RequestParam("roleIds") Integer[] roleIds) {
        try {
            userService.add(user, roleIds);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_USER_FAIL);
        }
        return new Result(true, MessageConstant.ADD_USER_SUCCESS);
    }
}
