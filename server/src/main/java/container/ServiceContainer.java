package container;

/**
 * @author hydogdog
 * @Description 用于存储服务的容器
 */
public interface ServiceContainer {
    <T> void registry(T service);

    Object getService(String serviceName);
}
