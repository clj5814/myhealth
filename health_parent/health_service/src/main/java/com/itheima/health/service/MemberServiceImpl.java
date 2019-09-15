package com.itheima.health.service;

import com.alibaba.dubbo.config.annotation.Service;
import com.itheima.health.MemberService;
import com.itheima.health.common.utils.MD5Utils;
import com.itheima.health.dao.MemberDao;
import com.itheima.health.pojo.Member;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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
}
