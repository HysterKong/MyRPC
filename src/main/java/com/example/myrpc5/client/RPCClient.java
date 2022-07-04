package com.example.myrpc5.client;

import com.example.myrpc5.common.RPCRequest;
import com.example.myrpc5.common.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest rpcRequest);
}
