package com.gabriel.api.controller;

import com.gabriel.api.domain.Game;
import com.gabriel.api.requests.GamePostRequestBody;
import com.gabriel.api.requests.GamePutRequestBody;
import com.gabriel.api.service.GameService;
import com.gabriel.api.util.DateUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@Log4j2
@RequestMapping("/api/game/v1")
@RequiredArgsConstructor
public class GamesController {
    private final DateUtil dateUtil;
    private final GameService gameService;

    @GetMapping
    public ResponseEntity<Page<Game>> list(Pageable pageable) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(gameService.listAll(pageable), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Game> findById(@PathVariable long id) {
        return ResponseEntity.ok(gameService.findByIdOrThrowBadRequestException(id));
    }

    @GetMapping(path = "/find")
    public ResponseEntity<List<Game>> findByName(@RequestParam String name) {
        return ResponseEntity.ok(gameService.findByName(name));
    }

    @PostMapping
    public ResponseEntity<Game> save(@RequestBody @Valid GamePostRequestBody gamePostRequestBody) {
        return new ResponseEntity<>(gameService.save(gamePostRequestBody), HttpStatus.CREATED);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody GamePutRequestBody gamePutRequestBody) {
        gameService.replace(gamePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id) {
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        gameService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
