package container;

import exception.RpcError;
import lombok.extern.slf4j.Slf4j;

import java.util.Map;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Author hydogdog
 * @Date 2023/6/14 15:26
 */
@Slf4j
public class DefaultServiceContainer implements ServiceContainer{
    /**
     * 用于存储已注册的服务
     */
    private static final Set<String> registeredService = ConcurrentHashMap.newKeySet();
    /**
     * 用于存储服务的容器
     */
    private static final Map<String,Object> serviceMap = new ConcurrentHashMap<>();

    @Override
    public <T> void registry(T service) {
        String serviceName = service.getClass().getCanonicalName();
        if(registeredService.contains(serviceName)){
            return;
        }
        registeredService.add(serviceName);
        Class<?>[] interfaces = service.getClass().getInterfaces();
        if(interfaces.length == 0){
            log.error(RpcError.SERVICE_NOT_IMPLEMENT_ANY_INTERFACE.getMessage());
        }

        for (Class<?> anInterface : interfaces) {
            serviceMap.put(anInterface.getCanonicalName(),service);
        }
        log.info("向接口:{} 注册服务 {}",interfaces,serviceName);
    }

    @Override
    public Object getService(String serviceName) {
        Object service = serviceMap.get(serviceName);
        if(Objects.isNull(service)){
            log.error(RpcError.SERVICE_NOT_FOUND.getMessage());
        }

        log.info("找到已注册服务...{}",service);
        return service;
    }
}
