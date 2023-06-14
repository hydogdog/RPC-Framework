package threadhandler;

import dto.RpcRequest;
import dto.RpcResponse;
import enmu.ResponseCode;
import lombok.extern.slf4j.Slf4j;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;


/**
 * @ClassName RequestHandler
 * @Description 方法调用器
 * @Author hydogdog
 * @Date 2023/6/14 15:54
 */
@Slf4j
public class RequestHandler {
    public Object handle(RpcRequest rpcRequest, Object service) {
        Object result = null;
        try {
            result = invokeTargetMethod(rpcRequest, service);
            log.info("服务:{} 成功调用方法:{}", rpcRequest.getInterfaceName(), rpcRequest.getMethodName());
        }catch (NoSuchMethodException e) {
            return RpcResponse.fail(ResponseCode.METHOD_NOT_FOUND);
        }catch (InvocationTargetException | IllegalAccessException e) {
             log.error("调用或发送时有错误发生：", e);
        }

        return result;
    }

    private Object invokeTargetMethod(RpcRequest rpcRequest, Object service) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {


        Method method = service.getClass().getMethod(rpcRequest.getMethodName(), rpcRequest.getParamTypes());


        return method.invoke(service, rpcRequest.getParameters());

    }
}
