package com.ccc;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;

@SpringBootApplication
@MapperScan("com.ccc.mapper")
public class TokenApplication {

    public static void main(String[] args) {
        SpringApplication.run(TokenApplication.class,args);
    }

}