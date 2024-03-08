package com.example.cit.domain.log.log.service;

import com.example.cit.domain.gameMap.gameMap.entity.GameMap;
import com.example.cit.domain.gameMap.gameMap.repository.GameMapRepository;
import com.example.cit.domain.gameMap.gameMap.service.GameMapService;
import com.example.cit.domain.log.log.entity.PlayerLog;
import com.example.cit.domain.log.log.repository.PlayerLogRepository;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.member.member.service.AuthTokenService;
import com.example.cit.global.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlayerLogService {

    private final PlayerLogRepository playerLogRepository;

    @Transactional
    public void createPlayerLog(String logType, String username, Long userId,
                                 Long gameMapId, String stage, String step, String difficulty, Integer level,
                                 String detailText, Integer detailInt) {

        PlayerLog playerLog = PlayerLog.builder()
                .logType(logType)
                .username(username)
                .userId(userId)
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

    public Optional<PlayerLog> getGamesLastLog(Long memberId, GameMap gameMap) {
        return playerLogRepository.findTop1ByLogTypeAndUserIdAndGameMapStageAndGameMapStepAndGameMapDifficultyOrderByCreateDateDesc(
                "STAGECLEAR", memberId, gameMap.getStage(), gameMap.getStep(), gameMap.getDifficulty());


    }

    public Optional<PlayerLog> findByUserIdAndGameMapId(Long memberId, Long gameMapId) {
        return playerLogRepository.findByUserIdAndGameMapIdAndLogType(memberId, gameMapId, "STAGECLEAR");
    }

    public List<PlayerLog> getStageClearLog(Long id, String stage) {

        return playerLogRepository.findByUserIdAndGameMapStageAndLogTypeAndDetailInt(id, stage, "STAGECLEAR", 1);
    }



}
