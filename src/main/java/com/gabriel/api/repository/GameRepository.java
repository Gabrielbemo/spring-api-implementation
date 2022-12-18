package com.gabriel.api.repository;

import com.gabriel.api.domain.Game;

import java.util.List;

public interface GameRepository {
    List<Game> listAll();
}
