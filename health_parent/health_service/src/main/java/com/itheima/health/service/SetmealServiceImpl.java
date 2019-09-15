package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
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
        PageHelper.startPage(currentPage,pageSize);
        Page<Setmeal> page =setmealDao.findPage(queryString);
        return new PageResult(page.getTotal(),page.getResult());
    }

    @Override
    public void add(Setmeal setmeal,Integer[] checkgroupIds) {
        setmealDao.add(setmeal);
        setSetmealAndCheckGroup(setmeal.getId(),checkgroupIds);
        savePic2Redis(setmeal.getImg());
    }

    @Override
    public List<Setmeal> findAll() {
        return setmealDao.findAll();
    }

    @Override
    public Setmeal findById(Integer id) {
        return setmealDao.findById(id);
    }

    @Override
    public Map<String, Object> getSetmealReport() {
        List<Map<String,Object>> list=setmealDao.getSetmealReport();
        List<String> nameList=new ArrayList<>();
        for (Map<String, Object> map : list) {
            String name = (String) map.get("name");
            nameList.add(name);
        }
        Map<String,Object> map = new HashMap<>();
        map.put("setmealNames",nameList);
        map.put("setmealCount",list);
        return map;
    }

    private void savePic2Redis(String img) {
        jedisPool.getResource().sadd(RedisConstant.SETMEAL_PEC_DB_RESOURCES,img);
    }

    private void setSetmealAndCheckGroup(Integer id, Integer[] checkgroupIds) {
        if (checkgroupIds != null && checkgroupIds.length > 0) {
            for (Integer checkgroupId : checkgroupIds) {
                setmealDao.setSetmealAndCheckGroup(id,checkgroupId);
            }
        }
    }
}
