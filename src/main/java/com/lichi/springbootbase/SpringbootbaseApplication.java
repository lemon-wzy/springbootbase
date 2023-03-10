package com.lichi.springbootbase;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

/**
 * @SpringBootApplication 来标注一个主程序类，说明这是一个Spring Boot应用
 * @author lich
 */
@SpringBootApplication
@EnableCaching
public class SpringbootbaseApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringbootbaseApplication.class, args);
    }

}
