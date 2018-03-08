package io.placeholder;


import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import org.apache.commons.cli.Options;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.CommandLineParser;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.placeholder.net.ServerChannelInitializer;

public class Server {

    private static final Logger LOGGER = LoggerFactory.getLogger(Server.class);

    private static final int DEFAULT_PORT = 8080;

    private final ServerContext context = new ServerContext();

    private int port;

    private Server(int port) {
        this.port = port;
    }

    private void init() throws Exception {
        initNetworking();
    }

    private void initNetworking() throws Exception {
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup loopGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap bootstrap = new ServerBootstrap();

            bootstrap.group(bossGroup, loopGroup);
            bootstrap.channel(NioServerSocketChannel.class);
            bootstrap.childHandler(new ServerChannelInitializer(context));

            Channel channel =  bootstrap.bind(port).sync().channel();

            LOGGER.info("Starting Server on Port {}", port);

            channel.closeFuture().sync();

        } finally {
            bossGroup.shutdownGracefully();
            loopGroup.shutdownGracefully();
        }
    }

    public static void main(String... args) {
        try {
            Options options = new Options();
            options.addOption("port", true, "The port for the server to run on");

            CommandLineParser parser = new DefaultParser();
            CommandLine commandLine = parser.parse(options, args);

            int port = commandLine.hasOption("port") ? Integer.parseInt(commandLine.getOptionValue("port")) : DEFAULT_PORT;

            Server server = new Server(port);
            server.init();
        } catch (Exception e) {
            LOGGER.error("Opps.... The Server Crashed.", e);
            System.exit(-1);
        }
    }
}
