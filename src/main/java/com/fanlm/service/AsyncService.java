package com.fanlm.service;

import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @program: java8_test
 * @description: async
 * @author: flm
 * @create: 2021-01-22 15:48
 **/
@Service
public class AsyncService {
    @Async
    public void testAsync(){
        System.out.println("testAsync start");
        try {
            Thread.sleep(10000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("testAsync end");
    }
}
