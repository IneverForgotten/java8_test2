package com.fanlm.thread;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.*;

/**
 * @program: java8_test
 * @description: 线程池 test
 * @author: flm
 * @create: 2021-02-04 15:27
 **/
public class ThreadPoolExecutorExample {
    public static void main(String[] args) throws ExecutionException, InterruptedException {

//        ThreadPoolExecutor executor1 = new ScheduledThreadPoolExecutor(1,)

//        ThreadPoolExecutor executor = new ThreadPoolExecutor(5,10,1000, TimeUnit.SECONDS,new ArrayBlockingQueue<>(100),new ThreadPoolExecutor.CallerRunsPolicy());

//        for (int i = 0; i < 10; i++) {
//            //创建WorkerThread对象（WorkerThread类实现了Runnable 接口）
//            Runnable worker = new MyRunnable2();
//            //执行Runnable
////            executor.execute(worker);
//            String s1 = "";
//            Future<String> submit = executor.submit(new Callable<String>() {
//                @Override
//                public String call() throws Exception {
//                    return ""+args.toString();
//                }
//            });
//            String s = submit.get();
//            System.out.println(s);
//        }
        List<String> list = new ArrayList<>();
        list.add("s");
        list.add("s");
        list.add("s");
        list.add("s");
        list.add("s");
        long l = System.currentTimeMillis();
//        for (int i = 0; i < 5; i++) {
//            //创建WorkerThread对象（WorkerThread类实现了Runnable 接口）
//            Runnable worker = new MyRunnable3();
//            //执行Runnable
        ThreadPoolExecutor executor = new ThreadPoolExecutor(2,10,1000, TimeUnit.SECONDS,new ArrayBlockingQueue<>(100),new ThreadPoolExecutor.CallerRunsPolicy());
        for (int i = 0; i < 10; i++) {
            //创建WorkerThread对象（WorkerThread类实现了Runnable 接口）
            Runnable worker = new MyRunnable211();
            //执行Runnable
//            executor.execute(worker);
            String s1 = "";
            Future<String> submit = executor.submit(new Callable<String>() {
                @Override
                public String call() throws Exception {
                    return ""+args.toString();
                }
            });
            String s = submit.get();
            System.out.println(s);
        }

        //终止线程池
        executor.shutdown();
        while (!executor.isTerminated()) {
        }
        System.out.println("Finished all threads");
    }
}
class MyRunnable implements Runnable {

    private String command;

    public MyRunnable(String s) {
        this.command = s;
    }

    @Override
    public void run() {
        System.out.println(Thread.currentThread().getName() + " Start. Time = " + new Date());
        processCommand();
        System.out.println(Thread.currentThread().getName() + " End. Time = " + new Date());
    }

    private void processCommand() {
        try {
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @Override
    public String toString() {
        return this.command;
    }
}