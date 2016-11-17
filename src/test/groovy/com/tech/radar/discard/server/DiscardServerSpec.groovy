package com.tech.radar.discard.server

import com.tech.radar.utils.SocketPortUtils
import io.netty.bootstrap.ServerBootstrap
import io.netty.channel.ChannelFuture
import io.netty.channel.ChannelInitializer
import io.netty.channel.EventLoopGroup
import io.netty.channel.nio.NioEventLoopGroup
import io.netty.channel.socket.SocketChannel
import io.netty.channel.socket.nio.NioServerSocketChannel
import io.netty.handler.logging.LogLevel
import io.netty.handler.logging.LoggingHandler
import spock.lang.Shared
import spock.lang.Specification

/**
 * Created by dmumma on 11/5/16.
 */
class DiscardServerSpec extends Specification {

    @Shared
    EventLoopGroup bossGroup

    @Shared
    EventLoopGroup workerGroup

    @Shared
    ChannelFuture channelFuture

    @Shared
    int currentPort

    def setupSpec() {
        bossGroup = new NioEventLoopGroup(1);
        workerGroup = new NioEventLoopGroup();
        ServerBootstrap serverBootstrap =
                new ServerBootstrap()
                        .group(bossGroup, workerGroup)
                        .channel(NioServerSocketChannel.class)
                        .handler(new LoggingHandler(LogLevel.INFO))
                        .childHandler(new ChannelInitializer<SocketChannel>() {
                    protected void initChannel(SocketChannel socketChannel) throws Exception {
                        socketChannel.pipeline().addLast(new DiscardServerHandler());
                    }
                })
        currentPort = SocketPortUtils.nextFreePort(9000, 9100)
        channelFuture = serverBootstrap.bind(currentPort).sync();
    }

    def cleanupSpec() {
        channelFuture.channel().close()
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

    def "given a message to DiscardServer, message should be discarded"() {
        given: "a telnet process"
        def proc = ('telnet localhost ' + currentPort)

        when: "we execute the process"
        def processExec = proc.execute()

        then: "should expect the process to be alive"
        processExec.alive

        and: "process should successfully shutdown"
        processExec.destroy()
    }
}
