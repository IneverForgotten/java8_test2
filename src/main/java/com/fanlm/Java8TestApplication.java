package com.fanlm;

import com.fanlm.config.inter.EnableLog;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableAsync;

@SpringBootApplication
//@EnableLog
@EnableAsync
public class Java8TestApplication {

    public static void main(String[] args) {
        SpringApplication.run(Java8TestApplication.class, args);
    }

}
