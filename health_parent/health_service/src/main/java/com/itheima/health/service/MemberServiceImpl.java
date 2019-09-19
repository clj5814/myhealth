package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.MemberService;
import com.itheima.health.common.utils.MD5Utils;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service(interfaceClass = MemberService.class)
@Transactional
public class MemberServiceImpl implements MemberService {

    @Autowired
    private MemberDao memberDao;


    @Override
    public Member check(String telephone) {
        return memberDao.findMemberByTelephone(telephone);
    }

    @Override
    public void add(Member member) {
        //密码加盐
        if (member.getPassword() != null) {
            member.setPassword(MD5Utils.md5(member.getPassword()));
        }
        memberDao.add(member);
    }

    @Override
    public List<Integer> findMemberCountByMonth(List<String> list) {
        List<Integer> list1=new ArrayList<>();
        for (String month : list) {
            month=month+"-31";
            int count=memberDao.findMemberCountByMonth(month);
            list1.add(count);
        }
        return list1;
    }

    @Override
    public Map<String, Object> getMemberReportBySex() {
        int manCount=memberDao.getMemberReportByMan();
        int womanCount=memberDao.getMemberReportByWoman();
        List<Map<String,Object>> memberSexCount=new ArrayList<>();
        Map<String,Object> map1=new HashMap<>();
        map1.put("name","男性");
        map1.put("value",manCount);
        memberSexCount.add(map1);
        Map<String,Object> map2=new HashMap<>();
        map2.put("name","女性");
        map2.put("value",womanCount);
        memberSexCount.add(map2);
        List<String> memberSex=new ArrayList<>();
        memberSex.add("男性");
        memberSex.add("女性");
        Map<String, Object> map=new HashMap<>();
        map.put("memberSexCount",memberSexCount);
        map.put("memberSex",memberSex);
        return map;
    }
}
