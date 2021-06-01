package com.example.microserviceeurekaorder.Controller;

import com.example.microserviceeurekaorder.po.Order;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

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
public class OrderController {
    // 通过 id 查询订单
    @GetMapping("/order/{id}")
    public String findOrderById(@PathVariable String id) {
        Order order = new Order();
        order.setId("123");
        order.setPrice("￥23.5");
        order.setReceiverName("XiaoQiang");
        order.setReceiverAddress("Beijing");
        order.setReceiverPhone("13494142411");

        return order.toString();
    }

}
