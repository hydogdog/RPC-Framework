package threadhandler;

import container.DefaultServiceContainer;
import container.ServiceContainer;
import dto.RpcRequest;
import dto.RpcResponse;
import io.netty.channel.*;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.concurrent.EventExecutorGroup;
import lombok.extern.slf4j.Slf4j;

/**
 * @ClassName NettyServerHandler
 * @Description 服务端事件处理器
 * @Author hydogdog
 * @Date 2023/6/26 14:52
 */
@Slf4j
public class NettyServerHandler extends SimpleChannelInboundHandler<RpcRequest> {

    private static final RequestHandler requestHandler;

    private static final ServiceContainer serviceContainer;

    static {
        requestHandler = new RequestHandler();
        serviceContainer = new DefaultServiceContainer();
    }


    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RpcRequest msg) throws Exception {
        try {
            log.info("服务器接收到请求: {}", msg);
            Object service = serviceContainer.getService(msg.getInterfaceName());
            Object result = requestHandler.handle(msg, service);
            ChannelFuture future = ctx.writeAndFlush(RpcResponse.success(result));
            future.addListener(ChannelFutureListener.CLOSE);
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        log.error("处理调用过程中有错误发生:");
        cause.printStackTrace();
        ctx.close();
    }
}
