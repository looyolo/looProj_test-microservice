package com.example.microserviceeurekauser;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

@SpringBootApplication
@EnableEurekaClient
@RestController
public class MicroserviceEurekaUserApplication {
    @RequestMapping("/hello")
    public String home() {
        return "hello world!";
    }
    public static void main(String[] args) {
        SpringApplication.run(MicroserviceEurekaUserApplication.class, args);
    }

    /*
    * 代码解释：
    *     RestTemplate 是 Spring 提供的用于访问 Rest 服务的客户端示例，
    *       它提供了多种便捷访问远程 Http 服务的方法，能够大大提高客户端的编写效率。
    *
    *  */
    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }
}
