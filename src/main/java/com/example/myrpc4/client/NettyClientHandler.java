package com.example.myrpc4.client;

import com.example.myrpc4.common.RPCResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.AttributeKey;


public class NettyClientHandler extends SimpleChannelInboundHandler<RPCResponse> {
    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RPCResponse rpcResponse) throws Exception {
        // 接收到response, 给channel设计别名，让sendRequest里读取response
        AttributeKey<RPCResponse> key = AttributeKey.valueOf("RPCResponse");
        ctx.channel().attr(key).set(rpcResponse);
        ctx.channel().close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
