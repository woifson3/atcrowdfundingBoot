package com.atguigu.scw.common.utils;

import com.atguigu.scw.common.Consts.AppConsts;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 响应返回类
 * @param <T>
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class AppResponse<T> {

    private int code;//10000:成功   10001:失败
    private String message;
    private T Data;

    public static<T> AppResponse<T> ok(String message ,T Data ){
        return  new AppResponse<T>(AppConsts.SUCCESS_CODE , message ,Data);
    }

    public static<T> AppResponse<T> fail( String message ,T Data){

        return new AppResponse<T>(AppConsts.ERROR_CODE ,  message ,Data );
    }
}
