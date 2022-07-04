package com.example.myrpc2.server;

import com.example.myrpc2.server.impl.BlogServiceImpl;
import com.example.myrpc2.server.impl.UserServiceImpl;

import java.util.HashMap;
import java.util.Map;

public class TestServer {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();
//        Map<String, Object> serviceProvider = new HashMap<>();
        //通过Map中的key-value来暴露两个服务接口
//        serviceProvider.put("com.example.myrpc2.server.UserService", userService);
//        serviceProvider.put("com.example.myrpc2.server.BlogService", blogService);
        ServiceProvider serviceProvider = new ServiceProvider();
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        RPCServer rpcServer = new ThreadPoolRPCServer(serviceProvider);
        rpcServer.start(8899);
    }
}
