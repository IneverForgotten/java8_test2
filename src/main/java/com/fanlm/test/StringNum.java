package com.fanlm.test;

/**
 * @program: java8_test
 * @description: 字符串常量池 测试
 * @author: flm
 * @create: 2021-01-28 11:16
 **/
public class StringNum {
    public static void main(String[] args) {
        //对象创建两种方式
        //第一种：创建了几个对象呢？ 1个或2个 第一个对象:如果常量池中已有"abc"直接返回引用，如果不存在就创建一个；第二个对象:在堆内存中new的String对象。
        String s1 = new String("abc");//s1指向堆内存对象的引用地址

        //第二种：堆内存字符常量池（首先检查字符串常量池中是否存在，如果存在则直接引用，不存在添加进入）
        String s2 = "abc";//指向字符串常量池引用
        String s3 = "abc";//指向字符串常量池引用
        System.out.println(s1==s2);//false
        System.out.println(s2==s3);//true
        System.out.println("1>>>>>>>>>>>>>>>");

        String s4 = new String("hello");//s4指向堆内存地址的引用地址
        String intern = s4.intern();//调用intern方法，查询字符常量池是否存在字符串，如果不存在就放入字符常量池，如果存在返回地址引用
        String s5 = "hello";
        System.out.println(s4==s5);//false
        System.out.println("2>>>>>>>>>>>>>>>");

        //字符串拼接
        String s6_1 = "123" + "123";//在字符串常量池创建
        String s6_2 = new String("123") + new String("123");//在对内存中创建
        String s7 = "123123";
        System.out.println(s6_1==s6_2);//false
        System.out.println(s6_1==s7);//true
        System.out.println(s6_2==s7);//false
        System.out.println("3>>>>>>>>>>>>>>>");

        String s8 = new String("456") + "4561";//在字符串常量池创建
        String s9 = "4564561";
        System.out.println(s8==s9);//false
        System.out.println("4>>>>>>>>>>>>>>>");

        final String s10 = "abc";
        final String s11 = new String("abc");
        System.out.println("s1与s7：" + (s1 == s10));// false
        System.out.println("s1与s8：" + (s1 == s11));// false
        System.out.println("s11与s10：" + (s10 == s11));// false


    }
}

