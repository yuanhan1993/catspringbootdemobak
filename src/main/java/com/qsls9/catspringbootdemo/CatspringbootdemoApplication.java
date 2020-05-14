package com.qsls9.catspringbootdemo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("com.qsls9.catspringbootdemo.dao")
public class CatspringbootdemoApplication {

    public static void main(String[] args) {
        SpringApplication.run(CatspringbootdemoApplication.class, args);
    }

}
