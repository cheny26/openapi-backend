package com.cheny.openapi.service.impl;

import com.cheny.openapi.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class UserServiceImplTest {
    @Resource
    UserService userService;

    @Test
    void userRegister() {
        long cheny = userService.userRegister("cheny", "12345678", "12345678");
        System.out.println(cheny);
    }
}