package com.tech.radar.discard.server;

import com.tech.radar.base.BaseChannelInboundHandler;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * Created by dmumma on 11/5/16.
 */
public class DiscardServerHandler extends BaseChannelInboundHandler<Object> {

    private static final InternalLogger LOGGER = InternalLoggerFactory.getInstance(DiscardServerHandler.class);

    @Override
    protected void channelRead0(final ChannelHandlerContext channelHandlerContext,
                                final Object object) throws Exception {
        final ByteBuf in = (ByteBuf) object;
        if (in.isReadable()) {
            this.LOGGER.info((in.toString(io.netty.util.CharsetUtil.US_ASCII)));
        }
    }
}
