package com.example.cit.domain.gameMap.gameMap.repository;

import com.example.cit.domain.gameMap.gameMap.entity.GameMap;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// 게임 맵 jpa 리포지토리
public interface GameMapRepository extends JpaRepository<GameMap, Long> {
    Optional<GameMap> findByStepAndLevel(String tutorial, int i);

    Optional<GameMap> findByStepAndDifficultyAndLevel(String numberPart, String letterPart, int lastNumber);
}
