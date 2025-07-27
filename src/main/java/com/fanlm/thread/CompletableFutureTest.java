package com.fanlm.thread;

import io.swagger.models.auth.In;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CompletableFutureTest {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ExecutorService executor = Executors.newFixedThreadPool(5);
        CompletableFuture<Integer> cf = new CompletableFuture<>();
        cf.complete(1);
        Integer i = cf.get();
        System.out.println(i);


        CompletableFuture<Integer> cf1 = CompletableFuture.supplyAsync(() -> {
            System.out.println("test1");
            return 1;
        }, executor);
        CompletableFuture<Integer> cf2 = CompletableFuture.completedFuture(2);

        CompletableFuture<Integer> cf3 = cf1.thenApply((v1) -> {
            System.out.println("test3 " + v1);
            return v1 + 2;
        });

        CompletableFuture<Integer> cf5 = cf2.thenApply((v1) -> {
            System.out.println("test5 " + v1);
            return v1 + 3;
        });

        CompletableFuture<Integer> cf4 = cf1.thenCombine(cf2, (v1, v2) -> {
            System.out.println("test4 " + v1 + v2);
            try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            return 4;
        });

        CompletableFuture<Void> cf6 = CompletableFuture.allOf(cf3, cf5, cf4);

        cf6.thenApply((v) -> {
            System.out.println("test6");
            System.out.println(cf3.join());
            System.out.println(cf4.join());
            System.out.println(cf5.join());
            return 6;
        });

    }
}
