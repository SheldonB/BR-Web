package io.placeholder.net.session;

import io.netty.channel.Channel;
import io.placeholder.game.model.Player;

public class Session {

    private Channel channel;

    private Player player;

    public Session(Channel channel, Player player) {
        this.channel = channel;
        this.player = player;
    }

    public final Channel getChannel() {
        return channel;
    }
}
