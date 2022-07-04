package com.example.myrpc2.client;

import com.example.myrpc2.pojo.Blog;
import com.example.myrpc2.pojo.User;
import com.example.myrpc2.server.BlogService;
import com.example.myrpc2.server.UserService;

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
        // 客户中添加新的测试用例
        BlogService blogService = clientProxy.getProxy(BlogService.class);
        Blog blogById = blogService.getBlogById(10000);
        System.out.println("从服务端得到的blog为：" + blogById);
    }
}
