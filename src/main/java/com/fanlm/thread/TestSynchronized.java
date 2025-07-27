package com.fanlm.thread;

import io.swagger.models.auth.In;

import java.util.concurrent.CompletableFuture;

public class TestSynchronized {
    Integer a = 1;
    public static void main(String[] args) throws InterruptedException {
        TestSynchronized test = new TestSynchronized();

//        CompletableFuture.runAsync(() -> {
//            try {
//                test.test1();
//            } catch (InterruptedException e) {
//                e.printStackTrace();
//            }
//        });
//        CompletableFuture.runAsync(() -> {
//            try {
//                test.test2();
//            } catch (Exception e) {
//                e.printStackTrace();
//            }
//        });
//        test.test1();
//        test.test2();
        TestSynchronized.test3();
//        TestSynchronized.test4();
    }

    void test1() throws InterruptedException {
        synchronized(this){
            System.out.println(1);
            test2();
            Thread.sleep(2000);
        }
    }


    void test2(){
        synchronized(this){
            System.out.println(2);
        }
    }

    static synchronized void test3() throws InterruptedException {
        System.out.println(3);
        test4();
        Thread.sleep(2000);
    }

    static synchronized void test4(){
        System.out.println(4);
    }

}
