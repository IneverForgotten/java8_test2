package com.fanlm.config;

import com.fanlm.filter.LogFilter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @program: java8_test
 * @description: 注册filter
 * @author: flm
 * @create: 2021-01-22 15:11
 **/
@Configuration
public class LogFilterWebConfig {
    @Bean
    public LogFilter timeFilter(){
        return new LogFilter();
    }
}
