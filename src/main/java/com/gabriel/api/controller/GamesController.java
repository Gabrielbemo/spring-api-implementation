package com.gabriel.api.controller;

import com.gabriel.api.domain.Game;
import com.gabriel.api.util.DateUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("game")
@Log4j2
@RequiredArgsConstructor
public class GamesController {
    private final DateUtil dateUtil;

    @GetMapping(path = "list")
    public List<Game> list(){
        log.info(dateUtil.formatLocalDateTimeToDatabaseStyle(LocalDateTime.now()));
        return List.of(new Game("tf2"), new Game("minecraft"));
    }
}
