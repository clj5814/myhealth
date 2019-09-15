package com.itheima.health;

import com.itheima.health.pojo.OrderSetting;

import java.util.Date;
import java.util.List;
import java.util.Map;

public interface OrderSettingService {


    void add(List<OrderSetting> orderSettingList);

    List<Map<String, Object>> getOrderByMonth(String date);

    void editNumberByDate(OrderSetting orderSetting);
}
