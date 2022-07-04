package com.example.myrpc6.client;

import com.example.myrpc6.common.RPCRequest;
import com.example.myrpc6.common.RPCResponse;
import lombok.AllArgsConstructor;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;

@AllArgsConstructor
public class ClientProxy implements InvocationHandler {
    private RPCClient client;

    // jdk 动态代理，每一次代理对象调用方法，会经过此方法增强（反射获取request对象，socket发送至客户端）
    @Override
    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
        //使用lombok中的builder构建request
        RPCRequest request = RPCRequest.builder().interfaceName(method.getDeclaringClass().getName())
                .methodName(method.getName())
                .params(args).paramTypes(method.getParameterTypes()).build();
        //数据传输
        RPCResponse response = client.sendRequest(request);
        return response.getData();
    }

    <T>T getProxy(Class<T> clazz) {
        Object o = Proxy.newProxyInstance(clazz.getClassLoader(), new Class[]{clazz}, this);
        return (T) o;
    }
}
