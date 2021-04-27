package com.fanlm;

import com.fanlm.dao.ActionMapper;
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
@MapperScan("com.fanlm.dao")
public class Java8TestApplication {

    private static final Logger log = Logger.getLogger(Java8TestApplication.class);

    @Bean
    public SpringBeanUtil getSpringBeanUtil() {
        return new SpringBeanUtil();
    }
    public static void main(String[] args) {
        ConfigurableApplicationContext run = SpringApplication.run(Java8TestApplication.class, args);
        ActionMapper actionMapper = run.getBean("actionMapper", ActionMapper.class);
        log.info(actionMapper);
    }

}

