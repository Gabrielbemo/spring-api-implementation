package com.gabriel.api.util;

import com.gabriel.api.domain.Game;

public class GameCreator {

    public static Game createGameToBeSaved() {
        return Game.builder()
                   .name("tf2")
                   .build();
    }

    public static Game createValidGame() {
        return Game.builder()
                   .id(1L)
                   .name("tf2")
                   .build();
    }
}
