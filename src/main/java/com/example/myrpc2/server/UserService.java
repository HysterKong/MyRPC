package com.example.myrpc2.server;

import com.example.myrpc2.pojo.User;

public interface UserService {
    //客户端通过这个接口调用服务端的实现类
    User getUserByUserId(Integer id);
    //增加一个功能
    Integer inserUser(User user);
}
