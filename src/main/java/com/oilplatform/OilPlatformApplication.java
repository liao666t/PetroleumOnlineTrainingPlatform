package com.oilplatform;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@MapperScan("com.oilplatform.modules.*.mapper")
@EnableScheduling
public class OilPlatformApplication {
    public static void main(String[] args) {
        SpringApplication.run(OilPlatformApplication.class, args);
        System.out.println("Amazing Teaching 石油专业线上智能教学实训平台启动成功！");
    }
}