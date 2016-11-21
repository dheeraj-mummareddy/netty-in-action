package com.tech.radar.base;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.util.internal.logging.InternalLogger;
import io.netty.util.internal.logging.InternalLoggerFactory;

/**
 * Created by dmumma on 11/20/16.
 *
 * @param <I>
 */

public abstract class BaseChannelInboundHandler<I> extends SimpleChannelInboundHandler<I> {

    private final InternalLogger logger = InternalLoggerFactory.getInstance(getClass());

    @Override
    public void channelRegistered(final ChannelHandlerContext ctx) throws Exception {
        this.logger.info(this.getClass() + " REGISTERED");
        super.channelRegistered(ctx);
    }

    @Override
    public void channelUnregistered(final ChannelHandlerContext ctx) throws Exception {
        this.logger.info(this.getClass() + " UNREGISTERED");
        super.channelUnregistered(ctx);
    }

    @Override
    public void channelActive(final ChannelHandlerContext ctx) throws Exception {
        this.logger.info(this.getClass() + " ACTIVE");
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(final ChannelHandlerContext ctx) throws Exception {
        this.logger.info(this.getClass() + " INACTIVE");
        ctx.fireChannelInactive();
    }

    @Override
    public void exceptionCaught(final ChannelHandlerContext ctx, final Throwable cause) throws Exception {
        this.logger.error("Exception: ", cause);
        super.exceptionCaught(ctx, cause);
    }
}
