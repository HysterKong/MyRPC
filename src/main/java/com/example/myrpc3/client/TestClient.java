package com.example.myrpc3.client;

import com.example.myrpc3.pojo.Blog;
import com.example.myrpc3.pojo.User;
import com.example.myrpc3.server.BlogService;
import com.example.myrpc3.server.UserService;

public class TestClient {

    public static void main(String[] args) {
//        // 构建一个使用java Socket传输的客户端
//        SimpleRPCClient simpleRPCClient = new SimpleRPCClient("127.0.0.1", 8899);
        RPCClient rpcClient = new NettyRPCClient("127.0.0.1", 8899);
        // 把这个客户端传入代理客户端
        ClientProxy rpcClientProxy = new ClientProxy(rpcClient);
        // 代理客户端根据不同的服务，获得一个代理类， 并且这个代理类的方法以或者增强（封装数据，发送请求）
        UserService userService = rpcClientProxy.getProxy(UserService.class);
        // 调用方法1
        User userByUserId = userService.getUserByUserId(10);
        System.out.println("从服务端得到的user为：" + userByUserId);
        // 调用方法2
        User user = User.builder().userName("张三").id(100).sex(true).build();
        Integer integer = userService.inserUser(user);
        System.out.println("向服务端插入数据："+integer);

        //代理类
        BlogService blogService = rpcClientProxy.getProxy(BlogService.class);
        //调用方法3
        Blog blogById = blogService.getBlogById(10000);
        System.out.println("从服务端得到的blog为：" + blogById);
    }
}
