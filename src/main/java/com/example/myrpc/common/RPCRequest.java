package com.example.myrpc.common;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class RPCRequest implements Serializable {
    //服务类名，客户端只知道该接口的名字，调用后由实际实现类处理
    private String interfaceName;
    //服务类的方法名
    private String methodName;
    //参数列表
    private Object[] params;
    //参数类型
    private Class<?>[] paramTypes;
}
