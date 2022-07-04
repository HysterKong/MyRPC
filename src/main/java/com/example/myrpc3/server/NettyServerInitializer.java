package com.example.myrpc3.server;

import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.serialization.ClassResolver;
import io.netty.handler.codec.serialization.ObjectDecoder;
import io.netty.handler.codec.serialization.ObjectEncoder;
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
        // 消息格式 [长度][消息体], 解决粘包问题
        channelPipeline.addLast(new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE,
                0, 4, 0, 4));
        // 计算当前待发送消息的长度，写入到前4个字节中
        channelPipeline.addLast(new LengthFieldPrepender(4));

        channelPipeline.addLast(new ObjectEncoder());
        channelPipeline.addLast(new ObjectDecoder(new ClassResolver() {
            @Override
            public Class<?> resolve(String s) throws ClassNotFoundException {
                return Class.forName(s);
            }
        }));

        channelPipeline.addLast(new NettyRPCServerHandler(serviceProvider));
    }
}
