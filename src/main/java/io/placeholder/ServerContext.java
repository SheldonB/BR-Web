package io.placeholder;

import io.placeholder.game.GameService;
import io.placeholder.game.MatchService;

/**
 * The {@link ServerContext} represents the entire
 * server, the {@link MatchService}, and {@link GameService}
 */
public class ServerContext {

    private final MatchService matchService = new MatchService(this);

    private final GameService gameService = new GameService(this);

    /**
     * The Server Context should not be
     * initialized anywhere but by the server.
     */
    ServerContext() { }

    public MatchService getMatchService() {
        return matchService;
    }

    public GameService getGameService() {
        return gameService;
    }
}
