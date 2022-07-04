package com.example.myrpc6.client;

import com.example.myrpc6.common.RPCRequest;
import com.example.myrpc6.common.RPCResponse;
import com.example.myrpc6.register.ServiceRegister;
import com.example.myrpc6.register.zkServiceRegister;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;

import java.net.InetSocketAddress;

public class NettyRPCClient implements RPCClient {
    private static final Bootstrap bootstarp;
    private static final EventLoopGroup eventLoopGroup;

    static {
        eventLoopGroup = new NioEventLoopGroup();
        bootstarp = new Bootstrap();
        bootstarp.group(eventLoopGroup).channel(NioSocketChannel.class)
                .handler(new NettyClientInitializer());
    }

    private String host;
    private int port;
    private ServiceRegister serviceRegister;


    public NettyRPCClient() {
        this.serviceRegister = new zkServiceRegister();
    }

    /**
     * 这里需要操作一下，因为netty的传输都是异步的，你发送request，会立刻返回， 而不是想要的相应的response
     */
    @Override
    public RPCResponse sendRequest(RPCRequest rpcRequest) {
        InetSocketAddress serverAdress = serviceRegister.serviceDiscovery(rpcRequest.getInterfaceName());
        host = serverAdress.getHostName();
        port = serverAdress.getPort();
        try {
            ChannelFuture channelFuture = bootstarp.connect(host, port).sync();
            Channel channel = channelFuture.channel();
            //发送数据
            channel.writeAndFlush(rpcRequest);
            channel.closeFuture().sync();
            // 阻塞的获得结果，通过给channel设计别名，获取特定名字下的channel中的内容（这个在handler中设置）
            // AttributeKey是，线程隔离的，不会由线程安全问题。
            // 实际上不应通过阻塞，可通过回调函数
            AttributeKey<RPCResponse> key = AttributeKey.valueOf("RPCResponse");
            RPCResponse response = channel.attr(key).get();
            System.out.println(response);
            return response;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
