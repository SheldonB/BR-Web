package io.placeholder.net;

public class GameMessage {
    private String payload;

    public GameMessage(String payload) {
        this.payload = payload;
    }

    public String getPayload() {
        return payload;
    }
}
