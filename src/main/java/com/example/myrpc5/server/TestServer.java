package com.example.myrpc5.server;

import com.example.myrpc5.server.impl.BlogServiceImpl;
import com.example.myrpc5.server.impl.UserServiceImpl;

public class TestServer {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        BlogService blogService = new BlogServiceImpl();

        // 这里重用了服务暴露类，顺便在注册中心注册，实际上应分开，每个类做各自独立的事
        ServiceProvider serviceProvider = new ServiceProvider("127.0.0.1", 8899);
        serviceProvider.provideServiceInterface(userService);
        serviceProvider.provideServiceInterface(blogService);

        RPCServer rpcServer = new NettyRPCServer(serviceProvider);
        rpcServer.start(8899);
    }
}
