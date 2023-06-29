package server;

import coder.CommonDecoder;
import coder.CommonEncoder;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;
import serializer.JsonSerializer;
import threadhandler.NettyServerHandler;

/**
 * @ClassName NettyServer
 * @Description Netty客户端服务
 * @Author hydogdog
 * @Date 2023/6/25 16:49
 */
@Slf4j
public class NettyServer implements RpcServer{

    private int PORT;

    public NettyServer(int port) {
        this.PORT = port;
    }
    @Override
    public void start() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workGroup = new NioEventLoopGroup();

        try {
            ServerBootstrap serverBootstrap = new ServerBootstrap();

            serverBootstrap.group(bossGroup,workGroup)
                    .channel(NioServerSocketChannel.class)
                    .handler(new LoggingHandler(LogLevel.INFO))
                    .option(ChannelOption.SO_BACKLOG,256)
                    .option(ChannelOption.SO_KEEPALIVE,true)
                    .childOption(ChannelOption.TCP_NODELAY,true)
                    .childHandler(new ChannelInitializer<SocketChannel>() {

                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {
                            ChannelPipeline pipeline = ch.pipeline();
                            //编码器
                            pipeline.addLast(new CommonEncoder(new JsonSerializer()));
                            //解码器
                            pipeline.addLast(new CommonDecoder());
                            //业务处理器
                            pipeline.addLast(new NettyServerHandler());
                        }
                    });
            ChannelFuture future = serverBootstrap.bind(PORT).sync();
            future.channel().closeFuture().sync();
        } catch (InterruptedException e) {
            log.error("启动服务器时发生错误:",e);
        } finally {
            bossGroup.shutdownGracefully();
            workGroup.shutdownGracefully();
        }

    }
}
