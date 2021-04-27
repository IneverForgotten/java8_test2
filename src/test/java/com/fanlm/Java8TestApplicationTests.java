package com.fanlm;

import com.fanlm.controller.TestController;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.LinkedList;
import java.util.List;

@SpringBootTest
class Java8TestApplicationTests {

    @Resource
    private TestController testController;

    @Test
    void contextLoads() {
        testController.test();
    }

    @Test
    void testLinkedList() {


    }

}
