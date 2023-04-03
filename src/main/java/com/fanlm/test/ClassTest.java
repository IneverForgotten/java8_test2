package com.fanlm.test;

import org.springframework.objenesis.Objenesis;
import org.springframework.objenesis.ObjenesisStd;
import org.springframework.objenesis.instantiator.ObjectInstantiator;

import javax.annotation.PostConstruct;

/**
 * @ClassName: ClassTest
 * @Description:
 * @Author: fanLeiMin
 * @Date: 2023/3/28 16:58
 */
public class ClassTest {
    public static void main(String[] args) {
        //TestA testA = new TestA();

        /* --  Objenesis -- */
        Objenesis objenesisStd = new ObjenesisStd();
        ObjenesisTest objenesisTest = objenesisStd.newInstance(ObjenesisTest.class);
        objenesisTest.test();
        System.out.println(objenesisTest);

        ObjectInstantiator<ObjenesisTest> instantiatorOf = objenesisStd.getInstantiatorOf(ObjenesisTest.class);
        ObjenesisTest objenesisTest1 = instantiatorOf.newInstance();
        ObjenesisTest objenesisTest2 = instantiatorOf.newInstance();
        System.out.println(objenesisTest1);
        System.out.println(objenesisTest2);
        objenesisTest1.test();
        /* --  Objenesis -- */

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



    public class ObjenesisTest{
        public String publicTest = "1";
        private String privateTest = "private";


        public void test(){
            System.out.println("publicTest  " + publicTest);
            System.out.println("privateTest  " + privateTest);
        }

    }


}
