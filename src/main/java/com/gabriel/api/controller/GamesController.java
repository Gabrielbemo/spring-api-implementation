package com.gabriel.api.controller;

import com.gabriel.api.domain.Game;
import com.gabriel.api.service.GameService;
import com.gabriel.api.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

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
    public List<Game> list(){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return gameService.listAll();
    }
}
