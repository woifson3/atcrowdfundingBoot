package com.atguigu.scw.controller;


import com.atguigu.scw.Vo.MemberRequestVo;
import com.atguigu.scw.common.Consts.AppConsts;
import com.atguigu.scw.common.Template.SmsTemplate;
import com.atguigu.scw.common.utils.AppResponse;
import com.atguigu.scw.common.utils.ScwUtils;
import com.atguigu.scw.service.MemberService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

import java.sql.Time;
import java.util.HashMap;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Api("用户注册验证码登录请求的controller")
@RestController
public class userController {

    @Autowired
    StringRedisTemplate redisTemplate;
    @Autowired
    ScwUtils scwUtils;
    @Autowired
    SmsTemplate smsTemplate;
    @Autowired
    MemberService memberService;

    /**
     * 要进行以下验证
     * 1.验证手机号格式是否正确
     * 2.验证redis中验证码次数是否超标（设置3次）
     * 3.验证日redis中是否有（未过期）验证码
     * 4.把验证码在redis中存5分钟
     * 5.更新叠加验证码使用次数（不可以超过三次，没用一次就叠加）
     * @param phoneNum
     * @return
     */
    @ApiOperation("给手机发送短信验证码的方法")
    @ApiImplicitParams(@ApiImplicitParam(name="phoneNum" ,required = false, value = "手机号码"))
    public AppResponse<Object> sendSms(String phoneNum){
        //1.判断手机格式是否正确
        boolean b = scwUtils.isMobilePhone(phoneNum);
        if(!b ){
            return AppResponse.fail("手机号码格式不正确" ,null);
        }

        //2.从redis中获取该手机号在当天获取的验证码的次数（没有超过3次就可以获取）
        String countStr = redisTemplate.opsForValue().get(AppConsts.CODE_PREFIX + phoneNum + AppConsts.CODE_COUNT_SUFFIX);//这是自己组装的一个格式，存放到redis中
        int count =0;
        //要是获取到的值不为空，就转成数字.赋值到count上
        if(!StringUtils.isEmpty(countStr)){
             count = Integer.parseInt(countStr);
        }
        if(count>3){
            return AppResponse.fail("超过当日获取验证码的次数上限" , null);
        }

        //3.验证redis中的验证码是否过期（就是使用当前手机号的验证码去redis中查看是否存在）
        Boolean key = redisTemplate.hasKey(AppConsts.CODE_PREFIX + phoneNum + AppConsts.CODE_COUNT_SUFFIX);
        if( key){
            return AppResponse.fail("请勿频繁获取验证码" ,null);
        }

        //要是没有了雁阵吗，那就发送验证码(手动自己造，存到redis中)
        String code = UUID.randomUUID().toString().replace("-", "").substring(0, 6);//随机生成验证码
        HashMap<String, String> querys = new HashMap<>();
        querys.put("mobile" , phoneNum);
        querys.put("params" , AppConsts.CODE_PREFIX + code);
        querys.put("tpl_id", "TP1711063");
        smsTemplate.sendSms(querys);
        //4.将验证码存到redis中5分钟
        redisTemplate.opsForValue().set(AppConsts.CODE_PREFIX + phoneNum + AppConsts.CODE_COUNT_SUFFIX ,code,5 , TimeUnit.MINUTES);

        //5.更新验证码使用的次数
        Long expire = redisTemplate.getExpire(AppConsts.CODE_PREFIX + phoneNum + AppConsts.CODE_COUNT_SUFFIX, TimeUnit.MINUTES);//获取出该手机使用了验证码的次数
        if (expire ==null || expire>=3){
            expire = (long) (24*60); //要是超过了一天使用的上线，重新设置时间
        }
        count++; //要是没有超过3次就叠加
        //
        redisTemplate.opsForValue().set(AppConsts.CODE_PREFIX + phoneNum + AppConsts.CODE_COUNT_SUFFIX,count+"" ,expire,TimeUnit.MINUTES);
        return AppResponse.ok("发送短信成功",null);
    }

    /**
     * 注册用户
     * 1.检查验证码是否真确
     * 2.检查手机号对应的验证码是否正确
     * 3.完成注册
     * @param vo
     * @return
     */
    @ApiOperation("用户注册的方法")
    @PostMapping("/user/doRegister")
    public Object doRegist(MemberRequestVo vo){
        //1.检查验证码是否正确
        String loginacct = vo.getLoginacct(); //loginacct就是登陆名，就是用手机号登陆的，所以可以
        String code = redisTemplate.opsForValue().get(AppConsts.CODE_PREFIX + loginacct + AppConsts.CODE_COUNT_SUFFIX);
        if(StringUtils.isEmpty(code)){
           return AppResponse.fail("验证码失效",null);
        }
        if(!loginacct.equals(code)){
            return AppResponse.fail("验证码错误",null);
        }
        //储存用户信息
        memberService.saveMember(vo);
        //删除redis中该存储了信息的手机验证码
        redisTemplate.delete(AppConsts.CODE_PREFIX + loginacct + AppConsts.CODE_COUNT_SUFFIX);
        return AppResponse.ok("注册成功",null);
    }

}
