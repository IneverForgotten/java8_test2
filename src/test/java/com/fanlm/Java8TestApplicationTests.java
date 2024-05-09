package com.fanlm;

import com.fanlm.controller.TestController;
import com.fanlm.entity.Action;
import com.fanlm.mapper.ActionMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
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

}
