package com.atguigu.scw;

import java.util.Date;
import java.util.concurrent.TimeUnit;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import com.google.gson.Gson;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ScwUserApplicationTests {
	@Autowired
	RedisTemplate<Object,Object> redisTemplate; //操作redis的模板类，一般用来操作对象
	@Autowired
	StringRedisTemplate stringTemplate; //操作redis的模板类，一般操作字符串
	Logger logger = LoggerFactory.getLogger(getClass());
	@Test
	public void contextLoads() {
//		Boolean flag = redisTemplate.hasKey("key1");
//		logger.info("redis中key1键是否存在：{}", flag);
//		redisTemplate.opsForValue().set("key1", "xxxxx", 100, TimeUnit.SECONDS);//存储键值对到redis中并设置过期时间
//		Long expire = redisTemplate.getExpire("key1", TimeUnit.SECONDS);
//		logger.info("reids中key1键的过期时间：{}",expire);
		//redisTemplate.delete("key1");
		
		stringTemplate.opsForValue().set("key2", "code:xxx", 100, TimeUnit.HOURS);
		Date date = new Date();
		Gson gson = new Gson();
		String json = gson.toJson(date);
		stringTemplate.opsForValue().set("dateStr", json);
		String dateStr = stringTemplate.opsForValue().get("dateStr");
		date = gson.fromJson(dateStr, Date.class);
		System.out.println(date);
		
	}
}
