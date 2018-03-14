package io.placeholder.net;

import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.http.websocketx.TextWebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketFrame;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.placeholder.ServerContext;
import io.placeholder.game.model.Player;
import io.placeholder.net.session.Session;

import java.util.List;

public class WebSocketFrameDecoder extends MessageToMessageDecoder<WebSocketFrame> {

    private static final Logger LOGGER = LoggerFactory.getLogger(WebSocketFrameDecoder.class);

    private ServerContext context;

    WebSocketFrameDecoder(ServerContext context) {
        this.context = context;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
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
    protected void decode(ChannelHandlerContext ctx, WebSocketFrame msg, List<Object> out) throws Exception {
        if (msg instanceof TextWebSocketFrame) {
            TextWebSocketFrame frame = (TextWebSocketFrame) msg;
            out.add(new GameMessage(frame.text()));
        }
    }
}
