package com.gabriel.api.util;

import com.gabriel.api.requests.GamePutRequestBody;

public class GamePutRequestBodyCreator {

    public static GamePutRequestBody createGamePutRequestBody() {
        return GamePutRequestBody.builder()
                                 .id(GameCreator.CreateValidGame().getId())
                                 .name(GameCreator.CreateValidGame().getName())
                                 .build();
    }
}
