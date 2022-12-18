package com.gabriel.api.service;

import com.gabriel.api.domain.Game;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Service
public class GameService {
    private List<Game> games = List.of(new Game(1L, "tf2"), new Game(2L, "minecraft"));

    public List<Game> listAll(){
        return games;
    }

    public Game findById(long id){
        return games.stream()
                .filter(game -> game.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Game not found"));
    }
}
