package com.fanlm.utils;

/**
 * 源：https://blog.csdn.net/I_r_o_n_M_a_n/article/details/125457236
 */
public class BitMapUtil {
    private final long[] bitMap;

    /**
     * 构造函数
     *
     * @param max 位图存储着 [0,max]的元素
     */
    public BitMapUtil(int max) {
        bitMap = new long[(max + 64) >> 6];
    }

    public static void main(String[] args) {
        System.out.println((63 + 64) >> 6);

        BitMapUtil bitMapUtil = new BitMapUtil(100);
        bitMapUtil.add(62);
        bitMapUtil.add(64);
        bitMapUtil.contains(98);
        bitMapUtil.contains(99);
    }

    /**
     * 往位图里面添加元素
     *
     * @param num 待添加的元素
     */
    public void add(int num) {
        bitMap[num >> 6] |= (1L << (num & 63));
    }

    /**
     * 删除位图里面的元素
     *
     * @param num 待删除的元素
     */
    public void delete(int num) {
        bitMap[num >> 6] &= ~(1L << (num & 63));
    }

    /**
     * 观察目标元素num在位图中是存在
     *
     * @param num 目标元素
     * @return 存在返回1，否则返回0
     */
    public boolean contains(int num) {
        return (bitMap[num >> 6] & (1L << (num & 63))) != 0;
    }
}
