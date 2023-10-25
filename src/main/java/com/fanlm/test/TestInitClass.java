package com.fanlm.test;

import org.springframework.stereotype.Component;

/**
 * @ClassName: TestInitClass
 * @Description:
 * @Author: fanLeiMin
 * @Date: 2022/11/14 9:36
 */
@Component
public class TestInitClass {
    private String test1;
    {
        System.out.println("构造块初始化");
    }
}
