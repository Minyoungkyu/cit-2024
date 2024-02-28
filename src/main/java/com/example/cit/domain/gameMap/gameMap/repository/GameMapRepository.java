package com.example.cit.domain.gameMap.gameMap.repository;

import com.example.cit.domain.gameMap.gameMap.entity.GameMap;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GameMapRepository extends JpaRepository<GameMap, Long> {
}
