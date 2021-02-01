package com.fanlm.thread;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @program: java8_test
 * @description: synchronized test
 * @author: flm
 * @create: 2021-02-01 16:17
 **/
public class SynchronizedTest {
    public void func() {
        synchronized (this) {
            // ...
        }
    }
    public static void main(String[] args) {
        A a = new A();
        B b = new B(a);
        b.start();
        a.start();


        //等待
        ExecutorService executorService = Executors.newCachedThreadPool();
        WaitNotifyExample example = new WaitNotifyExample();
        executorService.execute(() -> example.after());
        executorService.execute(() -> example.before());
//        executorService.shutdownNow();
//        System.out.println(runnables.t    oString());
    }

}
class WaitNotifyExample {

    public synchronized void before() {
        System.out.println("before");
        notifyAll();
    }

    public synchronized void after() {
        try {
            wait();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("after");
    }
}
class A extends Thread {
    @Override
    public void run() {
        System.out.println("A");
    }
}

class B extends Thread {

    private A a;

    B(A a) {
        this.a = a;
    }

    @Override
    public void run() {
        System.out.println("B");
        try {
            a.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(1);
    }
}
