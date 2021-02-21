package com.fanlm.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;

/**
 * @program: java8_test
 * @description: test 初始化bean
 * @author: flm
 * @create: 2021-01-22 15:57
 **/
@Configuration
public class TestInitializingBean implements InitializingBean {
    @PostConstruct
    public void init(){
        System.out.println("----初始化  PostConstruct ----");
    }

    @Override
    public void afterPropertiesSet() throws Exception {

        System.out.println("----初始化  InitializingBean ----");
    }
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("----初始化  postProcessAfterInitialization");
        return bean;
    }
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("----初始化  postProcessBeforeInitialization");
        return bean;
    }


}
