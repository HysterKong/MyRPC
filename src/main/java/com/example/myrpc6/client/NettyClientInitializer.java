package com.example.myrpc6.client;

import com.example.myrpc6.codec.JsonSerializer;
import com.example.myrpc6.codec.MyDecoder;
import com.example.myrpc6.codec.MyEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;

public class NettyClientInitializer extends ChannelInitializer<SocketChannel> {

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline pipeline = socketChannel.pipeline();

        //使用自定义的编解码器
        pipeline.addLast(new MyDecoder());
        pipeline.addLast(new MyEncoder(new JsonSerializer()));

        pipeline.addLast(new NettyClientHandler());
    }
}
