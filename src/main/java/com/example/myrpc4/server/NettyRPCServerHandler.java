package com.example.myrpc4.server;

import com.example.myrpc4.common.RPCRequest;
import com.example.myrpc4.common.RPCResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@AllArgsConstructor
public class NettyRPCServerHandler extends SimpleChannelInboundHandler {
    private ServiceProvider serviceProvider;

    @Override
    protected void channelRead0(ChannelHandlerContext channelHandlerContext, Object o) throws Exception {
        RPCResponse response = getResponse(o);
        channelHandlerContext.writeAndFlush(response);
        channelHandlerContext.close();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }

    private RPCResponse getResponse(Object o) {
        RPCRequest request = (RPCRequest) o;
        String interfaceName = request.getInterfaceName();
        Object service = serviceProvider.getService(interfaceName);
        try {
            Method method = service.getClass().getMethod(request.getMethodName(), request.getParamTypes());
            Object invoke = method.invoke(service, request.getParams());
            return RPCResponse.success(invoke);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException e) {
            e.printStackTrace();
            System.out.println("方法执行错误");
            return RPCResponse.fail();
        }

    }
}
