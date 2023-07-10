import api.entity.HelloService;
import container.DefaultServiceContainer;
import impl.HelloServiceImpl;
import org.junit.Test;
import server.NettyServer;
import server.SocketServer;

/**
 * @ClassName testServer
 * @Description TODO
 * @author hydogdog
 * @Date 2023/5/23 17:36
 */
public class testServer {
    @Test
    public void testSocketServer() throws Exception {
        DefaultServiceContainer container = new DefaultServiceContainer();
        HelloServiceImpl helloService = new HelloServiceImpl();
        container.registry(helloService);
        SocketServer socketServer = new SocketServer(container);
        socketServer.register(9999, new HelloServiceImpl());
    }

    @Test
    public void testNettyServer(){
        HelloServiceImpl helloService = new HelloServiceImpl();
        NettyServer nettyServer = new NettyServer("127.0.0.1",9999);
        nettyServer.publishService(helloService, HelloService.class);
    }

}
