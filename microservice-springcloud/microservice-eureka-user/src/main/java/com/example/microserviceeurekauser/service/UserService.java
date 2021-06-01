package com.example.microserviceeurekauser.service;

import com.example.microserviceeurekauser.po.User;
import java.util.List;

/*
 * 代码解释：
 *     编写 Service 接口，接口中方法不用做具体实现。具体实现将有 ServiceImpl 类来完成。
 *
 *     这里不做演示，只作为提示。
 *
 * */
public interface UserService {
    // 查询所有用户
    List<User> getAllUsers();

}
