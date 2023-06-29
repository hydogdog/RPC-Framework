package serializer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import dto.RpcRequest;
import enmu.SerializerCode;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;

/**
 * @ClassName JsonSerializer
 * @Description Json序列化器的实现
 * @Author hydogdog
 * @Date 2023/6/25 17:47
 */
@Slf4j
public class JsonSerializer implements CommonSerializer{

    private ObjectMapper objectMapper = new ObjectMapper();
    @Override
    public byte[] serialize(Object obj) {
        try {
            return objectMapper.writeValueAsBytes(obj);
        } catch (JsonProcessingException e) {
            log.error("序列化时有错发生: {}", e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public Object deserialize(byte[] bytes, Class<?> clazz) {
        try {
            Object obj = objectMapper.readValue(bytes, clazz);
            if(obj instanceof RpcRequest){
                obj = handleRequest(obj);
            }
            return obj;
        } catch (IOException e) {
            log.error("反序列化有错误发生: {}",e.getMessage());
            e.printStackTrace();
            return null;
        }
    }

    private Object handleRequest(Object obj) throws IOException{
                RpcRequest rpcRequest = (RpcRequest) obj;
                for (int i = 0; i < rpcRequest.getParamTypes().length; i++) {
                    Class<?> clazz = rpcRequest.getParamTypes()[i];
                    if(!clazz.isAssignableFrom(rpcRequest.getParameters()[i].getClass())){
                        byte[] bytes = objectMapper.writeValueAsBytes(rpcRequest.getParameters()[i]);
                        rpcRequest.getParameters()[i] = objectMapper.readValue(bytes, clazz);
                    }

                }
                return rpcRequest;

    }

    @Override
    public int getCode() {
        return SerializerCode.valueOf("JSON").getCode();
    }
}
