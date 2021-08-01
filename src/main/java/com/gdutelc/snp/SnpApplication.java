package com.gdutelc.snp;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@MapperScan("com.gdutelc.snp.dao")
@SpringBootApplication
public class SnpApplication {

    public static void main(String[] args) {
        SpringApplication.run(SnpApplication.class, args);
    }

}
