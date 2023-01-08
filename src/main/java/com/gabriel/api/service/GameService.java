package com.gabriel.api.service;

import com.gabriel.api.domain.Game;
import com.gabriel.api.exception.BadRequestException;
import com.gabriel.api.mapper.GameMapper;
import com.gabriel.api.repository.GameRepository;
import com.gabriel.api.requests.GamePostRequestBody;
import com.gabriel.api.requests.GamePutRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@RequiredArgsConstructor
public class GameService {

    private final GameRepository gameRepository;

    public Page<Game> listAll(Pageable pageable) {
        return gameRepository.findAll(pageable);
    }

    public List<Game> listAllNonPageable() {
        return gameRepository.findAll();
    }

    public List<Game> findByName(String name) {
        return gameRepository.findByName(name);
    }

    public Game findByIdOrThrowBadRequestException(long id) {
        return gameRepository.findById(id)
                             .orElseThrow(() -> new BadRequestException("Game not found"));

    }

    @Transactional
    public Game save(GamePostRequestBody gamePostRequestBody) {
        return gameRepository.save(GameMapper.INSTANCE.toGame(gamePostRequestBody));
    }

    @Transactional
    public void delete(long id) {
        gameRepository.delete(findByIdOrThrowBadRequestException(id));
    }

    @Transactional
    public void replace(GamePutRequestBody gamePutRequestBody) {
        findByIdOrThrowBadRequestException(gamePutRequestBody.getId());
        Game game = GameMapper.INSTANCE.toGame(gamePutRequestBody);
        game.setId(gamePutRequestBody.getId());
        gameRepository.save(game);
    }
}
