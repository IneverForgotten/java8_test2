package com.fanlm.utils;

import java.util.function.BiConsumer;
import java.util.function.Consumer;

/**
 * @ClassName: LambdaUtils
 * @Description:
 * @Author: fanLeiMin
 * @Date: 2022/1/5 16:56
 */
public class LambdaUtils {

    public static <T> Consumer<T> consumerWithIndex(BiConsumer<T, Integer> consumer) {
        class Obj {
            int i;
        }
        // 只会被调用一次，原因看java.lang.Iterable#forEach
        Obj obj = new Obj();
        // 返回的Consumer函数
        return t -> {
            int index = obj.i++;
            // 这里执行System.out.println("list[" + index + "]=" + item),消费指定泛型的数据。
            consumer.accept(t, index);
        };
    }
}
