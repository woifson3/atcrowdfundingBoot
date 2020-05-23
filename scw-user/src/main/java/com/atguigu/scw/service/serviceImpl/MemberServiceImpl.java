package com.atguigu.scw.service.serviceImpl;

import com.atguigu.scw.Vo.MemberRequestVo;
import com.atguigu.scw.bean.TMember;
import com.atguigu.scw.bean.TMemberExample;
import com.atguigu.scw.common.utils.AppResponse;
import com.atguigu.scw.mapper.TMemberMapper;
import com.atguigu.scw.service.MemberService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import sun.swing.plaf.synth.DefaultSynthStyle;

import java.util.List;

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

    /**
     * 查询出用户的信息
     * @param loginacct
     * @param userpwsd
     * @return
     */
    @Override
    public TMember getMember(String loginacct, String userpwsd) {

        TMemberExample e =new TMemberExample();
        //根据登录名和密码作为条件去查询.但是bCryptPasswordEncoder每次加密后的字符串都不一样，所以要在下面判断密码（只能判断密码是否正确，不能判断密码字符串是否一样）
        e.createCriteria().andAccttypeEqualTo(loginacct);
        List<TMember> tMembers = memberMapper.selectByExample(e);
        //对拿到的对象进行判断
        if(CollectionUtils.isEmpty(tMembers)|| tMembers.size()>1){
            return null;
        }
        TMember member = tMembers.get(0);//拿取对象

        //对输入的密码判断是否真确：userpwsd和数据库中查询到的密码进行对比
        boolean b = bCryptPasswordEncoder.matches(userpwsd, member.getUserpswd());
        if(!b){
            return cc;
        }

        //擦除重要信息
        member.setUserpswd("[PROTECTED]");
        return member;
    }

    public void Test(){
        return;
    }
}
