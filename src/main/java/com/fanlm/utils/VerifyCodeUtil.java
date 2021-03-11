package com.fanlm.utils;

/**
 * @program: java8_test
 * @description: 验证码
 * @author: flm
 * @create: 2021-03-01 15:38
 **/
public class VerifyCodeUtil {

    public String getCode(){
        return String.valueOf((int)((Math.random()*9+1)*Math.pow(10,5)));
    }
}
