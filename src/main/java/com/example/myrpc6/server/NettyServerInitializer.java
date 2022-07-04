package com.example.myrpc6.server;

import com.example.myrpc6.codec.JsonSerializer;
import com.example.myrpc6.codec.MyDecoder;
import com.example.myrpc6.codec.MyEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import lombok.AllArgsConstructor;

/**
 * 初始化，主要负责序列化的编码解码， 需要解决netty的粘包问题
 */
@AllArgsConstructor
public class NettyServerInitializer extends ChannelInitializer<SocketChannel> {
    private ServiceProvider serviceProvider;

    @Override
    protected void initChannel(SocketChannel socketChannel) throws Exception {
        ChannelPipeline channelPipeline = socketChannel.pipeline();

        // 使用自定义的编解码器
        channelPipeline.addLast(new MyDecoder());
        // 编码需要传入序列化器，这里是json，还支持ObjectSerializer，也可以自己实现其他的
        channelPipeline.addLast(new MyEncoder(new JsonSerializer()));

        channelPipeline.addLast(new NettyRPCServerHandler(serviceProvider));
    }
}
