package com.gabriel.api.service;

import com.gabriel.api.domain.Game;
import com.gabriel.api.repository.GameRepository;
import com.gabriel.api.requests.GamePostRequestBody;
import com.gabriel.api.requests.GamePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public List<Game> listAll() {
        return gameRepository.findAll();
    }

    public Game findByIdOrThrowBadResquestException(long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Game not found"));

    }

    public Game save(GamePostRequestBody gamePostRequestBody) {
        return gameRepository.save(Game.builder().name(gamePostRequestBody.getName()).build());
    }

    public void delete(long id) {
        gameRepository.delete(findByIdOrThrowBadResquestException(id));
    }

    public void replace(GamePutRequestBody gamePutRequestBody) {
        findByIdOrThrowBadResquestException(gamePutRequestBody.getId());
        Game game =Game.builder()
                .id(gamePutRequestBody.getId())
                .name(gamePutRequestBody.getName())
                .build();

        gameRepository.save(game);
    }
}
