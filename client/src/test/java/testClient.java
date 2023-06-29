import api.entity.HelloObject;
import api.entity.HelloService;
import client.NettyClient;
import client.SocketClient;
import org.junit.Test;
import proxy.RpcClientProxy;

/**
 * @ClassName testClient
 * @Description 测试基于Socket连接的客户端
 * @Author hydogdog
 * @Date 2023/5/23 17:44
 */
public class testClient {
    @Test
    public void testSocketClient(){
        SocketClient socketClient = new SocketClient("127.0.0.1",9999);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(socketClient);
        HelloService helloService = rpcClientProxy.getProxy(HelloService.class);
        HelloObject object = new HelloObject(12, "this is a message");
        String res = helloService.hello(object);
        System.out.println(res);

    }

    @Test
    public void testNettyClient(){
        NettyClient nettyClient = new NettyClient("127.0.0.1", 9999);
        RpcClientProxy rpcClientProxy = new RpcClientProxy(nettyClient);
        HelloService proxy = rpcClientProxy.getProxy(HelloService.class);
        String this_is_a_message = proxy.hello(new HelloObject(12, "this is a message"));
        System.out.println(this_is_a_message);

    }
}
