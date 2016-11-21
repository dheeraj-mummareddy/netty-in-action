package com.tech.radar.echo.server;

import com.tech.radar.base.BaseChannelInboundHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * Created by dmumma on 11/20/16.
 */
public class EchoServerHandler extends BaseChannelInboundHandler<Object> {

    private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(EchoServerHandler.class);

    @Override
    protected void channelRead0(final ChannelHandlerContext channelHandlerContext,
                                final Object object) throws Exception {
        final ByteBuf in = (ByteBuf) object;
        if (in.isReadable()) {
            final String inputMessage = in.toString(io.netty.util.CharsetUtil.US_ASCII);
            this.LOGGER.info(inputMessage);
            channelHandlerContext.writeAndFlush(inputMessage);
        }
    }
}
