package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.MemberService;
import com.itheima.health.common.constant.AgeConstant;
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

    @Override
    public Map<String, Object> getMemberReportByAge() {
        Map<String, Object> map =new HashMap<>();

        String name01="0-"+AgeConstant.USER_AGE_01;
        String name02=AgeConstant.USER_AGE_01+"-"+AgeConstant.USER_AGE_02;
        String name03=AgeConstant.USER_AGE_02+"-"+AgeConstant.USER_AGE_03;
        String name04=AgeConstant.USER_AGE_03+"以上";

        List<String> nameList=new ArrayList<>();
        nameList.add(name01);
        nameList.add(name02);
        nameList.add(name03);
        nameList.add(name04);

        List<Map> list=new ArrayList<>();
        String age01=2019-AgeConstant.USER_AGE_01+"-12-31";
        String age02=2019-AgeConstant.USER_AGE_01+"-01-01";
        String age03=2019-AgeConstant.USER_AGE_02+"-12-31";
        String age04=2019-AgeConstant.USER_AGE_02+"-01-01";
        String age05=2019-AgeConstant.USER_AGE_03+"-12-31";
        String age06=2019-AgeConstant.USER_AGE_03+"-01-01";
        int count01=memberDao.getMemberReportByAge01(age01);
        int count02=memberDao.getMemberReportByAge02(age02,age03);
        int count03=memberDao.getMemberReportByAge03(age04,age05);
        int count04=memberDao.getMemberReportByAge04(age06);
        Map map1=new HashMap();
        map1.put("name",name01);
        map1.put("value",count01);
        Map map2=new HashMap();
        map2.put("name",name02);
        map2.put("value",count02);
        Map map3=new HashMap();
        map3.put("name",name03);
        map3.put("value",count03);
        Map map4=new HashMap();
        map4.put("name",name04);
        map4.put("value",count04);
        list.add(map1);
        list.add(map2);
        list.add(map3);
        list.add(map4);

        map.put("memberAge",nameList);
        map.put("memberAgeCount",list);
        return map;
    }
}
