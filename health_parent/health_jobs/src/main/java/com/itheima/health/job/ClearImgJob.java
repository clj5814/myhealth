package com.itheima.health.job;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.OrderSettingService;
import com.itheima.health.common.constant.RedisConstant;
import com.itheima.health.common.utils.DateUtils;
import com.itheima.health.common.utils.QiniuUtils;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Date;
import java.util.Set;

public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;
    @Reference
    private OrderSettingService orderSettingService;

    public void clearImg() {
        //获得redis中差值的名称
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PEC_DB_RESOURCES);
        for (String s : set) {
            System.out.println("删除的图片是：" + s);
            //删除七牛云中的数据
            QiniuUtils.deleteFileFromQiniu(s);
            //删除redis中的数据
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES, s);
        }
    }


    public void clearOrderSetting() throws Exception {
        String data = DateUtils.parseDate2String(new Date());
        orderSettingService.deleteOrderSettingByData(data);
    }
}
