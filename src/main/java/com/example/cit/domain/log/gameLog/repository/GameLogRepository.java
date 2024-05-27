package com.example.cit.domain.log.gameLog.repository;

import com.example.cit.domain.log.gameLog.entity.GameLog;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface GameLogRepository extends JpaRepository<GameLog, Long> {
    Optional<GameLog> findByLogTypeAndUserIdAndGameMapIdAndClearCountLog_Result(String logType, Long userId, long gameMapId, int result);

    Optional<GameLog> findGameLogByLogTypeAndUserId(String logType, Long userId);

    @Query("SELECT g FROM GameLog g JOIN g.clearCountLog c WHERE g.logType = :logType AND g.userId = :userId AND g.gameMapId = :gameMapId AND c.result = 0")
    Optional<GameLog> findGameLogByLogTypeAndUserIdAndGameMapIdAndClearCountLogResultIsZero(
            @Param("logType") String logType,
            @Param("userId") Long userId,
            @Param("gameMapId") Long gameMapId);
}
