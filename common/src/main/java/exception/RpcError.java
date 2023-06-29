package exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum RpcError {


    SERVICE_NOT_IMPLEMENT_ANY_INTERFACE("注册的服务未实现接口"),

    SERVICE_NOT_FOUND("找不到对应的服务"),
    UNKNOWN_PROTOCOL("不识别的协议包"),
    UNKNOWN_PACKAGE_TYPE("不识别的数据包类型");

   private final String message;
}
