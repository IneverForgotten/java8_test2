package com.fanlm.thread;

public class TestSynchronized {
    Integer a = 1;
    public static void main(String[] args) {
        TestSynchronized test = new TestSynchronized();
        test.test1();
        test.test2();
    }

    void test1(){
        synchronized(a){
            System.out.println(1);
        }
    }


    void test2(){
        synchronized(a){
            System.out.println(2);
        }
    }

}
