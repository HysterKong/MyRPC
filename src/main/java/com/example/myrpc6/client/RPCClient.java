package com.example.myrpc6.client;

import com.example.myrpc6.common.RPCRequest;
import com.example.myrpc6.common.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest rpcRequest);
}
