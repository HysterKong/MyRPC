package com.example.myrpc.server;

import com.example.myrpc.pojo.User;
import com.example.myrpc.server.impl.UserServiceImpl;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class RPCServer {
    public static void main(String[] args) {
        UserService userService = new UserServiceImpl();
        try {
            ServerSocket serverSocket = new ServerSocket(8899);
            System.out.println("服务器启动了");
            //以BIO的方式监听Socket
            while (true) {
                Socket socket = serverSocket.accept();
                //开启一个线程处理
                new Thread(()->{
                    try {
                        ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
                        ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
                        //读取客户端传过来的ID
                        Integer id = inputStream.readInt();
                        User user = userService.getUserByUserId(id);
                        //写入User对象传回客户端
                        outputStream.writeObject(user);
                        outputStream.flush();
                    } catch (IOException e) {
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
