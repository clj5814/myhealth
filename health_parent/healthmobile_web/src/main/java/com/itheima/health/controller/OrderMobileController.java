package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.aliyuncs.exceptions.ClientException;
import com.itheima.health.OrderMobileService;
import com.itheima.health.common.constant.MessageConstant;
import com.itheima.health.common.constant.RedisMessageConstant;
import com.itheima.health.common.utils.SMSUtils;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Order;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

import java.util.Map;

@RestController
@RequestMapping("/order")
public class OrderMobileController {
    @Reference
    private OrderMobileService orderMobileService;
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/findById")
    public Result findById(Integer id){
        try {
            Map map=orderMobileService.findById(id);
            return new Result(true,MessageConstant.QUERY_ORDER_SUCCESS,map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false,MessageConstant.QUERY_ORDER_FAIL);
        }
    }

    /**
     * 预约提交
     * @param map
     * @return
     */
    @RequestMapping("/submit")
    public Result submit(@RequestBody Map map) {
        String telephone = (String) map.get("telephone");
        String codeRedis = jedisPool.getResource().get(telephone + RedisMessageConstant.SENDTYPE_ORDER);
        String validateCode = (String) map.get("validateCode");
        if (codeRedis==null || !codeRedis.equals(validateCode)){
            return new Result(false,MessageConstant.VALIDATECODE_ERROR);
        }
        Result result=null;
        try {
            map.put("orderType", Order.ORDERTYPE_WEIXIN);
            result=orderMobileService.order(map);
        } catch (Exception e) {
            e.printStackTrace();
            return result;
        }
        if(result.isFlag()) {
            String orderDate = (String) map.get("orderDate");
            try {
                System.out.println(telephone+"预约时间:"+orderDate);
//                SMSUtils.sendShortMessage(telephone,orderDate);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return result;
    }
}
