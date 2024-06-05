package com.fanlm;

import com.fanlm.cache.IGlobalCache;
import com.fanlm.controller.TestController;
import com.fanlm.entity.Action;
import com.fanlm.mapper.ActionMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
class Java8TestApplicationTests {

    @Resource
    private TestController testController;

    @Autowired
    private ActionMapper mapper;

    @Test
    void contextLoads() {
        testController.test();
    }

    @Test
    void testLinkedList() {
        Action action = mapper.selectById("1");

    }


    @Autowired
    private IGlobalCache globalCache;

    @Test
    public void test() {
        globalCache.set("key2", "value3");
        globalCache.lSetAll("list", Arrays.asList("hello", "redis"));
        List<Object> list = globalCache.lGet("list", 0, -1);
        System.out.println(globalCache.get("key2"));
    }
}
