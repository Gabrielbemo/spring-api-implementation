package com.gabriel.api.util;

import com.gabriel.api.requests.GamePostRequestBody;

public class GamePostRequestBodyCreator {

    public static GamePostRequestBody createGamePostRequestBody() {
        return GamePostRequestBody.builder()
                                  .name(GameCreator.CreateValidGame().getName())
                                  .build();
    }
}
