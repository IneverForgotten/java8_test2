package com.fanlm;

import com.fanlm.utils.SpringBeanUtil;
import org.apache.log4j.Logger;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableAsync;


@SpringBootApplication
//@EnableLog
@EnableAsync
@MapperScan("com.fanlm.mapper")
public class Java8TestApplication {

    private static final Logger log = Logger.getLogger(Java8TestApplication.class);
    public static void main(String[] args) {
        SpringApplication.run(Java8TestApplication.class, args);
    }

}

