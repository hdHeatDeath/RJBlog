package com.reji;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.scheduling.annotation.EnableScheduling;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@MapperScan("com.reji.mapper")
@EnableScheduling
@EnableSwagger2
public class ReJiBlogApplication {
    public static void main(String[] args) {
        SpringApplication.run(ReJiBlogApplication.class, args);
    }
}
