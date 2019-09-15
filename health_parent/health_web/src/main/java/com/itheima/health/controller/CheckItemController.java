package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.CheckItemService;
import com.itheima.health.common.constant.MessageConstant;
import com.itheima.health.entity.PageResult;
import com.itheima.health.entity.QueryPageBean;
import com.itheima.health.pojo.CheckItem;
import com.itheima.health.entity.Result;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/checkitem")
public class CheckItemController {
    @Reference
    private CheckItemService checkItemService;
    @RequestMapping("/findAll")
    @PreAuthorize("hasAnyAuthority('CHECKITEM_QUERY')")
    public Result findAll(){
        try {
            List<CheckItem> list=checkItemService.findAll();
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_CHECKITEM_FAIL);
        }
    }

    /**
     * 编辑检查项
     * @param checkItem
     * @return
     */
    @RequestMapping("/edit")
    @PreAuthorize("hasAnyAuthority('CHECKITEM_EDIT')")
    public Result edit(@RequestBody CheckItem checkItem){
        try {
            checkItemService.edit(checkItem);
            return new Result(true,MessageConstant.EDIT_CHECKITEM_SUCCESS,checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.EDIT_CHECKITEM_FAIL);
        }
    }

    /**
     * 查询检查项
     * @param id
     * @return
     */
    @RequestMapping("/fingById")
    @PreAuthorize("hasAnyAuthority('CHECKITEM_QUERY')")
    public Result findById(Integer id){
        try {
            CheckItem checkItem=checkItemService.findById(id);
            return new Result(true,MessageConstant.QUERY_CHECKITEM_SUCCESS,checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.QUERY_CHECKITEM_FAIL);
        }

    }

    /**
     * 删除检查项
     * @param id
     * @return
     */
    @PreAuthorize("hasAnyAuthority('CHECKITEM_DELETE')")
    @RequestMapping("/delete")
    public Result delete(Integer id){
        try {
            System.out.println(id);
            checkItemService.delete(id);
            return new Result(true,MessageConstant.DELETE_CHECKITEM_SUCCESS);
        }catch (RuntimeException e){
            return new Result(false,e.getMessage());
        }
        catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.DELETE_CHECKITEM_FAIL);
        }
    }

    /**
     * 分页查询
     * @param queryPageBean
     * @return
     */
    @RequestMapping("/findPage")
    @PreAuthorize("hasAnyAuthority('CHECKITEM_QUERY')")
    public PageResult findPage(@RequestBody QueryPageBean queryPageBean) {
        PageResult pageResult = checkItemService.findPage(queryPageBean.getQueryString(), queryPageBean.getCurrentPage(), queryPageBean.getPageSize());
        return pageResult;
    }

    /**
     * 新增检查检查项
     *
     * @param checkItem
     * @return
     */
    @RequestMapping("/add")
    @PreAuthorize("hasAnyAuthority('CHECKITEM_ADD')")
    public Result add(@RequestBody CheckItem checkItem) {
        try {
            checkItemService.add(checkItem);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.ADD_CHECKITEM_FAIL);
        }
        return new Result(true, MessageConstant.ADD_CHECKITEM_SUCCESS);
    }
}
