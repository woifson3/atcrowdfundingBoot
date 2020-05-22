package com.atguigu.scw.Vo;

import lombok.Data;
import org.springframework.stereotype.Component;

@Data
public class MemberRequestVo {
    private String loginacct;
    private Integer userpswd;
    private String email;
    private Integer usertype; //0-个人  1-企业
    private String code;
}
