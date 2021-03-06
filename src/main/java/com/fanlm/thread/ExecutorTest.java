package com.fanlm.thread;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.Thread.interrupted;

/**
 * @program: java8_test
 * @description: 线程池demo
 * @author: flm
 * @create: 2021-02-01 15:33
 **/
public class ExecutorTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executorService = Executors.newCachedThreadPool();
        for (int i = 0; i < 5; i++) {
            executorService.execute(new MyRunnable211());
        }
        executorService.shutdown();
        Future<?> submit = executorService.submit(new MyRunnable211());
        Object o = submit.get();
        //daemon
        Thread thread = new Thread(new MyRunnable211());
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

class MyRunnable211 implements Runnable{

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