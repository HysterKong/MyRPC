package com.example.myrpc1.server;

import com.example.myrpc1.pojo.User;
import com.example.myrpc1.common.RPCRequest;
import com.example.myrpc1.common.RPCResponse;
import com.example.myrpc1.server.impl.UserServiceImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.ServerSocket;
import java.net.Socket;

public class RPCServer {
    public static void main(String[] args) {
        //服务端解析 Request 并发送 Response
        UserService userService = new UserServiceImpl();
        try {
            ServerSocket serverSocket = new ServerSocket(8899);
            System.out.println("服务器启动了");
            //以BIO的方式监听Socket
            while (true) {
                //阻塞地等待连接
                Socket socket = serverSocket.accept();
                //开启一个线程处理,多线程可以应对多个连接
                new Thread(()->{
                    try {
                        //阻塞地等待数据
                        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                        //读取客户端传过来的request
                        RPCRequest request = (RPCRequest) inputStream.readObject();
                        //需要反射调用对应的方法
                        Method method = userService.getClass().getMethod(request.getMethodName(), request.getParamTypes());
                        Object invoke = method.invoke(userService, request.getParams());
                        //封装写入response对象
                        outputStream.writeObject(RPCResponse.success(invoke));
                        outputStream.flush();
                    } catch (IOException | ClassNotFoundException | NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
                        e.printStackTrace();
                        System.out.println("服务端读取错误");
                    }
                }).start();
            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器启动失败");
        }

    }
}
