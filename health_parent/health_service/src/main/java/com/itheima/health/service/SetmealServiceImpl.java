package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.TypeReference;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.itheima.health.SetmealService;
import com.itheima.health.common.constant.RedisConstant;
import com.itheima.health.dao.SetmealDao;
import com.itheima.health.entity.PageResult;
import com.itheima.health.pojo.Setmeal;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import redis.clients.jedis.JedisPool;

import java.util.*;

@Service(interfaceClass = SetmealService.class)
@Transactional
public class SetmealServiceImpl implements SetmealService {
    @Autowired
    private SetmealDao setmealDao;
    @Autowired
    private JedisPool jedisPool;


    @Override
    public PageResult findPage(String queryString, Integer currentPage, Integer pageSize) {
        PageHelper.startPage(currentPage, pageSize);
        Page<Setmeal> page = setmealDao.findPage(queryString);
        return new PageResult(page.getTotal(), page.getResult());
    }

    @Override
    public void add(Setmeal setmeal, Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        setSetmealAndCheckGroup(setmeal.getId(), checkgroupIds);
        savePic2Redis(setmeal.getImg());
    }

    /**
     * 查找预约列表
     *
     * @return
     */
    @Override
    public List<Setmeal> findAll() {
        List<Setmeal> list = null;
        try {
            String value = jedisPool.getResource().get(RedisConstant.SETMEAL_RESOURCES);
            if (value == null || value == "") {
                list = setSetmealToRedis();
                //将list转化成String
                String value1 = JSON.toJSONString(list);
                jedisPool.getResource().set(RedisConstant.SETMEAL_RESOURCES,value1);
            } else {
                //将String转化成List<Object>
                list = JSONObject.parseArray(value, Setmeal.class);
            }
        } catch (Exception e) {
            e.printStackTrace();
            list = setSetmealToRedis();
        } finally {
            return list;
        }
    }

    /**
     * 从数据库查询数据，将数据保存到redis
     *
     * @return
     * @throws Exception
     */
    private List<Setmeal> setSetmealToRedis() {
        return setmealDao.findAll();
    }

    /**
     * 查找预约列表详情
     *
     * @param id
     * @return
     */
    @Override
    public Setmeal findById(Integer id) {
        Setmeal setmeal=null;
        try {
            //从redis中获取
            String value = jedisPool.getResource().get(id+RedisConstant.SETMEAL_DETAIL_RESOURCES);
            if (value == null || value == "") {
                setmeal = setSetmealDetail(id);
                //将对象转化成String
                String value1 = JSON.toJSONString(setmeal);
                //将数据保存到redis
                jedisPool.getResource().set(id+RedisConstant.SETMEAL_DETAIL_RESOURCES, value1);
            }
//            setmeal=  JSONObject.parseObject(value, Setmeal.class);
            //将String转化成对象
             setmeal=JSON.parseObject(value,new TypeReference<Setmeal>(){});
        } catch (Exception e) {
            e.printStackTrace();
            setmeal = setSetmealDetail(id);
        }finally {
            return setmeal;
        }
    }

    private Setmeal setSetmealDetail(Integer id) {
        return setmealDao.findById(id);
    }

    @Override
    public Map<String, Object> getSetmealReport() {
        List<Map<String, Object>> list = setmealDao.getSetmealReport();
        List<String> nameList = new ArrayList<>();
        for (Map<String, Object> map : list) {
            String name = (String) map.get("name");
            nameList.add(name);
        }
        Map<String, Object> map = new HashMap<>();
        map.put("setmealNames", nameList);
        map.put("setmealCount", list);
        return map;
    }

    /**
     * 保存图片链接到redis
     *
     * @param img
     */
    private void savePic2Redis(String img) {
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PEC_DB_RESOURCES, img);
    }

    private void setSetmealAndCheckGroup(Integer id, Integer[] checkgroupIds) {
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.setSetmealAndCheckGroup(id, checkgroupId);
            }
        }
    }
}
