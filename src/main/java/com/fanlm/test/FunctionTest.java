package com.fanlm.test;

import cn.hutool.core.lang.func.LambdaUtil;
import com.fanlm.test.function.MyFilrstFunction;
import com.fanlm.utils.LambdaUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * @ClassName: FunctionTest
 * @Description:
 * @Author: fanLeiMin
 * @Date: 2021/12/28 8:59
 */
public class FunctionTest {

    /*
       java.util.function提供了大量的函数式接口
       Predicate<T> 接收参数T对象，返回一个boolean类型结果
       Consumer 接收参数T对象，没有返回值
       Function 接收参数T对象，返回R对象
       Supplier 不接受任何参数，直接通过get()获取指定类型的对象
       UnaryOperator 接口参数T对象，执行业务处理后，返回更新后的T对象
       BinaryOperator 接口接收两个T对象，执行业务处理后，返回一个T对象

     */

    public static void main(String[] args) {
        //var a = new String("1234");   JAVA10
        testFirstFunction(() -> "hello world");
        testThread(() -> System.out.println("testThread start!"));

        String[] array={"abc","ab","abcd","bc"};
        Arrays.sort(array, (b,a) -> b.length() - a.length());
        System.out.println(Arrays.toString(array));

        //供应
        System.out.println(testSupplier(() -> "aaa"));

        //消费
        testConsumer( s -> System.out.println(s));
        //消费2
        formattorPersonMsg((s1) -> {
            for (int i = 0; i < s1.length; i++) {
                System.out.print(s1[i].split("\\,")[0] + " ");
            }
        }, (s2) -> {
            System.out.println();
            for (int i = 0; i < s2.length; i++) {
                System.out.print(s2[i].split("\\,")[1] + " ");
            }
        });
        System.out.println("");
        numberToString( e ->  e.stream().filter( s -> s.contains("2")).collect(Collectors.toList()).toString() + " : success");
        System.out.println(compute6(5, e -> e * 5, e -> e + 5));


        System.out.println("---------------------------------------------");
        val list = Arrays.asList("Hi", "I", "am", "Henry.Yao");
        val partition = Lists.partition(list,2);
        partition.forEach(LambdaUtils.consumerWithIndex( (item, index) -> {
            System.out.println(index + " " + item);
        }));
        val a = 1;
        System.out.println(list.stream().allMatch((s) -> s.startsWith("a")));
        list.stream().reduce((s, v) -> s + v).ifPresent(System.out::println);
        list.stream().reduce((s, v) -> s + v).ifPresent( s -> System.out.println(s + "test"));
    }

    public static void testFirstFunction(MyFilrstFunction myFilrstFunction){
        System.out.println(myFilrstFunction.firstTeest());
    }

    public static void testThread(Runnable runnable){
        new Thread(runnable).start();
    }

    public static String testSupplier(Supplier<String> suply){
        return suply.get();
    }

    public static void testConsumer(Consumer<String> consumer){
        consumer.accept("helo consumer");
    }

    public static void formattorPersonMsg(Consumer<String[]> con1, Consumer<String[]> con2) {
        // con1.accept(new String[]{ "迪丽热巴,女", "古力娜扎,女", "马尔扎哈,男" });
        // con2.accept(new String[]{ "迪丽热巴,女", "古力娜扎,女", "马尔扎哈,男" });
        // 一句代码搞定
        con1.andThen(con2).accept(new String[] { "迪丽热巴,女", "古力娜扎,女", "马尔扎哈,男" });
    }

    private static void numberToString(Function<List<String>, String> function) {
        List<String> list = new ArrayList<>();
        list.add("add");
        list.add("add2");
        list.add("add4");
        list.add("add5");
        String apply = function.apply(list);
        System.out.println("转换结果:"+apply);
    }
    // andThen 先1@后2                   compose 先2后1
    public static int compute(int source, Function<Integer, Integer> function1, Function<Integer, Integer> function2) {
        //5, e -> e * 5, e -> e + 5        50
        return function1.compose(function2).apply(source);
    }
    public static int compute2(int source, Function<Integer, Integer> function1, Function<Integer, Integer> function2) {
        //5, e -> e * 5, e -> e + 5        30
        return function1.andThen(function2).apply(source);
    }
    public static int compute3(int source, Function<Integer, Integer> function1, Function<Integer, Integer> function2) {
        //5, e -> e * 5, e -> e + 5        130
        return function1.andThen(function2).compose(function1).apply(source); //从后往前
    }
    public static int compute4(int source, Function<Integer, Integer> function1, Function<Integer, Integer> function2) {
        //5, e -> e * 5, e -> e + 5        250
        return function1.compose(function2).andThen(function1).apply(source);
    }
    public static int compute6(int source, Function<Integer, Integer> function1, Function<Integer, Integer> function2) {
        //5, e -> e * 5, e -> e + 5        1250
        return function1.compose(function1).andThen(function1).compose(function2).apply(source);
    }

}
