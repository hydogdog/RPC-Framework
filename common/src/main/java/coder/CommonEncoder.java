package coder;

import dto.RpcRequest;
import enmu.PackageType;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;
import serializer.CommonSerializer;

/**
 * @ClassName CommonEncoder
 * @Description TODO
 * @Author hydogdog
 * @Date 2023/6/25 17:17
 */
public class CommonEncoder extends MessageToByteEncoder {

    private static final int MAGIC_NUMBER = 0xCAFEBABE;

    private final CommonSerializer serializer;

    public CommonEncoder(CommonSerializer serializer) {
        this.serializer = serializer;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        //写入魔数
        out.writeInt(MAGIC_NUMBER);
        //写入包类型
        if (msg instanceof RpcRequest) {
            out.writeInt(PackageType.REQUEST_PACK.getCode());
        } else {
            out.writeInt(PackageType.RESPONSE_PACK.getCode());
        }
        //写入序列化器类型
        out.writeInt(serializer.getCode());

        byte[] bytes = serializer.serialize(msg);
        //写入数据长度
        out.writeInt(bytes.length);

        //最后写入数据
        out.writeBytes(bytes);
    }
}
