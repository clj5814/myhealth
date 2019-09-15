package com.itheima.health.dao;

import com.itheima.health.pojo.Order;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

@Repository
public interface OrderMobileDao {
    List<Order> findOrder(Order order);

    void add(Order order);
    @Select("SELECT m.`name` member,s.`name` setmeal,o.orderDate orderDate,o.orderType orderType from t_order o,t_setmeal s,t_member m WHERE o.member_id=m.id and s.id=o.setmeal_id and o.id=#{id}")
    Map findById(Integer id);
    @Select("SELECT count(*) from t_order WHERE orderDate =#{today}")
    int findtodayOrderNumber(String today);

    @Select("SELECT count(*) from t_order WHERE orderDate between #{monday} and #{sunday}")
    int findWeekNewMember(@Param("monday") String thisWeekMonday,@Param("sunday")  String thisWeekSunday);

    @Select("SELECT count(*) from t_order WHERE orderDate between #{first} and #{last}")
    int findthisMonthNewMember(@Param("first") String firstDay4ThisMonth,@Param("last")  String lastDay4ThisMonth);

    @Select("SELECT count(*) from t_order WHERE orderDate =#{today} and orderStatus='已到诊'")
    int findtodayVisitsNumber(String today);

    @Select("SELECT count(*) from t_order WHERE orderDate between #{monday} and #{sunday} and orderStatus='已到诊'")
    int findWeekVisitsMember(@Param("monday")String thisWeekMonday,@Param("sunday") String thisWeekSunday);

    @Select("SELECT count(*) from t_order WHERE orderDate between #{first} and #{last} and orderStatus='已到诊'")
    int findthisMonthVisitsMember(@Param("first")String firstDay4ThisMonth, @Param("last")String lastDay4ThisMonth);
    @Select("SELECT s.`name` name,count(o.id) setmeal_count,count(o.id)/(SELECT count(id) from t_order) proportion from t_setmeal s,t_order o WHERE o.setmeal_id=s.id GROUP BY s.`name` ORDER BY count(o.id) desc LIMIT 0,4")
    List<Map> findhotSetmeal();
}
