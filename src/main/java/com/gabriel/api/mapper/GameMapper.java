package com.gabriel.api.mapper;

import com.gabriel.api.domain.Game;
import com.gabriel.api.requests.GamePostRequestBody;
import com.gabriel.api.requests.GamePutRequestBody;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring")
public abstract class GameMapper {
    public static final GameMapper INSTANCE = Mappers.getMapper(GameMapper.class);

    public abstract Game toGame(GamePostRequestBody gamePostRequestBody);
    public abstract Game toGame(GamePutRequestBody gamePutRequestBody);
}
