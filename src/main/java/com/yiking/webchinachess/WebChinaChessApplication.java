package com.yiking.webchinachess;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@MapperScan(basePackages = "com.yiking.webchinachess.mapper")
@SpringBootApplication
@EnableCaching
public class WebChinaChessApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebChinaChessApplication.class, args);
    }

}
