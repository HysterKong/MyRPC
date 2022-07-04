package com.example.myrpc4.server;

public interface RPCServer {
    // 把RPCServer抽象成接口，以后的服务端实现这个接口即可
    void start(int port);

    void stop();
}
