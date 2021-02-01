package com.fanlm.service;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

/**
 * @program: java8_test
 * @description: test async
 * @author: flm
 * @create: 2021-01-22 15:28
 **/
@Service
public class TestAsyncService implements TestService, BeanFactoryAware , ApplicationContextAware {

    @Autowired
    private AsyncService testAsyncService;

    private BeanFactory beanFactory;

    private ApplicationContext applicationContext;

    @Override
    public void test(){
        System.out.println(" test start ");
        testAsyncService.testAsync();
//        System.out.println(" test end  ");

    }
    @Override
    public void testAsync(){
        //T同类调用不行，不会走AOP了。。
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }
}
