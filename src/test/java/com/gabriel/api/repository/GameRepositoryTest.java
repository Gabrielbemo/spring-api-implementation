package com.gabriel.api.repository;

import com.gabriel.api.domain.Game;
import com.gabriel.api.util.GameCreator;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
@ActiveProfiles("test")
class GameRepositoryTest {

    @Autowired
    GameRepository gameRepository;

    @DisplayName("Throws a ConstraintViolationException when name is empty")
    @Test
    void save_ThrowsConstraintViolationException_WhenNameIsEmpty() {
        Game gameToBeSaved = new Game();

        assertThrows(ConstraintViolationException.class, () -> gameRepository.save(gameToBeSaved));
    }

    @DisplayName("Find by name and return a list of games if successful")
    @Test
    void findByName_ReturnsListOfGame_WhenSuccessful() {
        Game savedGame = gameRepository.save(GameCreator.CreateGameToBeSaved());

        List<Game> games = gameRepository.findByName(savedGame.getName());

        assertFalse(games.isEmpty());
        assertTrue(games.contains(savedGame));
    }

    @DisplayName("Find by name and return a empty list when game not found")
    @Test
    void findByName_ReturnsEmptyList_WhenGameNotFound() {
        List<Game> games = gameRepository.findByName("mine");

        assertTrue(games.isEmpty());
    }
}