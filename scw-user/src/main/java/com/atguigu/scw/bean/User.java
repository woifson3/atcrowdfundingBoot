package com.atguigu.scw.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;
@AllArgsConstructor  //生成所有参数的构造器
@NoArgsConstructor //生成无参构造器
@ToString //生成toString方法
@Data  //生成getter/setter方法
@ApiModel(value="用户实体类")
public class User {
	@ApiModelProperty("用户序列号，主键")
	private Integer id;
	@ApiModelProperty("用户账号")
	private String username;
	@ApiModelProperty("用户密码")
	private String password;
	
	public void aaa() {
		//new Us
	}
}
