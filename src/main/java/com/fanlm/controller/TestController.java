package com.fanlm.controller;

import com.fanlm.service.TestAsyncService;
import com.fanlm.service.TestService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @program: java8_test
 * @description:
 * @author: flm
 * @create: 2021-01-22 15:18
 **/
@RestController
public class TestController {

    @Resource
    private TestService testService;

    @GetMapping("test")
    public void test() throws InterruptedException {
        Thread.sleep(11000);
        System.out.println("程序执行-----------------");
        testService.test();
    }
}
