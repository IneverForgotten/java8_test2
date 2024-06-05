package com.fanlm.test;

import cn.hutool.json.JSONUtil;
import com.fanlm.utils.HttpUtils;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;

import java.io.ByteArrayOutputStream;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

import static com.alibaba.fastjson.util.IOUtils.UTF8;

/**
 * @ClassName: BasicsTest
 * @Description:
 * @Author: fanLeiMin
 * @Date: 2022/6/10 9:57
 */
public class BasicsTest {
    public static void main(String[] args) {
        // 参数 {"bexCode":"123456","g_funcid":"100004","g_kjseid":"100004"}
        String str = "{\"bexCode\":\"123456\",\"g_funcid\":\"100004\",\"g_kjseid\":\"100004\"}";
        // post post http://10.144.0.48:7001/jros
        String url = "http://10.144.0.48:7001/jros";
        // jsonString to map

        Map<String, String> map = JSONUtil.parse(str).toBean(Map.class);
        Map<String, String> map1 = new HashMap<>();
        map1.put("Content-Type", "text/plan");
        String post = HttpUtils.post(url, map1, map);
        System.out.println(post);
    }


}
