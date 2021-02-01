package com.fanlm.executor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.interrupted;

/**
 * @program: java8_test
 * @description: 线程池demo
 * @author: flm
 * @create: 2021-02-01 15:33
 **/
public class ExecutorTest {
    public static void main(String[] args) {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            executorService.execute(new MyRunnable());
        }
        executorService.shutdown();

        //daemon
        Thread thread = new Thread(new MyRunnable());
        thread.setDaemon(true);
        thread.start();

        //interrupt
        Thread threadInterrupt = new Thread(new MyRunnable1());
        threadInterrupt.start();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        threadInterrupt.interrupt();
        System.out.println("Main run");
    }
}

class MyRunnable implements Runnable{

    @Override
    public void run() {
        try {
            Thread.sleep(100);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(1);
    }
}

class MyRunnable1 implements Runnable{

    @Override
    public void run() {
        while (!interrupted()) {
            // ..
        System.out.println(1);
        }
//        for(;;)
    }
}