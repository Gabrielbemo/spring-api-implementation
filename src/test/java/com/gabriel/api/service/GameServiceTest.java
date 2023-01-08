package com.gabriel.api.service;

import com.gabriel.api.domain.Game;
import com.gabriel.api.repository.GameRepository;
import com.gabriel.api.util.GameCreator;
import com.gabriel.api.util.GamePostRequestBodyCreator;
import com.gabriel.api.util.GamePutRequestBodyCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.BDDMockito;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class GameServiceTest {

    @InjectMocks
    private GameService gameService;

    @Mock
    private GameRepository gameRepositoryMock;

    @BeforeEach
    void setUp() {
        Game validGame = GameCreator.CreateValidGame();

        BDDMockito.when(gameRepositoryMock.findAll(ArgumentMatchers.any(PageRequest.class)))
                  .thenReturn(new PageImpl<>(List.of(validGame)));

        BDDMockito.when(gameRepositoryMock.findAll())
                  .thenReturn(List.of(validGame));

        BDDMockito.when(gameRepositoryMock.findById(validGame.getId()))
                  .thenReturn(Optional.of(validGame));

        BDDMockito.when(gameRepositoryMock.findByName(validGame.getName()))
                  .thenReturn(List.of(validGame));

        BDDMockito.when(gameRepositoryMock.save(ArgumentMatchers.any(Game.class)))
                  .thenReturn(GameCreator.CreateValidGame());

        BDDMockito.doNothing().when(gameRepositoryMock).delete(ArgumentMatchers.any(Game.class));
    }

    @DisplayName("Return a list of games inside a page object when successful")
    @Test
    void list_ReturnsListOfGamesInsidePageObject_WhenSuccessful() {

        Page<Game> foundGames = gameService.listAll(null);

        assertNotNull(foundGames);

        assertNotNull(foundGames.toList());

        assertEquals(1, foundGames.getSize());

        assertEquals(
                foundGames.toList().get(0).getName(),
                GameCreator.CreateValidGame().getName());
    }

    @DisplayName("Return a list of games when successful")
    @Test
    void listAll_ReturnsListOfGames_WhenSuccessful() {

        List<Game> foundGames = gameService.listAllNonPageable();

        assertNotNull(foundGames);

        assertEquals(1, foundGames.size());

        assertEquals(
                foundGames.get(0).getName(),
                GameCreator.CreateValidGame().getName());
    }

    @DisplayName("Return a game by id when successful")
    @Test
    void findById_ReturnsGame_WhenSuccessful() {
        Game gameToBeFind = GameCreator.CreateValidGame();
        Game foundGame = gameService.findByIdOrThrowBadRequestException(gameToBeFind.getId());

        assertNotNull(foundGame);

        assertEquals(
                foundGame.getId(),
                gameToBeFind.getId());

        assertEquals(
                foundGame.getName(),
                gameToBeFind.getName());
    }

    @DisplayName("Return a list of games when find by name when successful")
    @Test
    void findByName_ReturnsListOfGames_WhenSuccessful() {

        List<Game> foundGames = gameService.findByName(GameCreator.CreateValidGame().getName());

        assertNotNull(foundGames);

        assertEquals(1, foundGames.size());

        assertEquals(
                foundGames.get(0).getName(),
                GameCreator.CreateValidGame().getName());
    }

    @DisplayName("Save a game and return saved game when successful")
    @Test
    void save_ReturnsGame_WhenSuccessful() {
        Game savedGame = gameService.save(GamePostRequestBodyCreator.createGamePostRequestBody());

        assertNotNull(savedGame);

        assertEquals(savedGame.getId(), GameCreator.CreateValidGame().getId());

        assertEquals(savedGame.getName(), GameCreator.CreateValidGame().getName());
    }

    @DisplayName("Replace a game when successful")
    @Test
    void replace_ReturnsVoidResponseEntity_WhenSuccessful() {

        assertDoesNotThrow(() -> gameService.replace(GamePutRequestBodyCreator.createGamePutRequestBody()));
    }

    @DisplayName("Delete a game when successful")
    @Test
    void delete_ReturnsVoidResponseEntity_WhenSuccessful() {

        assertDoesNotThrow(() -> gameService.delete(GameCreator.CreateValidGame().getId()));
    }
}