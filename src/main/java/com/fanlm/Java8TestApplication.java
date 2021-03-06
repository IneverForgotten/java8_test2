package com.fanlm;

import com.fanlm.config.inter.EnableLog;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
//@EnableLog
@EnableAsync
@MapperScan("com.fanlm.dao")
public class Java8TestApplication {

    @Bean
    public SpringBeanUtil getSpringBeanUtil() {
        return new SpringBeanUtil();
    }







    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Java8TestApplication.class, args);
    }

}

