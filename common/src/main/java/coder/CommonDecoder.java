package coder;

import dto.RpcRequest;
import dto.RpcResponse;
import enmu.PackageType;
import exception.RpcError;
import exception.RpcException;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import io.netty.handler.codec.ReplayingDecoder;
import lombok.extern.slf4j.Slf4j;
import serializer.CommonSerializer;

import java.util.List;

/**
 * @ClassName CommonDecoder
 * @Description TODO
 * @Author hydogdog
 * @Date 2023/6/25 17:17
 */
@Slf4j
public class CommonDecoder extends ByteToMessageDecoder {

    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int magic = in.readInt();

        if(magic != MAGIC_NUMBER){
            log.error("不识别的协议包: {}", magic);
            throw new RpcException(RpcError.UNKNOWN_PROTOCOL);
        }

        int packageCode = in.readInt();
        Class<?> packageClass;
        if(PackageType.REQUEST_PACK.getCode() == packageCode) {
            packageClass = RpcRequest.class;
        }else if(PackageType.RESPONSE_PACK.getCode() == packageCode) {
            packageClass = RpcResponse.class;
        }else {
            log.error("不识别的数据包: {}", packageCode);
            throw new RpcException(RpcError.UNKNOWN_PACKAGE_TYPE);
        }

        int serializerCode = in.readInt();
        CommonSerializer serializer = CommonSerializer.getByCode(serializerCode);

        int length = in.readInt();
        byte[] bytes = new byte[length];
        in.readBytes(bytes);
        Object obj = serializer.deserialize(bytes, packageClass);
        out.add(obj);

    }
}
