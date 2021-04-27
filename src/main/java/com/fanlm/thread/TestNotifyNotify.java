package com.fanlm.thread;

public class TestNotifyNotify {

    private static Object obj = new Object();

    public static void main(String[] args) {

        //测试 RunnableImplA wait()
        Thread t1 = new Thread(new RunnableImplA(obj,1));
        Thread t2 = new Thread(new RunnableImplA(obj,2));
        t1.start();
        t2.start();
    }

}


class RunnableImplA implements Runnable {

    private Object obj;
    private int i;

    public RunnableImplA(Object obj,int i) {
        this.obj = obj;
        this.i = i;
    }

    @Override
    public void run() {
        synchronized (obj) {
            System.out.println("i = " + i);
            try {
                i+=2;
                obj.notifyAll();
                obj.wait();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
class OddEvenPrinter {
    private final Object monitor = new Object();
    private final int limit;
    private volatile int count;

    public OddEvenPrinter(int limit, int initCount) {
        this.limit = limit;
        this.count = initCount;
    }
    public void print() {
        synchronized (monitor) {
            while (count < limit) {
                try {
                    System.out.println(String.format("线程[%s]打印数字:%d", Thread.currentThread().getName(), ++count));
                    monitor.notifyAll();
                    monitor.wait();
                } catch (InterruptedException e) {
                    //ignore
                }
            }
        }
    }
    public static void main(String[] args) throws Exception {
        OddEvenPrinter printer = new OddEvenPrinter(100, 0);
        Thread thread1 = new Thread(printer::print, "thread-1");
        Thread thread2 = new Thread(printer::print, "thread-2");
        thread1.start();
        thread2.start();
        Thread.sleep(Integer.MAX_VALUE);
    }
}