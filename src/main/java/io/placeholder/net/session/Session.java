package io.placeholder.net.session;

import io.netty.channel.Channel;

public class Session {

    private Channel channel;

    public Session(Channel channel) {
        this.channel = channel;
    }

    public final Channel getChannel() {
        return channel;
    }
}
