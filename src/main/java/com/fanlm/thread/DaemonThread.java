package com.fanlm.thread;

import java.util.HashMap;
import java.util.Map;
import java.util.Stack;

public class DaemonThread {

    static boolean running_flag = true;

    static class MyThread extends Thread {
        @Override
        public void run() {
            while (running_flag) {
                System.out.println(Thread.currentThread().getName() + " is running");
            }
        }
    }

    static class MyThread1 extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                System.out.println(Thread.currentThread().getName() + " is running");
            }
        }
    }

    public static void main(String[] args) {
        Thread t = new Thread(new MyThread(), "用户-中断");
        Thread t1 = new Thread(new MyThread1(), "用户1");

        Thread t2 = new Thread( () ->{
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            running_flag = false;
            for(;;){
                System.out.println("守护线程");
            }
        }, "守护线程");

        t2.setDaemon(true);

        t.start();
        t1.start();
        t2.start();

    }



}
