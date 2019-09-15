package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.ReportService;
import com.itheima.health.common.utils.DateUtils;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.dao.OrderMobileDao;
import com.itheima.health.dao.ReportDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = ReportService.class)
@Transactional
public class ReportServiceImpl implements ReportService {
    @Autowired
    private ReportDao reportDao;
    @Autowired
    private MemberDao memberDao;
    @Autowired
    private OrderMobileDao orderMobileDao;


    @Override
    public Map<String, Object> getBusinessReportData() throws Exception {
        //日期：今日，这周，这月
        String today = DateUtils.parseDate2String(DateUtils.getToday());
        String thisWeekMonday = DateUtils.parseDate2String(DateUtils.getThisWeekMonday());
        String thisWeekSunday = DateUtils.parseDate2String(DateUtils.getSundayOfThisWeek());
        String firstDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getFirstDay4ThisMonth());
        String lastDay4ThisMonth = DateUtils.parseDate2String(DateUtils.getLastDay4ThisMonth());

        Map<String,Object> map =new HashMap<>();
        int todayNewMember=memberDao.findTodayMember(today);
        int totalMember=memberDao.findTOtalMember();
        int thisWeekNewMember=memberDao.findWeekNewMember(thisWeekMonday,thisWeekSunday);
        int thisMonthNewMember=memberDao.findthisMonthNewMember(firstDay4ThisMonth,lastDay4ThisMonth);
        int todayOrderNumber=orderMobileDao.findtodayOrderNumber(today);
        int thisWeekOrderNumber=orderMobileDao.findWeekNewMember(thisWeekMonday,thisWeekSunday);
        int thisMonthOrderNumber=orderMobileDao.findthisMonthNewMember(firstDay4ThisMonth,lastDay4ThisMonth);
        int todayVisitsNumber=orderMobileDao.findtodayVisitsNumber(today);
        int thisWeekVisitsNumber=orderMobileDao.findWeekVisitsMember(thisWeekMonday,thisWeekSunday);
        int thisMonthVisitsNumber=orderMobileDao.findthisMonthVisitsMember(firstDay4ThisMonth,lastDay4ThisMonth);
        List<Map> list=orderMobileDao.findhotSetmeal();

        map.put("reportDate",today);
        map.put("todayNewMember",todayNewMember);
        map.put("totalMember",totalMember);
        map.put("thisWeekNewMember",thisWeekNewMember);
        map.put("thisMonthNewMember",thisMonthNewMember);
        map.put("todayOrderNumber",todayOrderNumber);
        map.put("thisWeekOrderNumber",thisWeekOrderNumber);
        map.put("thisMonthOrderNumber",thisMonthOrderNumber);
        map.put("todayVisitsNumber",todayVisitsNumber);
        map.put("thisWeekVisitsNumber",thisWeekVisitsNumber);
        map.put("thisMonthVisitsNumber",thisMonthVisitsNumber);
        map.put("hotSetmeal",list);
        return map;
    }
}
