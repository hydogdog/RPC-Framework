package server;

/**
 * @author hydogdog
 */
public interface RpcServer {

    /**
     * RPC服务端启动
     */
    void start ();

    <T> void publishService(Object service, Class<T> serviceClass);

}
