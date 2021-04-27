package com.fanlm.test;

import lombok.SneakyThrows;

import java.util.concurrent.CountDownLatch;

public class NewClassTest {
    @SneakyThrows
    public static void main(String[] args) {
        double d = (double) 25/(double  ) 2;
        System.out.println(d);
        A a = new B();
        A a2 = new A();
        B b = new B();
        A a1 = (A)b;
        a.a();//B.a()
        a1.a();//B.a()
        System.out.println(( a1 instanceof B));//true
        System.out.println(( a1 instanceof A)); //true

        CountDownLatch count = new CountDownLatch(2);
        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                count.countDown();
                synchronized (a2){
                    System.out.println(1);
                    Thread.sleep(1000);
                    synchronized (b) {
                        System.out.println(2);
                    }
                }
            }
        });

        new Thread(new Runnable() {
            @SneakyThrows
            @Override
            public void run() {
                count.countDown();
                synchronized (b){
                    System.out.println(3);
                    Thread.sleep(1000);
                    synchronized (a1) {
                        System.out.println(4);
                    }
                }
            }
        });
        count.await();
        System.out.println(5);
    }
}

class A {
    public void a(){
        System.out.println("A.a()");
    }
}
class B extends A {
    public void a(){
        System.out.println("B.a()");
    }
}