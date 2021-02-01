package com.fanlm.utils;

import com.google.gson.*;
import com.google.gson.annotations.Expose;
import com.google.gson.internal.LinkedTreeMap;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.util.*;

/**
 * @program: java8_test
 * @description: GsonUtil 工具类
 * @author: flm
 * @create: 2021-01-28 16:07
 **/
public class GsonUtil {

    private static Gson gson = null;

    /**
     * 返回gson对象
     * @return
     */
    public static synchronized Gson getGson() {

        if (gson == null)
            gson = getGsonBuilder().create();
        return gson;
    }

    /**
     * gson的@Expose注解在使用上并不灵活
     * gsonbuilder的excludeFieldsWithoutExposeAnnotation也会过滤掉没加@Expose的字段，那就要求所有输入输出都得加@Expose
     * 实现成只过滤加了@Expose且设置为false的字段
     */
    private static ExclusionStrategy serializationExclusionStrategy = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            Expose expose = fieldAttributes.getAnnotation(Expose.class);
            if (expose != null && expose.serialize() == false)
                return true;

            return false;
        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
    };

    private static ExclusionStrategy deserializationExclusionStrategy = new ExclusionStrategy() {
        @Override
        public boolean shouldSkipField(FieldAttributes fieldAttributes) {
            Expose expose = fieldAttributes.getAnnotation(Expose.class);
            if (expose != null && expose.deserialize() == false)
                return true;

            return false;
        }

        @Override
        public boolean shouldSkipClass(Class<?> aClass) {
            return false;
        }
    };

    /**
     * Object适配器
     * gson自带适配器读取时未区分int和double，都会序列化成double(1会变成1.0)
     * 增加了int和double支持
     */
    public static class ObjectTypeAdapterEx extends TypeAdapter<Object> {

        @Override
        public void write(JsonWriter out, Object value) throws IOException {
            //不处理
        }

        /**
         * copy自com.google.gson.internal.bind.ObjectTypeAdapter
         * 对NUMBER处理修改
         * @param in
         * @return
         * @throws IOException
         */
        @Override
        public Object read(JsonReader in) throws IOException {
            JsonToken token = in.peek();
            switch(token) {
                case BEGIN_ARRAY:
                    List<Object> list = new ArrayList();
                    in.beginArray();

                    while(in.hasNext()) {
                        list.add(this.read(in));
                    }

                    in.endArray();
                    return list;
                case BEGIN_OBJECT:
                    Map<String, Object> map = new LinkedTreeMap();
                    in.beginObject();

                    while(in.hasNext()) {
                        map.put(in.nextName(), this.read(in));
                    }

                    in.endObject();
                    return map;
                case STRING:
                    return in.nextString();
                case NUMBER:
                    //return in.nextDouble();

                    //将其作为一个字符串读取出来
                    String numberStr = in.nextString();
                    //返回的numberStr不会为null
                    if (numberStr.contains(".") || numberStr.contains("e")
                            || numberStr.contains("E")) {
                        return Double.parseDouble(numberStr);
                    }
                    return Long.parseLong(numberStr);

                case BOOLEAN:
                    return in.nextBoolean();
                case NULL:
                    in.nextNull();
                    return null;
                default:
                    throw new IllegalStateException();
            }
        }
    }

    /**
     * 返回gsonbuilder对象
     * @return
     */
    public static GsonBuilder getGsonBuilder() {
        /**
         * 设置时间格式
         * 禁用html转义
         * 设置@Expose过滤
         */
        return new GsonBuilder().setDateFormat("yyyy-MM-dd HH:mm:ss").disableHtmlEscaping().addSerializationExclusionStrategy(serializationExclusionStrategy).
                addDeserializationExclusionStrategy(deserializationExclusionStrategy).registerTypeAdapter(new TypeToken<Map<String, Object>>(){}.getType(), new ObjectTypeAdapterEx());
    }

    /**
     * 定义比较规则
     *
     * @return
     */
    private static Comparator<String> getComparator() {
        return (s1, s2) -> s1.compareTo(s2);
    }

    /**
     * 排序
     *
     * @param e
     */
    public static void sort(JsonElement e) {
        if (e.isJsonNull() || e.isJsonPrimitive()) {
            return;
        }

        if (e.isJsonArray()) {
            JsonArray a = e.getAsJsonArray();
            Iterator<JsonElement> it = a.iterator();
            it.forEachRemaining(i -> sort(i));
            return;
        }

        if (e.isJsonObject()) {
            Map<String, JsonElement> tm = new TreeMap<>(getComparator());
            for (Map.Entry<String, JsonElement> en : e.getAsJsonObject().entrySet()) {
                tm.put(en.getKey(), en.getValue());
            }

            String key;
            JsonElement val;
            for (Map.Entry<String, JsonElement> en : tm.entrySet()) {
                key = en.getKey();
                val = en.getValue();
                e.getAsJsonObject().remove(key);
                e.getAsJsonObject().add(key, val);
                sort(val);
            }
        }
    }

    /**
     * 根据json key排序
     * 避免排序后key不一致，不使用gsonutil
     * @param json
     * @return
     */
    public static String sortJson(String json) {
        try {
            Gson g = new Gson();
            JsonParser p = new JsonParser();
            JsonElement e = p.parse(json);
            sort(e);
            return g.toJson(e);

        } catch (Exception e) {
            e.printStackTrace();

        }
        return null;
    }

}
