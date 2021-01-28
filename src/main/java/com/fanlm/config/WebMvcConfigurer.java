package com.fanlm.config;

import com.fanlm.utils.GsonUtil;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.google.gson.JsonSerializationContext;
import com.google.gson.JsonSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.http.converter.json.GsonHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import springfox.documentation.spring.web.json.Json;

import java.lang.reflect.Type;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.List;

/**
 * @program: java8_test
 * @description: AJAX 跨域问题解决
 * @author: flm
 * @create: 2021-01-28 16:03
 **/
@Configuration
public class WebMvcConfigurer extends WebMvcConfigurerAdapter {

    /**
     * 跨域设置
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*")
                .allowedMethods("*")
                .allowedHeaders("*")
                //设置是否允许跨域传cookie
                .allowCredentials(true)
                //设置缓存时间，减少重复响应
                .maxAge(3600);
    }

    /**
     * swagger2启用的注解，默认不启用
     */
    @Value("${swagger2.enable:false}")
    private boolean swagger2Enable;

    private class Swagger2JsonSerializer implements JsonSerializer<Json> {
        @Override
        public JsonElement serialize(Json json, Type type, JsonSerializationContext jsonSerializationContext) {
            final JsonParser jsonParser = new JsonParser();

            return jsonParser.parse(json.value());
        }
    }

    /**
     * 设置使用gson做spring序列化
     * @param converters
     */
    @Override
    public void configureMessageConverters(List<HttpMessageConverter<?>> converters) {

        //string不转换
        StringHttpMessageConverter stringConverter = new StringHttpMessageConverter(
                Charset.forName("UTF-8"));
        converters.add(0, stringConverter);

        //json转换器
        GsonHttpMessageConverter converter = new GsonHttpMessageConverter();

        //设置gson
        if (swagger2Enable)
            converter.setGson(GsonUtil.getGsonBuilder().registerTypeAdapter(Json.class, new Swagger2JsonSerializer()).create());
        else
            converter.setGson(GsonUtil.getGsonBuilder().create());

        //设置编码
        List<MediaType> types = new ArrayList<MediaType>();
        types.add(MediaType.APPLICATION_JSON_UTF8);
        converter.setSupportedMediaTypes(types);
        converter.setDefaultCharset(Charset.forName("UTF-8"));

        converters.add(1, converter);
    }


}
