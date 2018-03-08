package io.placeholder.game.model;

import io.netty.util.internal.ConcurrentSet;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class Game implements Runnable {

    private static final Logger LOGGER = LoggerFactory.getLogger(Game.class);

    private final Set<Player> players = new ConcurrentSet<>();

    public Game() {
        
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public boolean isFull() {
        return players.size() == 10;
    }

    // Temporary, thread creation probably needs to be spawned
    // out into another class.
    public void start() {
        Thread thread = new Thread(this::run);
        thread.start();
    }

    @Override
    public void run() {
        while (true) {
            // Do The Game Stuff
        }
    }
}
