package com.atguigu.scw;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.cloud.openfeign.EnableFeignClients;
@EnableFeignClients
@EnableDiscoveryClient
@EnableHystrix
@SpringBootApplication
@MapperScan(basePackages="com.atguigu.scw.project.mapper")
public class ScwProjectApplication {

	public static void main(String[] args) {
		SpringApplication.run(ScwProjectApplication.class, args);
	}

}
