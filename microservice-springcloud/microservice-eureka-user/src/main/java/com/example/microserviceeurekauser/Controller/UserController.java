package com.example.microserviceeurekauser.Controller;

import com.example.microserviceeurekauser.service.impl.UserServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

/*
 * 代码解释：
 *     编写 Controller 类，实现具体的业务请求。
 *
 *     @Autowired 注解，可以解毒为"自动连线"，情形类似于 AutoCad 作图。
 *     @RestController 注解相当于 @Controller、@ResponseBody 组合在一起使用，
 *       此注解所标注类中方法的返回值返回的将不是视图页面，而是 return 语句中的内容。
 *     @GetMapping 注解定义了请求路径，
 *       通过此请求路径即可访问对应的方法，并返回结果。
 *
 * */
@RestController
public class UserController {
    @Autowired
    private UserServiceImpl userServiceImpl;
    @Autowired
    private RestTemplate restTemplate;

    /*
     * 查找与用户有关的订单：
     *     当用户查询订单时，首先会通过用户 id 查询与用户相关的素有订单（这里省略了查询方法，并且自定义了一个订单 id ，来模拟查询出的结果），
     *     然后通过 restTemplate 对象的 getForObject 方法调用到了订单服务中的查询订单方法，进而查询订单 id 为 123 的订单信息。
     *
     * */
    @GetMapping("/findOrderByUser/{id}")
    public String findOrderByUser(@PathVariable String id) {
        // 这里假设用户只有一个订单，并且订单 id 为 123
        int oid = 123;

        return this.restTemplate.getForObject("http://hp:17900/order/" + oid, String.class);
    }

}
