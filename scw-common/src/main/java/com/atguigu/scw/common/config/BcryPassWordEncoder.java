package com.atguigu.scw.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BcryPassWordEncoder {

    @Bean
    public BcryPassWordEncoder getPassWord(){
        return new BcryPassWordEncoder();
    }
}
