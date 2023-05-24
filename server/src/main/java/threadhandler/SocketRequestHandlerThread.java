package threadhandler;

import dto.RpcRequest;
import dto.RpcResponse;
import lombok.extern.slf4j.Slf4j;

import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.lang.reflect.Method;
import java.net.Socket;

/**
 * @ClassName SocketRequestHandlerThread
 * @Description TODO
 * @Author hydogdog
 * @Date 2023/5/23 17:06
 */
@Slf4j
public class SocketRequestHandlerThread implements Runnable {
    private final Socket accept;
    private final Object service;

    public SocketRequestHandlerThread(Socket accept, Object service) {
        this.accept = accept;
        this.service = service;
    }

    @Override
    public void run() {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(accept.getInputStream());
            ObjectOutputStream objectOutputStream = new ObjectOutputStream(accept.getOutputStream())){
            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();
            Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());
            Object returnObject = method.invoke(service, rpcRequest.getParameters());
            objectOutputStream.writeObject(RpcResponse.success(returnObject));
            objectOutputStream.flush();

        }catch (Exception e){
            log.error("调用或发送时有错误发生：", e);
        }
    }
}
