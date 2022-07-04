package com.example.myrpc4.server;

import com.example.myrpc4.server.impl.BlogServiceImpl;
import com.example.myrpc4.server.impl.UserServiceImpl;

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

        RPCServer rpcServer = new NettyRPCServer(serviceProvider);
        rpcServer.start(8899);
    }
}
