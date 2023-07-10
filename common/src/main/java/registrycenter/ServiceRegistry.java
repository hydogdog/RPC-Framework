package registrycenter;

import java.net.InetSocketAddress;

/**
 * @ClassName ServiceRegistry
 * @Description TODO
 * @Author hydogdog
 * @Date 2023/7/10 16:02
 */
public interface ServiceRegistry {

     void registry(String serviceName, InetSocketAddress inetSocketAddress);

     InetSocketAddress lookupService(String serviceName);
}
