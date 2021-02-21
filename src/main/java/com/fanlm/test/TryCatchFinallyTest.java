package com.fanlm.test;

import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

public class TryCatchFinallyTest {
    public static void main(String[] args) {
        int n = 6 - 1;
        n |= n >>> 1;
        n |= n >>> 2;
        n |= n >>> 4;
        n |= n >>> 8;
        n |= n >>> 16;
        int c = (n < 0) ? 1 : (n >= 1234) ? 1234 : n + 1;

//        AbstractQueuedSynchronizer

        System.out.println(test(0));
//        System.out.println(test(3));
    }



    public static int test (int a ){
        try {
            int b = 10/a;
            return b;
        }catch (Exception e){
//            throw new RuntimeException("TEST");
//            return 1/a;
            return 1;
        }finally {
            if (a == 3) return 0;
        }
    }

}
