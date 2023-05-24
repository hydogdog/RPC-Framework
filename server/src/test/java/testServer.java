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
        SocketServer socketServer = new SocketServer();
        socketServer.register(9999, new HelloServiceImpl());
    }

}
