package com.fanlm.config;

/**
 * @program: java8_test
 * @description: Swagger2 配置
 * @author: flm
 * @create: 2021-01-28 16:10
 **/
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.ParameterBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.schema.ModelRef;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.ArrayList;
import java.util.List;

@Configuration
@EnableSwagger2
public class Swagger2Config {

    /**
     * swagger2启用的注解，默认不启用
     */
    @Value("${swagger2.enable:false}")
    private boolean enable;

    @Bean
    public Docket createRestApi() {

        /**
         * 添加tokenuser header参数
         */
        ParameterBuilder aParameterBuilder = new ParameterBuilder();
        aParameterBuilder.name("token").description("sessionId").modelRef(new ModelRef("string")).parameterType("header").required(true).build();

        List<Parameter> aParameters = new ArrayList();
        aParameters.add(aParameterBuilder.build());

        return new Docket(DocumentationType.SWAGGER_2)
                .enable(enable)
                .apiInfo(apiInfo())
                //屏蔽modelattribute参数，也可以在每个controller上使用@apiignore
//                .ignoredParameterTypes(TokenUser.class)
                .globalOperationParameters(aParameters)
                .select()
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                //页面标题
                .title("test API")
                //创建人
                .contact(new Contact("flm", "", ""))
                //版本号
                .version("1.0")
                //描述
                .description("API 描述")
                .build();
    }
}

