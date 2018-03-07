package io.placeholder.game;

import io.placeholder.ServerContext;

public class MatchService {

    private ServerContext context;

    public MatchService(ServerContext context) {
        this.context = context;
    }

    public ServerContext getContext() {
        return context;
    }
}
