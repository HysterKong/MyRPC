package com.example.myrpc.server;

import com.example.myrpc.pojo.User;

public interface UserService {
    //客户端通过这个接口调用服务端的实现类
    User getUserByUserId(Integer id);
}
