package com.gabriel.api.service;

import com.gabriel.api.domain.Game;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GameService {
    public List<Game> listAll(){
        return List.of(new Game(1L, "tf2"), new Game(2L, "minecraft"));
    }
}
