package server;

import lombok.extern.slf4j.Slf4j;
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
    private static final String threadNamePrefix = "socket-server-rpc-pool";

    private final ExecutorService threadPool;

    public SocketServer() {
        threadPool = ThreadPoolFactory.createDefaultThreadPool(threadNamePrefix);
    }

    public void register(int port, Object service){
        try(ServerSocket server = new ServerSocket(port)){
            log.info("服务器正在启动...");
            Socket socket;
            while((socket = server.accept()) != null){
                log.info("消费者连接: {}:{}", socket.getInetAddress(), socket.getPort());
                threadPool.execute(new SocketRequestHandlerThread(socket,service));
            }
        }catch (IOException e){

        }
    }
}
