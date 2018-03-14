package io.placeholder.net;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.placeholder.net.session.Session;

import static java.util.Objects.requireNonNull;

public class GameMessageHandler extends SimpleChannelInboundHandler<GameMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(GameMessageHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, GameMessage msg) throws Exception {
        LOGGER.debug("BOOM: {}", msg.getPayload());
    }

    private Session getSession(ChannelHandlerContext ctx) {
        Session session = ctx.channel().attr(NetworkConstants.SESSION_KEY).get();
        return requireNonNull(session, "The session cannot be null.");
    }
}
