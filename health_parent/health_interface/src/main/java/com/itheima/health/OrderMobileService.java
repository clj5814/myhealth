package com.itheima.health;

import com.itheima.health.entity.Result;

import java.util.Map;

public interface OrderMobileService {


    Result order(Map map) throws Exception;

    Map findById(Integer id) throws Exception;
}
