package server;

import container.ServiceContainer;
import lombok.extern.slf4j.Slf4j;
import threadhandler.RequestHandler;
import threadhandler.SocketRequestHandlerThread;
import utils.ThreadPoolFactory;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;

/**
 * @ClassName SocketServer
 * @Description TODO
 * @Author hydogdog
 * @Date 2023/5/23 16:54
 */
@Slf4j
public class SocketServer {
    /**
     * 线程名前缀
     */
    private static final String threadNamePrefix = "socket-server-rpc-pool";
    /**
     * 线程池
     */
    private final ExecutorService threadPool;
    /**
     * 服务容器
     */
    private final ServiceContainer serviceContainer;

    private final RequestHandler requestHandler;


    public SocketServer(ServiceContainer serviceContainer) {
        this.serviceContainer = serviceContainer;
        this.threadPool = ThreadPoolFactory.createDefaultThreadPool(threadNamePrefix);
        this.requestHandler = new RequestHandler();
    }

    public void register(int port, Object service){
        try(ServerSocket server = new ServerSocket(port)){
            log.info("服务器正在启动...");
            Socket socket;
            while((socket = server.accept()) != null){
                log.info("消费者连接: {}:{}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new SocketRequestHandlerThread(socket,serviceContainer,requestHandler));

            }
        }catch (IOException e){

        }
    }
}
