package com.fanlm.test;

import java.util.*;

/**
 * @program: java8_test
 * @description: LAMNDA TEST
 * @author: flm
 * @create: 2021-02-07 14:04
 **/
public class LamndaTest {
    public LamndaTest() {
    }

    public static void main(String[] args) {
        List<String> strings = Arrays.asList("1", "2", "3");
        //传统foreach
        for (String s : strings) {
            System.out.println(s);
        }
        //Lambda foreach
        strings.forEach((s) -> System.out.println(s));
        //or
        strings.forEach(System.out::println);
        //map
        Map<Integer, String> map = new HashMap<>();
        map.forEach((k,v)->System.out.println(v));

        map.forEach((k,v) -> {
            System.out.println(k);
            System.out.println(v);
        });


        List<Integer> strings1 = Arrays.asList(1, 2, 3);
        //Lambda
        Collections.sort(strings1, (Integer o1, Integer o2) -> { return o1-o2; });
        //Lambda
        Collections.sort(strings1, (Integer o1, Integer o2) -> o1 - o2);

        Runnable aNew = LamndaTest::new;
    }
}
