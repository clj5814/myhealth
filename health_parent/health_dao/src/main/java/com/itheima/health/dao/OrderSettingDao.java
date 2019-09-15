package com.itheima.health.dao;

import com.itheima.health.pojo.OrderSetting;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderSettingDao {
    void add(OrderSetting orderSetting);

    long findCountByOrderDate(Date orderDate);

    void editNumberByOrderDate(OrderSetting orderSetting);

    List<OrderSetting> getOrderByMonth(@Param("start") String start, @Param("end")String end);

    void editNumberByDate(@Param("number") int number,@Param("number") Date orderDate);
    @Select("select * from t_ordersetting where orderDate=#{orderDate}")
    OrderSetting findOrderSettingByOrderDate(Date date);
    @Update("UPDATE t_ordersetting set reservations=#{reservations} WHERE orderDate =#{orderDate}")
    void editReservations(OrderSetting orderSetting);
}
