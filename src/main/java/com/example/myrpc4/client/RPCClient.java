package com.example.myrpc4.client;

import com.example.myrpc4.common.RPCRequest;
import com.example.myrpc4.common.RPCResponse;

public interface RPCClient {
    RPCResponse sendRequest(RPCRequest rpcRequest);
}
