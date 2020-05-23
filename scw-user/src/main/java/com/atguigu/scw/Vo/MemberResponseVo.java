package com.atguigu.scw.Vo;

import lombok.Data;

@Data
public class MemberResponseVo {
    private String loginacct;
    private Integer userpswd;
    private String email;
    private String MemberToken;
}
