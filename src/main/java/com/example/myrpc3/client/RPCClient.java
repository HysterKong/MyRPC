package com.example.myrpc3.client;

import com.example.myrpc3.common.RPCRequest;
import com.example.myrpc3.common.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest rpcRequest);
}
