package com.example.myrpc2.server;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class ThreadPoolRPCServer implements RPCServer {
    private ServiceProvider serviceProvider;
    private final ThreadPoolExecutor threadPoolExecutor;

    public ThreadPoolRPCServer(ServiceProvider serviceProvider) {
        this.serviceProvider = serviceProvider;
        threadPoolExecutor = new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors(),
                1000, 60,
                TimeUnit.SECONDS, new ArrayBlockingQueue<>(100));
    }

    public ThreadPoolRPCServer(ServiceProvider serviceProvider, int corePoolSize,
                               int maximumPoolSize, long keepAliveTime,
                               TimeUnit timeUnit, BlockingQueue<Runnable> workQueue) {
        this.serviceProvider = serviceProvider;
        threadPoolExecutor = new ThreadPoolExecutor(corePoolSize, maximumPoolSize,
                keepAliveTime, timeUnit, workQueue);
    }

    @Override
    public void start(int port) {
        System.out.println("服务器启动了");
        try {
            ServerSocket serverSocket = new ServerSocket(port);
            while (true) {
                Socket socket = serverSocket.accept();
                Runnable runnable = new WorkThread(socket, serviceProvider);
                threadPoolExecutor.execute(runnable);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void stop() {
    }
}
