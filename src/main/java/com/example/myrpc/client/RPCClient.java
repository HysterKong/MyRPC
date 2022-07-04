package com.example.myrpc.client;

import com.example.myrpc.pojo.User;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Random;

public class RPCClient {
    public static void main(String[] args) {
        try {
            //建立Socket连接
            Socket socket = new Socket("127.0.0.1", 8899);
            ObjectOutputStream outputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream inputStream = new ObjectInputStream(socket.getInputStream());
            //传给服务器ID
            outputStream.writeInt(new Random().nextInt());
            outputStream.flush();
            //服务器查询数据，返回User
            User user = (User) inputStream.readObject();
            System.out.println("服务端返回的User" + user);

        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("客户端启动失败");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
            System.out.println("客户端获取信息失败");
        }
    }
}
