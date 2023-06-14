package threadhandler;

import container.ServiceContainer;
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
    /**
     * 服务Socket
     */
    private  Socket accept;
    /**
     * 服务容器
     */
    private ServiceContainer serviceContainer;
    /**
     * 请求处理器
     */
    private RequestHandler requestHandler;

    public SocketRequestHandlerThread(Socket accept, ServiceContainer serviceContainer, RequestHandler requestHandler) {
        this.accept = accept;
        this.serviceContainer = serviceContainer;
        this.requestHandler = requestHandler;
    }

    @Override
    public void run() {
        try(ObjectInputStream objectInputStream = new ObjectInputStream(accept.getInputStream());

            ObjectOutputStream objectOutputStream = new ObjectOutputStream(accept.getOutputStream())){

            RpcRequest rpcRequest = (RpcRequest) objectInputStream.readObject();

            String interfaceName = rpcRequest.getInterfaceName();

            Object service = serviceContainer.getService(interfaceName);

            Object result = requestHandler.handle(rpcRequest,service);

            objectOutputStream.writeObject(RpcResponse.success(result));

            objectOutputStream.flush();

        }catch (Exception e){
            log.error("调用或发送时有错误发生：", e);
        }
    }
}
