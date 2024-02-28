package com.example.cit.domain.log.log.service;

import com.example.cit.domain.gameMap.gameMap.entity.GameMap;
import com.example.cit.domain.gameMap.gameMap.repository.GameMapRepository;
import com.example.cit.domain.log.log.entity.PlayerLog;
import com.example.cit.domain.log.log.repository.PlayerLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlayerLogService {

    private final PlayerLogRepository playerLogRepository;

    @Transactional
    public void createPlayerLog(String logType, String username,
                                 Long gameMapId, String stage, String step, String difficulty, Integer level,
                                 String detailText, String detailInt) {

        PlayerLog playerLog = PlayerLog.builder()
                .logType(logType)
                .username(username)
                .gameMapId(gameMapId)
                .gameMapStage(stage)
                .gameMapStep(step)
                .gameMapDifficulty(difficulty)
                .gameMapLevel(level)
                .detailText(detailText)
                .detailInt(detailInt)
                .build();

        playerLogRepository.save(playerLog);
    }

    public Optional<PlayerLog> findPlayerLogById(Long id) {
        return playerLogRepository.findById(id);
    }



}
