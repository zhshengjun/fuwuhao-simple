package com.stupidzhang.start;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackages = {
        "com.stupidzhang.start", "com.stupidzhang.keywords",
        "com.stupidzhang.weixin", "com.stupidzhang.convert",
        "com.stupidzhang.jingfen","com.stupidzhang.schedules",
        "com.stupidzhang.cps"
})
@MapperScan(basePackages = {"com.stupidzhang.keywords.mapper","com.stupidzhang.jingfen.mapper","com.stupidzhang.cps.mapper"})
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
public class StartApplication {

    public static void main(String[] args) {
        SpringApplication.run(StartApplication.class, args);
    }

}
