package cn.centychen.example.spring.cloud.biz.a.controller;

import cn.centychen.example.spring.cloud.biz.a.service.RemoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Cent.Chen
 * @Description
 * @date 2019/8/2 10:16 AM
 */
@RestController
@RequestMapping("/hello")
public class HelloController {

    @Autowired
    private RemoteService remoteService;

    /**
     * 示例方法
     *
     * @return
     */
    @GetMapping
    public String sayHello() {
        return "Hello,This is Biz-A Service.";
    }

    /**
     * 示例方法：调用服务B
     *
     * @return
     */
    @GetMapping(path = "/call2b")
    public String sayHello2B() {
        return remoteService.sayHello();
    }
}
