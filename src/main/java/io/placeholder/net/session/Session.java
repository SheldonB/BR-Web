package io.placeholder.net.session;

import io.netty.channel.Channel;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import io.placeholder.net.GameMessage;
import io.placeholder.game.model.Player;

public class Session {

    private static final Logger LOGGER = LoggerFactory.getLogger(Session.class);

    private Channel channel;

    private Player player;

    public Session(Channel channel, Player player) {
        this.channel = channel;
        this.player = player;
    }

    public final Channel getChannel() {
        return channel;
    }

    public void handleMessage(GameMessage msg) {

    }
}
