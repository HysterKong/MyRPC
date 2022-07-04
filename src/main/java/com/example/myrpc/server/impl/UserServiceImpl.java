package com.example.myrpc.server.impl;

import com.example.myrpc.pojo.User;
import com.example.myrpc.server.UserService;

import java.util.Random;
import java.util.UUID;

public class UserServiceImpl implements UserService {
    @Override
    public User getUserByUserId(Integer id) {
        System.out.println("客户端查询了ID为" + id + "的用户");
        //模拟从数据库中取数据的操作，随机产生一个user
        Random random = new Random();
        User user = User.builder()
                .userName(UUID.randomUUID().toString())
                .id(id)
                .sex(random.nextBoolean()).build();
        return user;
    }
}
