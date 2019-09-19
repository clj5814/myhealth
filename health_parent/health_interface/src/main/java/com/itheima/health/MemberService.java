package com.itheima.health;

import com.itheima.health.pojo.Member;

import java.util.List;
import java.util.Map;

public interface MemberService {


    Member check(String telephone);

    void add(Member member);

    List<Integer> findMemberCountByMonth(List<String> list);

    Map<String, Object> getMemberReportBySex();
}
