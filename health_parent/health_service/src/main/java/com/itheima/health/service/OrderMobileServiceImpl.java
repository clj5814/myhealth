package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.OrderMobileService;
import com.itheima.health.common.constant.MessageConstant;
import com.itheima.health.common.utils.DateUtils;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.dao.OrderMobileDao;
import com.itheima.health.dao.OrderSettingDao;
import com.itheima.health.entity.Result;
import com.itheima.health.pojo.Member;
import com.itheima.health.pojo.Order;
import com.itheima.health.pojo.OrderSetting;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = OrderMobileService.class)
@Transactional
public class OrderMobileServiceImpl implements OrderMobileService {
    @Autowired
    private OrderMobileDao orderMobileDao;
    @Autowired
    private OrderSettingDao orderSettingDao;
    @Autowired
    private MemberDao memberDao;

    @Override
    public Result order(Map map) throws Exception {
        //判断当前日期是否可以预约
        String orderDate = (String) map.get("orderDate");
        Date date = DateUtils.parseString2Date(orderDate);
        OrderSetting orderSetting = orderSettingDao.findOrderSettingByOrderDate(date);
        if (orderSetting == null) {
            return new Result(false, MessageConstant.SELECTED_DATE_CANNOT_ORDER);
        }
        //判断当前日期预约是否已满
        int number = orderSetting.getNumber();
        int reservations = orderSetting.getReservations();
        if (number <= reservations) {
            return new Result(false, MessageConstant.ORDER_FULL);
        }
        //判断是否是会员
        String telephone = (String) map.get("telephone");
        Member member = memberDao.findMemberByTelephone(telephone);
        Integer setmealId =Integer.parseInt((String) map.get("setmealId")) ;
        if (member != null) {
            Integer memberId = member.getId();
            Order order = new Order(memberId, date, null, null, setmealId);
            List<Order> list = orderMobileDao.findOrder(order);
            if (list != null && list.size() > 0) {
                return new Result(false, MessageConstant.HAS_ORDERED);
            }
        }
        //更新预约数据
        orderSetting.setReservations(reservations + 1);
        orderSettingDao.editReservations(orderSetting);
        //保存会员信息
        if (member == null) {
            member = new Member();
            member.setName((String) map.get("name"));
            member.setSex((String) map.get("sex"));
            member.setPhoneNumber(telephone);
            member.setIdCard((String) map.get("idCard"));
            member.setRegTime(new Date());
            memberDao.add(member);
            System.out.println(member.getId());
        }
        //向预约表存数据
        Order order = new Order();
        order.setMemberId(member.getId());
        order.setOrderDate(date);
        order.setOrderType((String) map.get("orderType"));
        order.setOrderStatus(Order.ORDERSTATUS_NO);
        order.setSetmealId(setmealId);
        orderMobileDao.add(order);
        return new Result(true,MessageConstant.ORDER_SUCCESS,order);
    }

    @Override
    public Map findById(Integer id) throws Exception {
        Map map= orderMobileDao.findById(id);
        if (map != null) {
            Date orderDate = (Date) map.get("orderDate");
            map.put("orderDate",DateUtils.parseDate2String(orderDate));
        }
        return map;
    }
}
