package com.fanlm.test.function;

import io.swagger.models.auth.In;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @ClassName: Use_Predicate
 * @Description:
 * @Author: fanLeiMin
 * @Date: 2022/1/5 8:43
 */
public class Use_Predicate {
    public static void main(String[] args) {
        test(a -> a.contains("oa"));
        testList(a -> a.stream().filter( e -> e.contains("a")).collect(Collectors.toList()).contains("ab"));
        System.out.println(testAndAnd(a -> a.contains("a"), b -> b.equals("aaa")));

        System.out.println(not((String s) -> s.contains("j")).test("java"));

        String[] array = { "迪丽热巴,女", "古力娜扎,女", "马尔扎哈,男", "赵丽颖,女" };
        getFemaleAndname((s) -> s.split("\\,")[0].length() == 4,
                (s) -> s.split("\\,")[1].equals("女"), array);
    }

    private static void test(Predicate<String> predicate){
        System.out.println(predicate.test("ooooaaaacccc"));
    }

    private static void testList(Predicate<List<String>> predicate){
        List<String> lit = new ArrayList<>();
        lit.add("aa");
        lit.add("b");
        lit.add("c");
        lit.add("ac");
        System.out.println(predicate.test(lit));
    }

    private static boolean testAndAnd(Predicate<String> predicate1, Predicate<String> predicate2){
        return predicate1.and(predicate2).test("aaa");
    }

    private static <T> Predicate<T> not(Predicate<? super T> target) {
        Objects.requireNonNull(target);
        return (Predicate<T>)target.negate();
    }

    private static void getFemaleAndname(Predicate<String> one, Predicate<String> two, String[] arr) {
        for (String string : arr) {
            if (one.and(two).test(string)) {
                System.out.println(string);
            }
        }
    }

}
