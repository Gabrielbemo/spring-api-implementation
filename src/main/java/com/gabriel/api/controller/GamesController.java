package com.gabriel.api.controller;

import com.gabriel.api.domain.Game;
import com.gabriel.api.requests.GamePostRequestBody;
import com.gabriel.api.requests.GamePutRequestBody;
import com.gabriel.api.service.GameService;
import com.gabriel.api.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("games")
@Log4j2
@RequiredArgsConstructor
public class GamesController {
    private final DateUtil dateUtil;
    private final GameService gameService;

    @GetMapping
    public ResponseEntity<List<Game>> list(){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return new ResponseEntity<>(gameService.listAll(), HttpStatus.OK);
    }

    @GetMapping(path = "/{id}")
    public ResponseEntity<Game> findById(@PathVariable long id){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return ResponseEntity.ok(gameService.findByIdOrThrowBadResquestException(id));
    }

    @PostMapping
    public ResponseEntity<Game> save(@RequestBody GamePostRequestBody gamePostRequestBody){
        return new ResponseEntity<>(gameService.save(gamePostRequestBody), HttpStatus.CREATED);
    }

    @DeleteMapping(path = "/{id}")
    public ResponseEntity<Void> delete(@PathVariable long id){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        gameService.delete(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PutMapping
    public ResponseEntity<Void> replace(@RequestBody GamePutRequestBody gamePutRequestBody){
        gameService.replace(gamePutRequestBody);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
