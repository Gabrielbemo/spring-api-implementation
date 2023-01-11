package com.gabriel.api.controller;

import com.gabriel.api.domain.Game;
import com.gabriel.api.requests.GamePostRequestBody;
import com.gabriel.api.requests.GamePutRequestBody;
import com.gabriel.api.service.GameService;
import com.gabriel.api.util.GameCreator;
import com.gabriel.api.util.GamePostRequestBodyCreator;
import com.gabriel.api.util.GamePutRequestBodyCreator;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
class GameControllerTest {

    @InjectMocks
    private GameController gameController;

    @Mock
    private GameService gameServiceMock;

    @BeforeEach
    void setUp() {
        Game validGame = GameCreator.createValidGame();

        BDDMockito.when(gameServiceMock.listAll(ArgumentMatchers.any()))
                  .thenReturn(new PageImpl<>(List.of(validGame)));

        BDDMockito.when(gameServiceMock.listAllNonPageable())
                  .thenReturn(List.of(validGame));

        BDDMockito.when(gameServiceMock.findByIdOrThrowBadRequestException(validGame.getId()))
                  .thenReturn(validGame);

        BDDMockito.when(gameServiceMock.findByName(validGame.getName()))
                  .thenReturn(List.of(validGame));

        BDDMockito.when(gameServiceMock.save(ArgumentMatchers.any(GamePostRequestBody.class)))
                  .thenReturn(GameCreator.createValidGame());

        BDDMockito.doNothing().when(gameServiceMock).replace(ArgumentMatchers.any(GamePutRequestBody.class));

        BDDMockito.doNothing().when(gameServiceMock).delete(validGame.getId());
    }

    @DisplayName("Return a list of games inside a page object when successful")
    @Test
    void list_ReturnsListOfGamesInsidePageObject_WhenSuccessful() {

        Page<Game> foundGames = gameController.list(null).getBody();

        assertNotNull(foundGames);

        assertNotNull(foundGames.toList());

        assertFalse(foundGames.isEmpty());

        assertEquals(
                foundGames.toList().get(0).getName(),
                GameCreator.createValidGame().getName());
    }

    @DisplayName("Return a list of games when successful")
    @Test
    void listAll_ReturnsListOfGames_WhenSuccessful() {

        List<Game> foundGames = gameController.listAll().getBody();

        assertNotNull(foundGames);

        assertFalse(foundGames.isEmpty());

        assertEquals(
                foundGames.get(0).getName(),
                GameCreator.createValidGame().getName());
    }

    @DisplayName("Return a game by id when successful")
    @Test
    void findById_ReturnsGame_WhenSuccessful() {
        Game gameToBeFind = GameCreator.createValidGame();
        Game foundGame = gameController.findById(gameToBeFind.getId()).getBody();

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

        List<Game> foundGames = gameController.findByName(GameCreator.createValidGame().getName()).getBody();

        assertNotNull(foundGames);

        assertFalse(foundGames.isEmpty());

        assertEquals(
                foundGames.get(0).getName(),
                GameCreator.createValidGame().getName());
    }

    @DisplayName("Return a empty list of games is not find by name")
    @Test
    void findByName_ReturnsEmptyListOfGame_WhenGameIsNotFound() {
        BDDMockito.when(gameServiceMock.findByName(ArgumentMatchers.any(String.class)))
                  .thenReturn(Collections.emptyList());

        List<Game> foundGames = gameController.findByName(GameCreator.createValidGame().getName()).getBody();

        assertNotNull(foundGames);

        assertTrue(foundGames.isEmpty());
    }

    @DisplayName("Save a game and return saved game when successful")
    @Test
    void save_ReturnsGame_WhenSuccessful() {
        Game savedGame = gameController.save(GamePostRequestBodyCreator.createGamePostRequestBody()).getBody();

        assertNotNull(savedGame);

        assertEquals(savedGame.getId(), GameCreator.createValidGame().getId());

        assertEquals(savedGame.getName(), GameCreator.createValidGame().getName());
    }

    @DisplayName("Replace a game when successful")
    @Test
    void replace_ReturnsVoidResponseEntity_WhenSuccessful() {
        ResponseEntity<Void> entity = gameController.replace(GamePutRequestBodyCreator.createGamePutRequestBody());

        assertNotNull(entity);

        assertEquals(entity.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @DisplayName("Delete a game when successful")
    @Test
    void delete_ReturnsVoidResponseEntity_WhenSuccessful() {

        assertDoesNotThrow(() -> {
            ResponseEntity<Void> entity = gameController.delete(GameCreator.createValidGame().getId());

            assertNotNull(entity);

            assertEquals(entity.getStatusCode(), HttpStatus.NO_CONTENT);
        });
    }
}