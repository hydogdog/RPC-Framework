package client;


import coder.CommonDecoder;
import coder.CommonEncoder;
import dto.RpcRequest;
import dto.RpcResponse;
import handler.NettyClientHandler;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.AttributeKey;
import lombok.extern.slf4j.Slf4j;
import registrycenter.ServiceRegistry;
import registrycenter.impl.NacosServiceRegistry;
import serializer.HessianSerializer;
import serializer.JsonSerializer;
import serializer.KryoSerializer;

import java.net.InetSocketAddress;
import java.util.Objects;


/**
 * @author hydogdog
 * @author hydogdog
 */
@Slf4j
public class NettyClient implements RpcClient{
    private int PORT;

    private String HOST;

    private static final Bootstrap bootstrap;

    private ServiceRegistry serviceRegistry;

    public NettyClient() {
        serviceRegistry = new NacosServiceRegistry();
    }

    static {
        NioEventLoopGroup group = new NioEventLoopGroup();
        bootstrap = new Bootstrap();
        bootstrap.group(group)
                .channel(NioSocketChannel.class)
                .option(ChannelOption.SO_KEEPALIVE,true)
                .handler(new ChannelInitializer<SocketChannel>() {

                    @Override
                    protected void initChannel(SocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        //TODO 抽取Serializer字段，实现使用Set方法配置
                        pipeline.addLast(new CommonEncoder(new HessianSerializer()));
                        pipeline.addLast(new CommonDecoder());

                        pipeline.addLast(new NettyClientHandler());
                    }
                });
    }

    @Override
    public Object sendRequest(RpcRequest rpcRequest) {
        try {
            InetSocketAddress inetSocketAddress = serviceRegistry.lookupService(rpcRequest.getInterfaceName());
            HOST = inetSocketAddress.getHostName();
            PORT = inetSocketAddress.getPort();
            ChannelFuture future = bootstrap.connect(HOST, PORT).sync();
            Channel channel = future.channel();
            if(channel != null){
                channel.writeAndFlush(rpcRequest).addListener(listener -> {
                    if(listener.isSuccess()){
                        log.info("客户端发送消息:{}",rpcRequest.toString());
                    }else{
                        log.error("客户端发送消息时发生错误:",listener.cause());
                    }
                });
            }
            channel.closeFuture().sync();
            AttributeKey<RpcResponse> key = AttributeKey.valueOf("rpcResponse");
            RpcResponse rpcResponse = channel.attr(key).get();
            return rpcResponse;
        } catch (InterruptedException e) {
            log.error("客户端发送消息时发生错误:",e);
        }
        return null;
    }
}
