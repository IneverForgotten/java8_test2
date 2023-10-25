package com.fanlm.cache;


import com.google.common.hash.BloomFilter;
import com.google.common.hash.Funnels;

import java.math.BigInteger;
import java.util.HashSet;

/**
 * 布隆过滤器 guava 实现
 *
 * 应用场景：
 * 数据库、爬虫、防缓存击穿
 * 需要精确知道某个数据不存在
 */
public class BloomGuava {
    // 预计要插入多少数据
    private static int size = 1000000;

    // 期望的误判率
    // 降低误报率，但相应的 CPU、内存的消耗就会提高；这就需要根据业务需要自行权衡
    private static double fpp = 0.01;

    private static BloomFilter<Integer> bloomFilter = BloomFilter.create(Funnels.integerFunnel(), size, fpp);

    public static void main(String[] args) {

        long start = System.currentTimeMillis();
        // 插入数据
        for (int i = 0; i < 1000000; i++) {
            bloomFilter.put(i);
        }
        int count = 0;
        for (int i = 1000000; i < 2000000; i++) {
            if (bloomFilter.mightContain(i)) {   //mightContain  是否存在函数
                count++;
            }
        }
        System.out.println("总共的误判数: " + count);
        System.out.println("用的时间: " + (System.currentTimeMillis() - start));
    }



}
