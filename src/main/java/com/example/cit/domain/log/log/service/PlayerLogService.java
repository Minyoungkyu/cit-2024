package com.example.cit.domain.log.log.service;

import com.example.cit.domain.gameMap.gameMap.entity.GameMap;
import com.example.cit.domain.gameMap.gameMap.repository.GameMapRepository;
import com.example.cit.domain.gameMap.gameMap.service.GameMapService;
import com.example.cit.domain.log.log.entity.PlayerLog;
import com.example.cit.domain.log.log.repository.PlayerLogRepository;
import com.example.cit.domain.member.member.entity.Member;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlayerLogService {

    private final PlayerLogRepository playerLogRepository;
    private final GameMapService gameMapService;

    @Transactional
    public void createPlayerLog(String logType, String username,
                                 Long gameMapId, String stage, String step, String difficulty, Integer level,
                                 String detailText, Integer detailInt) {

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

    public List<PlayerLog> getGamesLastLog(Member member, Long gameMapId) {

        return gameMapService.findGameMapById(gameMapId)
                .flatMap(gameMap -> playerLogRepository.findTop1ByLogTypeAndUsernameAndGameMapStageAndGameMapStepAndGameMapDifficultyOrderByCreateDateDesc(
                        "STAGECLEAR", member.getUsername(), gameMap.getStage(), gameMap.getStep(), gameMap.getDifficulty()))
                .flatMap(playerLog1 -> findPlayerLogById(playerLog1.getId())
                        .map(playerLog2 -> List.of(playerLog1, playerLog2)))
                .orElse(List.of());
    }

    public Optional<PlayerLog> findPlayerLogById(Long id) {
        return playerLogRepository.findById(id);
    }

    public List<PlayerLog> getStageClearLog(Member member, String stage) {
        return playerLogRepository.findByUsernameAndGameMapStageAndLogTypeAndDetailInt(member.getUsername(), stage, "STAGECLEAR", 1);
    }



}
