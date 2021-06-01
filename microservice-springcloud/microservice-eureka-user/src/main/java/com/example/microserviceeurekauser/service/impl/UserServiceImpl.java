package com.example.microserviceeurekauser.service.impl;

import com.example.microserviceeurekauser.po.User;
import com.example.microserviceeurekauser.service.UserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/*
* 代码解释：
*     编写 ServiceImpl 类，实现 Service 接口。
*
*     这里不做演示，只作为提示。
*
* */
@Service
@Transactional
public class UserServiceImpl implements UserService {
    @Override
    public List<User> getAllUsers() {
        return null;
    }

}
