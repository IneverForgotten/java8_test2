package com.fanlm.utils;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.conn.ssl.TrustSelfSignedStrategy;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.ssl.SSLContextBuilder;

import javax.net.ssl.SSLContext;
import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * HttpClient工具类
 *
 * @author ZhangYuanqiang
 * @since 2021-06-10
 */
public class HttpUtils {

    /**
     * 字符编码
     */
    private final static String UTF8 = "utf-8";
    /**
     * 字节流数组大小（1MB）
     */
    private final static int BYTE_ARRAY_LENGTH = 1024 * 1024;



    /**
     * 执行get请求获取响应
     *
     * @param url 请求地址
     * @return 响应内容
     */
    public static String get(String url) {
        return get(url, null);
    }

    /**
     * 执行get请求获取响应
     *
     * @param url     请求地址
     * @param headers 请求头参数
     * @return 响应内容
     */
    public static String get(String url, Map<String, String> headers) {
        HttpGet get = new HttpGet(url);
        return getRespString(get, headers);
    }

    /**
     * 执行post请求获取响应
     *
     * @param url 请求地址
     * @return 响应内容
     */
    public static String post(String url) {
        return post(url, null, null);
    }

    /**
     * 执行post请求获取响应
     *
     * @param url    请求地址
     * @param params 请求参数
     * @return 响应内容
     */
    public static String post(String url, Map<String, String> params) {
        return post(url, null, params);
    }

    /**
     * 执行post请求获取响应
     *
     * @param url     请求地址
     * @param headers 请求头参数
     * @param params  请求参数
     * @return 响应内容
     */
    public static String post(String url, Map<String, String> headers, Map<String, String> params) {
        HttpPost post = new HttpPost(url);
        post.setEntity(getHttpEntity(params));
        return getRespString(post, headers);
    }

    /**
     * 执行post请求获取响应（请求体为JOSN数据）
     *
     * @param url  请求地址
     * @param json 请求的JSON数据
     * @return 响应内容
     */
    public static String postJson(String url, String json) {
        return postJson(url, null, json);
    }

    /**
     * 执行post请求获取响应（请求体为JOSN数据）
     *
     * @param url     请求地址
     * @param headers 请求头参数
     * @param json    请求的JSON数据
     * @return 响应内容
     */
    public static String postJson(String url, Map<String, String> headers, String json) {
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-type", "application/json");
        post.setEntity(new StringEntity(json, UTF8));
        return getRespString(post, headers);
    }

    /**
     * 下载文件
     *
     * @param url      下载地址
     * @param path     保存路径（如：D:/images，不传默认当前工程根目录）
     * @param fileName 文件名称（如：hello.jpg）
     */
    public static void download(String url, String path, String fileName) {
        HttpGet get = new HttpGet(url);
        File dir = new File(path);
        if (!dir.exists()) {
            dir.mkdirs();
        }
        String filePath = null;
        if (Objects.isNull(path) || path.isEmpty()) {
            filePath = fileName;
        } else {
            if (path.endsWith("/")) {
                filePath = path + fileName;
            } else {
                filePath += path + "/" + fileName;
            }
        }
        File file = new File(filePath);
        if (!file.exists()) {
            try {
                file.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        try (FileOutputStream fos = new FileOutputStream(file); InputStream in = getRespInputStream(get, null)) {
            if (Objects.isNull(in)) {
                return;
            }
            byte[] bytes = new byte[BYTE_ARRAY_LENGTH];
            int len = 0;
            while ((len = in.read(bytes)) != -1) {
                fos.write(bytes, 0, len);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 获取请求体HttpEntity
     *
     * @param params 请求参数
     * @return HttpEntity
     */
    private static HttpEntity getHttpEntity(Map<String, String> params) {
        List<BasicNameValuePair> pairs = new ArrayList<BasicNameValuePair>();
        for (Entry<String, String> entry : params.entrySet()) {
            pairs.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
        }
        HttpEntity entity = null;
        try {
            entity = new UrlEncodedFormEntity(pairs, UTF8);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return entity;
    }

    /**
     * 设置请求头
     *
     * @param request 请求对象
     * @param headers 请求头参数
     */
    private static void setHeaders(HttpUriRequest request, Map<String, String> headers) {
        if (Objects.nonNull(headers) && !headers.isEmpty()) {
            // 请求头不为空，则设置对应请求头
            for (Entry<String, String> entry : headers.entrySet()) {
                request.setHeader(entry.getKey(), entry.getValue());
            }
        } else {
            // 请求为空时，设置默认请求头
            request.setHeader("Connection", "keep-alive");
            request.setHeader("Accept-Encoding", "gzip, deflate, br");
            request.setHeader("Accept", "*/*");
            request.setHeader("User-Agent",
                    "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/84.0.4147.135 Safari/537.36");
        }
    }

    /**
     * 执行请求，获取响应流
     *
     * @param request 请求对象
     * @return 响应内容
     */
    private static InputStream getRespInputStream(HttpUriRequest request, Map<String, String> headers) {
        // 设置请求头
        setHeaders(request, headers);
        // 获取响应对象
        HttpResponse response;
        try {
            if (request.getURI().toString().startsWith("https:")) {
                response = getSSLClient().execute(request);
            } else {
                response = HttpClients.createDefault().execute(request);
            }
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
        // 获取Entity对象
        HttpEntity entity = response.getEntity();
        // 获取响应信息流
        InputStream in = null;
        if (Objects.nonNull(entity)) {
            try {
                in = entity.getContent();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return in;
    }

    private static CloseableHttpClient getSSLClient() {
        try {
            // 创建信任所有证书的SSLContext
            SSLContext sslContext = SSLContextBuilder.create()
                    .loadTrustMaterial(new TrustSelfSignedStrategy())
                    .build();

            // 创建忽略主机名验证的HostnameVerifier
            NoopHostnameVerifier hostnameVerifier = new NoopHostnameVerifier();
            SSLConnectionSocketFactory sslSocketFactory = new SSLConnectionSocketFactory(sslContext, hostnameVerifier);
            CloseableHttpClient httpClient = HttpClients.custom()
                    .setSSLSocketFactory(sslSocketFactory)
                    .build();

            return httpClient;
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    /**
     * 执行请求，获取响应内容
     *
     * @param request 请求对象
     * @return 响应内容
     */
    private static String getRespString(HttpUriRequest request, Map<String, String> headers) {
        byte[] bytes = new byte[BYTE_ARRAY_LENGTH];
        int len = 0;
        try (InputStream in = getRespInputStream(request, headers);
             ByteArrayOutputStream bos = new ByteArrayOutputStream()) {
            if (Objects.isNull(in)) {
                return "";
            }
            while ((len = in.read(bytes)) != -1) {
                bos.write(bytes, 0, len);
            }
            return bos.toString(UTF8);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
