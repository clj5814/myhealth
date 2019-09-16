package com.itheima.health.controller;

import com.itheima.health.common.constant.MessageConstant;
import com.itheima.health.common.constant.RedisMessageConstant;
import com.itheima.health.common.utils.SMSUtils;
import com.itheima.health.common.utils.ValidateCodeUtils;
import com.itheima.health.entity.Result;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import redis.clients.jedis.JedisPool;

@RestController
@RequestMapping("/validateCode")
public class ValidateCodeController {
    @Autowired
    private JedisPool jedisPool;

    @RequestMapping("/send4Order")
    public Result send4Order(String telephone){
        try {
            Integer code = ValidateCodeUtils.generateValidateCode(4);
//            SMSUtils.sendShortMessage(telephone,code.toString());
            System.out.println("发送的预约手机校验码为："+code);
            jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_ORDER,5*60,code.toString());
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }
    @RequestMapping("/send4Login")
    public Result send4Login(String telephone){
        try {
            Integer code = ValidateCodeUtils.generateValidateCode(4);
//            SMSUtils.sendShortMessage(telephone,code.toString());
            System.out.println("发送的登录手机校验码为："+code);
            jedisPool.getResource().setex(telephone+ RedisMessageConstant.SENDTYPE_LOGIN,5*60,code.toString());
            return new Result(true, MessageConstant.SEND_VALIDATECODE_SUCCESS);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.SEND_VALIDATECODE_FAIL);
        }
    }

}
