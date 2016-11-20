package com.tech.radar.discard.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * Created by dmumma on 11/5/16.
 */
public class DiscardServerHandler extends SimpleChannelInboundHandler<Object> {

    private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(DiscardServerHandler.class);

    @Override
    public void channelRegistered(final ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("DiscardServerHandler REGISTERED");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(final ChannelHandlerContext ctx) throws Exception {
        LOGGER.info("DiscardServerHandler UNREGISTERED");
        super.channelUnregistered(ctx);
    }

    @Override
    protected void channelRead0(final ChannelHandlerContext channelHandlerContext,
                                final Object object) throws Exception {
        final ByteBuf in = (ByteBuf) object;
        if (in.isReadable()) {
            this.LOGGER.info((in.toString(io.netty.util.CharsetUtil.US_ASCII)));
        }
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
