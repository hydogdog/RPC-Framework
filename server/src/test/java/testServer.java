import container.DefaultServiceContainer;
import impl.HelloServiceImpl;
import org.junit.Test;
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

}
