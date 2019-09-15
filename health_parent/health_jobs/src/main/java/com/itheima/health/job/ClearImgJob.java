package com.itheima.health.job;

import com.itheima.health.common.utils.QiniuUtils;
import com.itheima.health.common.constant.RedisConstant;
import org.springframework.beans.factory.annotation.Autowired;
import redis.clients.jedis.JedisPool;

import java.util.Set;

public class ClearImgJob {
    @Autowired
    private JedisPool jedisPool;

    public void clearImg(){
        //获得redis中差值的名称
        Set<String> set = jedisPool.getResource().sdiff(RedisConstant.SETMEAL_PIC_RESOURCES, RedisConstant.SETMEAL_PEC_DB_RESOURCES);
        for (String s : set) {
            System.out.println("删除的图片是："+s);
            //删除七牛云中的数据
            QiniuUtils.deleteFileFromQiniu(s);
            //删除redis中的数据
            jedisPool.getResource().srem(RedisConstant.SETMEAL_PIC_RESOURCES,s);
        }
    }
}
