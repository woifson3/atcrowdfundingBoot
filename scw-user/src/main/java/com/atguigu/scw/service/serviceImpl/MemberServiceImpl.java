package com.atguigu.scw.service.serviceImpl;

import com.atguigu.scw.Vo.MemberRequestVo;
import com.atguigu.scw.bean.TMember;
import com.atguigu.scw.mapper.TMemberMapper;
import com.atguigu.scw.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class MemberServiceImpl implements MemberService {

    @Autowired
    TMemberMapper memberMapper;
    @Autowired
    BCryptPasswordEncoder bCryptPasswordEncoder;
    /**
     * 储存用户信息的方法
     * @param vo
     */
    @Override
    public void saveMember(MemberRequestVo vo) {
        //需要将vo里面数据放到TMember dao里面去
        TMember member = new TMember();
        BeanUtils.copyProperties(vo,member);
        member.setUsername(member.getLoginacct());//就是用手机号当做登陆名称的
        member.setAccttype("0");
        //保存的时候设置密码加密
        bCryptPasswordEncoder.encode(member.getUserpswd());
        //将装有数据的member保存到数据库中
        memberMapper.insertSelective(member);
    }
}
