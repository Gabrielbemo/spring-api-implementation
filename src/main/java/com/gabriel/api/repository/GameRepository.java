package com.gabriel.api.repository;

import com.gabriel.api.domain.Game;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface GameRepository  extends JpaRepository<Game, Long> {

}
