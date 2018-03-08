package io.placeholder.game;

import io.netty.util.internal.ConcurrentSet;
import io.placeholder.ServerContext;
import io.placeholder.game.model.Game;

import java.util.Set;

/**
 * The {@link GameService} is responsible for all matches
 * that are currently running on the server.
 *
 * Games are passed to the game service for handling
 * when the {@link MatchService} determines that a game is
 * ready to start.
 */
public final class GameService {

    private ServerContext context;

    private final Set<Game> currentGames = new ConcurrentSet<>();

    public GameService(ServerContext context) {
        this.context = context;
    }

    public void addGame(Game game) {
        currentGames.add(game);
    }
}
