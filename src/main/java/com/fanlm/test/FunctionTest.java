package com.fanlm.test;

import cn.hutool.core.lang.func.LambdaUtil;
import cn.hutool.core.util.CharsetUtil;
import cn.hutool.core.util.HexUtil;
import cn.hutool.crypto.SecureUtil;
import cn.hutool.crypto.asymmetric.KeyType;
import cn.hutool.crypto.asymmetric.RSA;
import com.fanlm.test.function.MyFilrstFunction;
import com.fanlm.utils.LambdaUtils;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import lombok.val;
import lombok.var;

import javax.crypto.Mac;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Base64;
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

    public static void main(String[] args) throws NoSuchAlgorithmException {

        String privateKey = "MIICdgIBADANBgkqhkiG9w0BAQEFAASCAmAwggJcAgEAAoGBALIzaIRRl9fg2jit1gU3mNNFyhypQEmyUVQ6H04mOozPdGzs5+Pbvaz/9ozfHjLtInuwjtSALnpJz6wdR1QeLa4oiuyEPWBFGiCndIcWdeKVI3OQLRWnV9YRYpnLVvjZ48sYqgTMZ4U1YcV7Sl9G0AzSyaFVfdFXfnwjmS4j6D29AgMBAAECgYEAmyBrfLx2vaPs8+hIZlRGwqx/TEH+R+lmKTdLp0FaONgjlusI1u+kh6RvIaTdaiHKofhJ7i0DyMrWcRMv08dNpVeiE+ainggSTtsdJdrVEmpseRQUA94kCrZQExVyVtqh5OjAi7PJ60hhqDlij43ZVlw5d9Onp6e6+29wCKkzFSECQQDs6NbEpitqR0U0Oh16QX3jpNDvUoElKmyl2o0WJpMqHi476G2N5JeFMjf81zOGh9/EuR3VjxwoTZxRujtk2BJZAkEAwI96g7lzK4wO7O9RtPYAg5q8hqunMCWlJKzA1evfIzzvLWchiwdy8rjwgjPA3kr76tzzbHpsukKf/ebc/J6yBQJAZlop/4ezFhV4hpndBmapFuKsCdlhRkdP7U/AyKMdzYKAgw1l13m9JKSPn8Lx1dt6B6nag9tyVM9DC+QjqOvY8QJAFAJUvr9Ugl/pZSFxIha18vbvRCcuFkizIl55I0GBTE4WpGclCydZAHPLOhxanD66cqtG+Cy4g5pMubt1lyJ+aQJAf58hvv0J/haXO5cdSIiDbWihx/F2Un1HSevo0QeYyY8HxdvD3L4KXEPtFmT5Xb1agloozTBejtY6t/XAymEdWA==";
        String publicKeyBase64 = "MIGfMA0GCSqGSIb3DQEBAQUAA4GNADCBiQKBgQCyM2iEUZfX4No4rdYFN5jTRcocqUBJslFUOh9OJjqMz3Rs7Ofj272s//aM3x4y7SJ7sI7UgC56Sc+sHUdUHi2uKIrshD1gRRogp3SHFnXilSNzkC0Vp1fWEWKZy1b42ePLGKoEzGeFNWHFe0pfRtAM0smhVX3RV358I5kuI+g9vQIDAQAB";
        //System.out.println(privateKey);
        //System.out.println(publicKeyBase64);

        RSA priRsa = new RSA(privateKey, null);
        // 初始化RSA工具并设置公钥
        RSA pubRsa = new RSA(null, publicKeyBase64);

        String testString = "{\"orderId\":1234567}";
        // 公钥加密
        String encodeStr = pubRsa.encryptBase64(testString, KeyType.PublicKey);
        System.out.println("encodeStr by public key:" + encodeStr);
        // 私钥解密
        String decodeStr = priRsa.decryptStr(encodeStr, KeyType.PrivateKey);
        System.out.println("decodeStr by private key:" + decodeStr);

        // 私钥加密
        String encodeStr1 = priRsa.encryptBase64(testString, KeyType.PrivateKey);
        System.out.println("encodeStr1 by private key:" + encodeStr1);
        // 公钥解密
        String decodeStr1 = pubRsa.decryptStr("ZnXYu8XJu51XHmNf71PgTmuD0zZKbNlUljSlQoNMZREkzaEjyxK1bjlmCdNnFVSbkAfcN5SBXWSZyN+Yg0RKM01yBtU+d9PkBzqIJh2E+Ps82daxr03dXqy/ji+tJQafq8eBVbif5jxi+I0xJURqV/5SnsdG8PENXtqCQsaHzKx3YnHHXIi+AqM1kIpxiAUd6O9RpvuG/yfIg04XONtzha/FtFGnfRJ0qMzXE5OkBTNVc8GK/ZndecuYouQYO+mO54un4BiOqbMqsBK6C5BZob+tZS1PQHDeU7cQA53T9s3urXilePWJQeSpcbsaO3zKrV2QdG/EBjhEINA5mwJJOQ==", KeyType.PublicKey);
        System.out.println("decodeStr1 by public key:" + decodeStr1);


        //var a = new String("1234");   JAVA10
        testFirstFunction(() -> "hello world");
        testThread(() -> System.out.println("testThread start!"));

        String[] array={"abc","ab","abcd","bc"};
        Arrays.sort(array, (b,a) -> b.length() - a.length());
        System.out.println(Arrays.toString(array));

        //供应
        System.out.println(testSupplier(() -> "aaa"));

        Consumer<String> con = testConsumer( s -> System.out.println(s));

        con.accept("a");
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

    public static Consumer testConsumer(Consumer<String> consumer){
        consumer.accept("helo consumer");
        return s -> System.out.println(s);
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
