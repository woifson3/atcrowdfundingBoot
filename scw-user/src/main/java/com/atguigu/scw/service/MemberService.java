package com.atguigu.scw.service;

import com.atguigu.scw.Vo.MemberRequestVo;
import com.atguigu.scw.bean.TMember;

public interface MemberService {
    void saveMember(MemberRequestVo vo);

    TMember getMember(String loginacct, String userpwsd);
}
