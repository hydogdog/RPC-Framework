package client;

import dto.RpcRequest;


public interface RpcClient {
    Object sendRequest(RpcRequest rpcRequest);
}
