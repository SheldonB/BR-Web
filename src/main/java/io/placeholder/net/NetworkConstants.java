package io.placeholder.net;

import io.netty.util.AttributeKey;
import io.placeholder.net.session.Session;

public final class NetworkConstants {
    NetworkConstants() { }

    static final AttributeKey<Session> SESSION_KEY = AttributeKey.valueOf("SESSION_KEY");
}
