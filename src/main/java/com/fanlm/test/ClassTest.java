package com.fanlm.test;

import javax.annotation.PostConstruct;

/**
 * @ClassName: ClassTest
 * @Description:
 * @Author: fanLeiMin
 * @Date: 2023/3/28 16:58
 */
public class ClassTest {
    public static void main(String[] args) {
        TestA testA = new TestA();
    }


    static class TestA{

        public TestA(){
            System.out.println("construst");
        }

        @PostConstruct
        void  init(){
            System.out.println("init");
        }
    }
}
