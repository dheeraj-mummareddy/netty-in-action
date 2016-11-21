package com.tech.radar.echo.server

import com.tech.radar.utils.SocketPortUtils
import io.netty.bootstrap.Bootstrap
import io.netty.bootstrap.ServerBootstrap
import io.netty.buffer.ByteBuf
import io.netty.buffer.Unpooled
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelInitializer
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.channel.socket.nio.NioSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import spock.lang.Shared
import spock.lang.Specification

import java.nio.charset.StandardCharsets

/**
 * Created by dmumma on 11/5/16.
 */
class EchoServerSpec extends Specification {

    @Shared
    EventLoopGroup bossGroup

    @Shared
    EventLoopGroup workerGroup

    @Shared
    ChannelFuture serverChannelFuture

    @Shared
    ChannelFuture clientChannelFuture

    @Shared
    int currentPort

    def setupSpec() {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap =
                new ServerBootstrap()
                        .group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                    @Override
                    public void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast("EchoServerHandler", new EchoServerHandler());
                    }
                })
                        .handler(new LoggingHandler(LogLevel.INFO))

        currentPort = SocketPortUtils.nextFreePort(9000, 9100)
        serverChannelFuture = serverBootstrap.bind(currentPort).sync();

        Bootstrap clientBootstrap = new Bootstrap();
        clientBootstrap
                .group(workerGroup)
                .channel(NioSocketChannel.class)
                .handler(new ChannelInitializer<SocketChannel>() {
            @Override
            protected void initChannel(SocketChannel ch) throws Exception {
                ch.pipeline().addLast(new EchoServerHandler());
            }
        });

        clientChannelFuture = clientBootstrap.connect(getHostName(), currentPort).sync()
    }

    def cleanupSpec() {
        clientChannelFuture.channel().close()
        serverChannelFuture.channel().close()
        bossGroup.shutdownGracefully()
        workerGroup.shutdownGracefully()
    }

    def setup() {


    }

    def cleanup() {

    }

    def "canary"() {
        expect:
        true
    }

    def "given a message to EchoServer, expect an echo"() {
        given: "a clientChannelFuture"
        ChannelFuture clientChannelFuture = clientChannelFuture

        when: "we write a message to the channel"
        def writeFuture = clientChannelFuture.channel().writeAndFlush(message())

        then: "await"
        def awaitFuture = writeFuture.await()
        awaitFuture == writeFuture
    }

    private String getHostName() {
        return InetAddress.getLocalHost().getHostName();
    }

    private static ByteBuf message() {
        return Unpooled.wrappedBuffer("ping".getBytes(StandardCharsets.US_ASCII));
    }
}
