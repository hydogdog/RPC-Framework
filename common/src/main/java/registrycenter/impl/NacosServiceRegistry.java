package registrycenter.impl;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingFactory;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import exception.RpcError;
import exception.RpcException;
import lombok.extern.slf4j.Slf4j;
import registrycenter.ServiceRegistry;

import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.util.List;

/**
 * @ClassName NacosServiceRegistry
 * @Description TODO
 * @Author hydogdog
 * @Date 2023/7/10 16:03
 */
@Slf4j
public class NacosServiceRegistry implements ServiceRegistry {

    private static final NamingService namingService;

//    private final InetSocketAddress inetAddress;

    private static final String NACOS_ADDR= "127.0.0.1:8845";

    static {
        try {
            namingService = NamingFactory.createNamingService(NACOS_ADDR);
        }catch (NacosException e){
            log.error("连接Nacos发生错误...");
            throw new RpcException(RpcError.FAILED_TO_CONNECT_TO_SERVICE_REGISTRY);
        }
    }

    @Override
    public void registry(String serviceName, InetSocketAddress inetSocketAddress) {
        String hostName = inetSocketAddress.getHostName();
        int port = inetSocketAddress.getPort();

        try {
            namingService.registerInstance(serviceName,hostName,port);
        } catch (NacosException e) {
            log.error("注册服务时出现错误...");
            throw new RpcException(RpcError.REGISTER_SERVICE_FAILED);
        }

    }

    @Override
    public InetSocketAddress lookupService(String serviceName) {
        try {
            List<Instance> instances = namingService.getAllInstances(serviceName);
            Instance instance = instances.get(0);
            return new InetSocketAddress(instance.getIp(), instance.getPort());
        } catch (NacosException e) {
            log.error("获取服务是有错误发生");
        }
        return null;
    }
}
