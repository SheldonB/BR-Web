package io.placeholder.net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.placeholder.ServerContext;
import io.placeholder.game.model.Player;

import io.placeholder.net.session.Session;

public class WebSocketFrameHandler extends SimpleChannelInboundHandler<WebSocketFrame> {
    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketFrameHandler.class);

    private ServerContext context;

    WebSocketFrameHandler(ServerContext context) {
        this.context = context;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) {
        // Intercept the WebSocket Handshake Complete Event.
        // This will create a new use session, and pass them over to matchmaking.
        if (evt instanceof WebSocketServerProtocolHandler.HandshakeComplete) {
            LOGGER.debug("New Client Connected: {}", ctx.channel().localAddress());

            Channel channel = ctx.channel();

            // This may not be the correct way to handle the Player/Session
            // lifecycle, as it is a circular reference. But Allows us to easily
            // get the player from the session, and then backtrack from the player to
            // the communication channel.
            Player player = new Player();
            Session session = new Session(channel, player);

            channel.attr(NetworkConstants.SESSION_KEY).set(session);

            player.setSession(session);

            context.getMatchService().addPlayerToLobby(player);
        }
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, WebSocketFrame msg) {
        if (msg instanceof TextWebSocketFrame) {
            String text = ((TextWebSocketFrame) msg).text();
            LOGGER.debug(text);
        }
    }
}
