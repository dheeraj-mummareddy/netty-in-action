package com.tech.radar.discard.server;

import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.util.ReferenceCountUtil;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * Created by dmumma on 11/5/16.
 */
public class DiscardServerHandler extends ChannelInboundHandlerAdapter {

    private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(DiscardServerHandler.class);

    @Override
    public void channelRead(final ChannelHandlerContext ctx, final Object msg) throws Exception {
        final ByteBuf in = (ByteBuf) msg;
        try {
            if (in.isReadable()) {
                this.LOGGER.info((in.toString(io.netty.util.CharsetUtil.US_ASCII)));
            }
        } finally {
            ReferenceCountUtil.release(msg);
        }
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) {
        cause.printStackTrace();
        ctx.close();
    }
}
