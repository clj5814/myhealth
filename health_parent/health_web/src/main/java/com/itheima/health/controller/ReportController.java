package com.itheima.health.controller;

import com.alibaba.dubbo.config.annotation.Reference;
import com.itheima.health.MemberService;
import com.itheima.health.ReportService;
import com.itheima.health.SetmealService;
import com.itheima.health.common.constant.MessageConstant;
import com.itheima.health.entity.Result;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileInputStream;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.*;

@RestController
@RequestMapping("/report")
public class ReportController {
    @Reference
    private MemberService memberService;
    @Reference
    private SetmealService setmealService;
    @Reference
    private ReportService reportService;

    @RequestMapping("/exportBusinessReport")
    public void exportBusinessReport(HttpServletRequest request, HttpServletResponse response) throws Exception {
        Map<String, Object> map = reportService.getBusinessReportData();
        //获取数据
        String reportDate = (String) map.get("reportDate");
        Integer todayNewMember = (Integer) map.get("todayNewMember");
        Integer totalMember = (Integer) map.get("totalMember");
        Integer thisWeekNewMember = (Integer) map.get("thisWeekNewMember");
        Integer thisMonthNewMember = (Integer) map.get("thisMonthNewMember");
        Integer todayOrderNumber = (Integer) map.get("todayOrderNumber");
        Integer todayVisitsNumber = (Integer) map.get("todayVisitsNumber");
        Integer thisWeekOrderNumber = (Integer) map.get("thisWeekOrderNumber");
        Integer thisWeekVisitsNumber = (Integer) map.get("thisWeekVisitsNumber");
        Integer thisMonthOrderNumber = (Integer) map.get("thisMonthOrderNumber");
        Integer thisMonthVisitsNumber = (Integer) map.get("thisMonthVisitsNumber");
        List<Map> hotSetmeal = (List) map.get("hotSetmeal");

        //String template = request.getSession().getServletContext().getRealPath("template/report_template.xlsx")
        String template = request.getSession().getServletContext().getRealPath("template") + File.separator + "report_template.xlsx";
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(new File(template)));
        XSSFSheet sheet = workbook.getSheetAt(0);


        XSSFRow row = sheet.getRow(2);
        row.getCell(5).setCellValue(reportDate);//日期

        row = sheet.getRow(4);
        row.getCell(5).setCellValue(todayNewMember);//新增会员数（本日）
        row.getCell(7).setCellValue(totalMember);//总会员数

        row = sheet.getRow(5);
        row.getCell(5).setCellValue(thisWeekNewMember);//本周新增会员数
        row.getCell(7).setCellValue(thisMonthNewMember);//本月新增会员数

        row = sheet.getRow(7);
        row.getCell(5).setCellValue(todayOrderNumber);//今日预约数
        row.getCell(7).setCellValue(todayVisitsNumber);//今日到诊数

        row = sheet.getRow(8);
        row.getCell(5).setCellValue(thisWeekOrderNumber);//本周预约数
        row.getCell(7).setCellValue(thisWeekVisitsNumber);//本周到诊数

        row = sheet.getRow(9);
        row.getCell(5).setCellValue(thisMonthOrderNumber);//本月预约数
        row.getCell(7).setCellValue(thisMonthVisitsNumber);//本月到诊数

        int num=12;
        for (Map map1 : hotSetmeal) {
            String name = (String) map1.get("name");
            long setmeal_count = (long) map1.get("setmeal_count");
            BigDecimal proportion = (BigDecimal) map1.get("proportion");
            row=sheet.getRow(num++);
            row.getCell(4).setCellValue(name);
            row.getCell(5).setCellValue(setmeal_count);
            row.getCell(6).setCellValue(proportion.doubleValue());
        }

        ServletOutputStream os = response.getOutputStream();
        //以什么样的文件形式输出(导出，下载）必须要指定
        //(1)下载的数据类型（excel）
        response.setContentType("application/vnd.ms-excel");
        //下载的形式（附件;内连inline）
        response.setHeader("content-Disposition","attachment;filename=report.xlsx");
        workbook.write(os);
        os.flush();
        os.close();
        workbook.close();
    }

    /**
     * 查询运营统计数据
     *
     * @return
     */
    @RequestMapping("/getBusinessReportData")
    public Result getBusinessReportData() {
        try {
            Map<String, Object> map = reportService.getBusinessReportData();
            return new Result(true, MessageConstant.GET_BUSINESS_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_BUSINESS_REPORT_FAIL);
        }
    }

    /**
     * 查询预约统计数据
     *
     * @return
     */
    @RequestMapping("/getSetmealReport")
    public Result getSetmealReport() {
        try {
            Map<String, Object> map = setmealService.getSetmealReport();
            return new Result(true, MessageConstant.GET_SETMEAL_COUNT_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_SETMEAL_COUNT_REPORT_FAIL);
        }
    }

    /**
     * 查询会员统计数据
     *
     * @return
     */
    @RequestMapping("/getMemberReport")
    public Result getMemberReport() {
        try {
            Calendar calendar = Calendar.getInstance();
            //获得当前日期之前的12个月的日期
            calendar.add(Calendar.MONTH, -12);
            List<String> list = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                calendar.add(Calendar.MONTH, 1);
                list.add(new SimpleDateFormat("yyyy-MM").format(calendar.getTime()));
            }
            Map<String, Object> map = new HashMap<>();
            map.put("months", list);
            List<Integer> memberCountList = memberService.findMemberCountByMonth(list);
            map.put("memberCount", memberCountList);
            return new Result(true, MessageConstant.GET_MEMBER_NUMBER_REPORT_SUCCESS, map);
        } catch (Exception e) {
            e.printStackTrace();
            return new Result(false, MessageConstant.GET_MEMBER_NUMBER_REPORT_FAIL);
        }
    }


}
