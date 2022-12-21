package com.gabriel.api.service;

import com.gabriel.api.domain.Game;
import com.gabriel.api.mapper.GameMapper;
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

    public List<Game> findByName(String name) {
        return gameRepository.findByName(name);
    }

    public Game findByIdOrThrowBadResquestException(long id) {
        return gameRepository.findById(id)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Game not found"));

    }

    public Game save(GamePostRequestBody gamePostRequestBody) {
        return gameRepository.save(GameMapper.INSTANCE.toGame(gamePostRequestBody));
    }

    public void delete(long id) {
        gameRepository.delete(findByIdOrThrowBadResquestException(id));
    }

    public void replace(GamePutRequestBody gamePutRequestBody) {
        findByIdOrThrowBadResquestException(gamePutRequestBody.getId());
        Game game = GameMapper.INSTANCE.toGame(gamePutRequestBody);
        game.setId(gamePutRequestBody.getId());
        gameRepository.save(game);
    }
}
