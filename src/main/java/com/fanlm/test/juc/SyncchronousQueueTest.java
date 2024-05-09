package com.fanlm.test.juc;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.SynchronousQueue;

public class SyncchronousQueueTest {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        final SynchronousQueue<Integer> queue = new SynchronousQueue<Integer>();
        CompletableFuture.runAsync(() -> {
            try {
                queue.put(1);
                System.out.println("put  =  " + queue.offer(2));
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        });
        CompletableFuture.supplyAsync(() -> {
            try {
                Thread.sleep(1000);
                return queue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).thenAccept( result -> {
            System.out.println("result  =  " + result);
        }).supplyAsync( () -> {
            try {
                Thread.sleep(1000);
                return queue.take();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }).thenAccept( result -> {
            System.out.println("result  =  " + result);
        }).get();


    }
}
