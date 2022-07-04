package com.example.myrpc5.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

/**
 * 这个实现类代表着java原始的BIO监听模式，来一个任务，就new一个线程去处理
 * 处理任务的工作见WorkThread中
 */

public class SimpleRPCServer implements RPCServer {
    // 存着服务接口名-> service对象的map
    private ServiceProvider serviceProvider;

    public SimpleRPCServer(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
    }

    @Override
    public void start(int port) {
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            System.out.println("服务端启动了");
            //以BIO的方式监听socket
            while (true) {
                //等待连接
                Socket socket = serverSocket.accept();
                //开启一个新线程去处理，等待数据
                //WorkThread类从服务端代码分离出来，用单一职责原则简化服务端代码
                new Thread(new WorkThread(socket, serviceProvider)).start();

            }
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("服务器启动失败");
        }
    }

    @Override
    public void stop() {
    }
}
