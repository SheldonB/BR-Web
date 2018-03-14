package io.placeholder.net;

import io.netty.channel.ChannelPipeline;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpServerCodec;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.websocketx.WebSocketServerProtocolHandler;
import io.netty.handler.codec.http.websocketx.extensions.compression.WebSocketServerCompressionHandler;

import io.placeholder.ServerContext;


public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private static final String WEBSOCKET_ENDPOINT = "/websocket";

    private ServerContext context;

    public ServerChannelInitializer(ServerContext context) {
        this.context = context;
    }

    @Override
    protected void initChannel(SocketChannel ch) {
        ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast(new HttpServerCodec());
        pipeline.addLast(new HttpObjectAggregator(65536));
        pipeline.addLast(new WebSocketServerCompressionHandler());
        pipeline.addLast(new WebSocketServerProtocolHandler(WEBSOCKET_ENDPOINT,null, true));
        pipeline.addLast(new StaticPageHandler());
        pipeline.addLast(new WebSocketFrameDecoder(context));
        pipeline.addLast(new GameMessageHandler());
    }
}
