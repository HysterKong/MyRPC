package com.example.myrpc1.client;

import com.example.myrpc1.pojo.User;
import com.example.myrpc1.server.UserService;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.Random;

public class RPCClient {
    public static void main(String[] args) {
        ClientProxy clientProxy = new ClientProxy("127.0.0.1", 8899);
        UserService proxy = clientProxy.getProxy(UserService.class);

        //调用服务端方法1
        User getUserById = proxy.getUserByUserId(10);
        System.out.println("从服务端得到的User为：" + getUserById);
        //调用服务端方法2
        User user = User.builder().userName("张三").sex(true).id(100).build();
        Integer result = proxy.inserUser(user);
        System.out.println("向服务端插入数据：" + result);
    }
}
