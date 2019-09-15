package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.OrderSettingService;
import com.itheima.health.common.constant.MessageConstant;
import com.itheima.health.common.utils.POIUtils;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.OrderSetting;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/ordersetting")
public class OrderSettingController {
    @Reference
    private OrderSettingService orderSettingService;

    /**
     * 编辑预约数
     * @param orderSetting
     * @return
     */
    @RequestMapping("/editNumberByDate")
    public Result editNumberByDate(@RequestBody OrderSetting orderSetting){
        System.out.println(orderSetting);
        try {
            orderSettingService.editNumberByDate(orderSetting);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }


    /**
     * 获得预约数据
     * @param date
     * @return
     */
    @RequestMapping("/getOrderByMonth")
    public Result getOrderByMonth(String date){
        try {
            List<Map<String,Object>> list= orderSettingService.getOrderByMonth(date);
            return new Result(true,MessageConstant.GET_ORDERSETTING_SUCCESS,list);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(true,MessageConstant.GET_ORDERSETTING_FAIL);
        }
    }

    /**
     * 上传文件
     * @param excelFile
     * @return
     */
    @RequestMapping("/upload")
    public Result upload(@RequestParam("excelFile") MultipartFile excelFile) {
        try {
            List<String[]> list = POIUtils.readExcel(excelFile);
            List<OrderSetting> orderSettingList = new ArrayList<>();
            if (list != null && list.size() > 0) {
                for (String[] strings : list) {
                    OrderSetting orderSetting = new OrderSetting(new Date(strings[0]), Integer.parseInt(strings[1]));
                    orderSettingList.add(orderSetting);
                }
                orderSettingService.add(orderSettingList);
            }
            return new Result(true, MessageConstant.IMPORT_ORDERSETTING_SUCCESS);
        } catch (IOException e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.IMPORT_ORDERSETTING_FAIL);
        }
    }

}
