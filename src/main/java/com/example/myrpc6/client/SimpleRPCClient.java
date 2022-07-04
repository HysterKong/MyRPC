package com.example.myrpc6.client;

import com.example.myrpc6.common.RPCRequest;
import com.example.myrpc6.common.RPCResponse;
import com.example.myrpc6.register.ServiceRegister;
import com.example.myrpc6.register.zkServiceRegister;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.InetSocketAddress;
import java.net.Socket;

public class SimpleRPCClient implements RPCClient {
    private String host;
    private int port;
    private ServiceRegister serviceRegister;

    public SimpleRPCClient() {
        // 初始化注册中心，建立连接
        this.serviceRegister = new zkServiceRegister();
    }

    @Override
    // BIO形式
    // 客户端发起一次请求调用，Socket建立连接，发起请求Request，得到响应Response
    // 这里的request是封装好的，不同的service需要进行不同的封装， 客户端只知道Service接口，需要一层动态代理根据反射封装不同的Service
    public RPCResponse sendRequest(RPCRequest rpcRequest) {
        // 从注册中心获取host，port
        InetSocketAddress serverAdress = serviceRegister.serviceDiscovery(rpcRequest.getInterfaceName());
        host = serverAdress.getHostName();
        port = serverAdress.getPort();

        try {
            Socket socket = new Socket(host, port);
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(socket.getOutputStream());
            ObjectInputStream objectInputStream = new ObjectInputStream(socket.getInputStream());

            System.out.println(rpcRequest);
            objectOutputStream.writeObject(rpcRequest);
            objectOutputStream.flush();

            RPCResponse response = (RPCResponse) objectInputStream.readObject();
            return response;
        } catch (IOException | ClassNotFoundException e) {
            e.printStackTrace();
            return RPCResponse.fail();
        }
    }
}
