package io.placeholder.game;

import io.placeholder.ServerContext;
import io.placeholder.game.model.Game;
import io.placeholder.game.model.Player;

import java.util.concurrent.ConcurrentLinkedQueue;

/**
 * The {@link MatchService} is responsible for
 * putting players that have connected into a lobby
 * with other players, which will then be passed over to the GameService.
 */
public final class MatchService {

    private final ConcurrentLinkedQueue<Player> playersInLobby = new ConcurrentLinkedQueue<>();

    private ServerContext context;

    public MatchService(ServerContext context) {
        this.context = context;
    }

    public ServerContext getContext() {
        return context;
    }

    /**
     * Adds a new player to the lobby, and then
     * creates a game and passes to over to the
     * GameService.
     *
     * @param player
     */
    public void addPlayerToLobby(Player player) {
        playersInLobby.add(player);

        createGameIfReady();
    }

    private void createGameIfReady() {
        if (playersInLobby.size() >= 10) {
            Game game = new Game();

            while (!game.isFull()) {
                game.addPlayer(playersInLobby.poll());
            }

            context.getGameService().addGame(game);
        }
    }
}
