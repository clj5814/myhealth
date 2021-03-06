package com.itheima.health.dao;

import com.itheima.health.pojo.Member;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Repository;

@Repository
public interface MemberDao {
    @Select("select * from t_member where phoneNumber = #{telephone}")
    Member findMemberByTelephone(String telephone);

    void add(Member member);

    @Select("SELECT count(*) FROM t_member WHERE regTime <= #{month}")
    int findMemberCountByMonth(String month);

    @Select("SELECT count(*) from t_member WHERE regTime=#{today}")
    int findTodayMember(String today);

    @Select("SELECT count(*) from t_member")
    int findTOtalMember();

    @Select("SELECT count(*) from t_member WHERE regTime between #{monday} and #{sunday}")
    int findWeekNewMember(@Param("monday") String thisWeekMonday, @Param("sunday") String thisWeekSunday);

    @Select("SELECT count(*) from t_member WHERE regTime between #{first} and #{last}")
    int findthisMonthNewMember(@Param("first") String firstDay4ThisMonth, @Param("last") String lastDay4ThisMonth);

    @Select("SELECT count(*) from t_member where sex=1")
    int getMemberReportByMan();

    @Select("SELECT count(*) from t_member where sex=2")
    int getMemberReportByWoman();

    @Select("SELECT count(*) from t_member where birthday < #{userAge01}")
    int getMemberReportByAge01(String userAge01);

    @Select("SELECT count(*) from t_member where birthday BETWEEN #{userAge02} and #{userAge01}")
    int getMemberReportByAge02(@Param("userAge01") String userAge01, @Param("userAge02") String userAge02);

    @Select("SELECT count(*) from t_member where birthday BETWEEN #{userAge03} and #{userAge02}")
    int getMemberReportByAge03(@Param("userAge02") String userAge02, @Param("userAge03") String userAge03);

    @Select("SELECT count(*) from t_member where birthday > #{userAge03}")
    int getMemberReportByAge04(String userAge03);
}
