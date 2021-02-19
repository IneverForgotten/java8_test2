package com.fanlm.thread;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: java8_test
 * @description: AQS test
 * @author: flm
 * @create: 2021-02-01 16:52
 **/
public class CountdownLatchExample
{
    public static void main(String[] args) throws InterruptedException {
        final int totalThread = 10;
        CountDownLatch countDownLatch = new CountDownLatch(totalThread);
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 15; i++) {
            executorService.execute(() -> {
                System.out.print("run..");
                countDownLatch.countDown();
                System.out.print("END..");
            });
        }
        countDownLatch.await();
        System.out.println("end");
        executorService.shutdown();
    }
}
