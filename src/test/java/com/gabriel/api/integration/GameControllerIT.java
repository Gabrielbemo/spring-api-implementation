package com.gabriel.api.integration;

import com.gabriel.api.domain.Game;
import com.gabriel.api.repository.GameRepository;
import com.gabriel.api.requests.GamePostRequestBody;
import com.gabriel.api.util.GameCreator;
import com.gabriel.api.util.GamePostRequestBodyCreator;
import com.gabriel.api.wrapper.PageableResponse;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureTestDatabase
@ActiveProfiles("test")
@DirtiesContext(classMode = DirtiesContext.ClassMode.BEFORE_EACH_TEST_METHOD)
class GameControllerIT {
    @Autowired
    private TestRestTemplate testRestTemplate;
    @Autowired
    private GameRepository gameRepository;

    @Test
    @DisplayName("list returns list of game inside page object when successful")
    void list_ReturnsListOfGamesInsidePageObject_WhenSuccessful() {
        Game savedGame = gameRepository.save(GameCreator.createGameToBeSaved());

        String expectedName = savedGame.getName();

        PageableResponse<Game> gamesPageable = testRestTemplate.exchange("/api/v1/games", HttpMethod.GET, null,
                                                                         new ParameterizedTypeReference<PageableResponse<Game>>() {
                                                                         }).getBody();

        assertNotNull(gamesPageable);

        assertFalse(gamesPageable.stream().toList().isEmpty());

        assertEquals(gamesPageable.stream().toList().get(0).getName(), expectedName);
    }

    @DisplayName("Return a list of games when successful")
    @Test
    void listAll_ReturnsListOfGames_WhenSuccessful() {
        Game savedGame = gameRepository.save(GameCreator.createGameToBeSaved());

        String expectedName = savedGame.getName();

        List<Game> games = testRestTemplate.exchange("/api/v1/games/all", HttpMethod.GET, null,
                                                     new ParameterizedTypeReference<List<Game>>() {
                                                     }).getBody();

        assertNotNull(games);

        assertFalse(games.isEmpty());

        assertEquals(games.get(0).getName(), expectedName);
    }

    @DisplayName("Return a game by id when successful")
    @Test
    void findById_ReturnsGame_WhenSuccessful() {
        Game savedGame = gameRepository.save(GameCreator.createGameToBeSaved());

        Game foundGame = testRestTemplate.getForObject("/api/v1/games/{id}", Game.class, savedGame.getId());

        assertNotNull(foundGame);

        assertEquals(foundGame.getId(), savedGame.getId());

        assertEquals(foundGame.getName(), savedGame.getName());
    }

    @DisplayName("Return a list of games when find by name when successful")
    @Test
    void findByName_ReturnsListOfGames_WhenSuccessful() {
        Game savedGame = gameRepository.save(GameCreator.createGameToBeSaved());

        String url = String.format("/api/v1/games/find?name=%s", savedGame.getName());

        List<Game> games = testRestTemplate.exchange(url, HttpMethod.GET, null,
                                                     new ParameterizedTypeReference<List<Game>>() {
                                                     }).getBody();

        assertNotNull(games);

        assertFalse(games.isEmpty());
    }

    @DisplayName("Return a empty list of games is not find by name")
    @Test
    void findByName_ReturnsEmptyListOfGame_WhenGameIsNotFound() {
        List<Game> games = testRestTemplate.exchange("/api/v1/games/find?name=InexistentName", HttpMethod.GET, null,
                                                     new ParameterizedTypeReference<List<Game>>() {
                                                     }).getBody();

        assertNotNull(games);

        assertTrue(games.isEmpty());
    }

    @DisplayName("Save a game and return saved game when successful")
    @Test
    void save_ReturnsGame_WhenSuccessful() {
        GamePostRequestBody gamePostRequestBody = GamePostRequestBodyCreator.createGamePostRequestBody();

        ResponseEntity<Game> gameResponseEntity = testRestTemplate.postForEntity("/api/v1/games", gamePostRequestBody, Game.class);

        assertNotNull(gameResponseEntity);
        assertNotNull(gameResponseEntity.getBody());
        assertNotNull(gameResponseEntity.getBody().getId());
        assertEquals(gameResponseEntity.getStatusCode(), HttpStatus.CREATED);
    }

    @DisplayName("Replace a game when successful")
    @Test
    void replace_ReturnsVoidResponseEntity_WhenSuccessful() {
        Game savedGame = gameRepository.save(GameCreator.createGameToBeSaved());

        savedGame.setName("otherName");

        ResponseEntity<Void> gameResponseEntity = testRestTemplate.exchange("/api/v1/games", HttpMethod.PUT,
                                                                            new HttpEntity<>(savedGame), Void.class);

        assertNotNull(gameResponseEntity);
        assertEquals(gameResponseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
    }

    @DisplayName("Delete a game when successful")
    @Test
    void delete_ReturnsVoidResponseEntity_WhenSuccessful() {
        Game savedGame = gameRepository.save(GameCreator.createGameToBeSaved());

        ResponseEntity<Void> gameResponseEntity = testRestTemplate.exchange("/api/v1/games/{id}", HttpMethod.DELETE,
                                                                            null, Void.class, savedGame.getId());

        assertNotNull(gameResponseEntity);
        assertEquals(gameResponseEntity.getStatusCode(), HttpStatus.NO_CONTENT);
    }

}
