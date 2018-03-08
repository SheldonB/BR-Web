package io.placeholder.game.model;

import io.netty.util.internal.ConcurrentSet;

import java.util.Set;

public class Game {

    private final Set<Player> players = new ConcurrentSet<>();

    public Game() {
        
    }

    public void addPlayer(Player player) {
        players.add(player);
    }

    public boolean isFull() {
        return players.size() == 10;
    }
}
