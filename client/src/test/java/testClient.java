import api.entity.HelloObject;
import api.entity.HelloService;
import client.SocketClient;
import org.junit.Test;
import proxy.RpcClientProxy;

/**
 * @ClassName testClient
 * @Description TODO
 * @Author hydogdog
 * @Date 2023/5/23 17:44
 */
public class testClient {
    @Test
    public void testSocketClient(){
        SocketClient socketClient = new SocketClient();
        RpcClientProxy rpcClientProxy = new RpcClientProxy(socketClient);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "this is a message");
        String res = helloService.hello(object);
        System.out.println(res);

    }
}
