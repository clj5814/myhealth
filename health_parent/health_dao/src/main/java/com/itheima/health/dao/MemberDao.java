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
    int findWeekNewMember(@Param("monday") String thisWeekMonday,@Param("sunday") String thisWeekSunday);
    @Select("SELECT count(*) from t_member WHERE regTime between #{first} and #{last}")
    int findthisMonthNewMember(@Param("first")String firstDay4ThisMonth, @Param("last")String lastDay4ThisMonth);
}
