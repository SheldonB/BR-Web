package io.placeholder;


import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import io.placeholder.net.ServerChannelInitializer;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.Options;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    private static final int DEFAULT_PORT = 8080;

    private void init(int port) throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup loopGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup, loopGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new ServerChannelInitializer());

            Channel channel =  bootstrap.bind(port).sync().channel();

            LOGGER.info("Starting Server on Port {}", port);

            channel.closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            loopGroup.shutdownGracefully();
        }
    }

    public static void main(String... args) throws Exception {
        Options options = new Options();
        options.addOption("port", true, "The port for the server to run on");

        CommandLineParser parser = new DefaultParser();
        CommandLine commandLine = parser.parse(options, args);

        int port = commandLine.hasOption("port") ? Integer.parseInt(commandLine.getOptionValue("port")) : DEFAULT_PORT;

        Server server = new Server();
        server.init(port);
    }
}
