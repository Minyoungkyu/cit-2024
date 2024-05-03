package com.example.cit.domain.log.log.service;

import com.example.cit.domain.gameMap.gameMap.dto.GameMapDto;
import com.example.cit.domain.gameMap.gameMap.entity.GameMap;
import com.example.cit.domain.gameMap.gameMap.service.GameMapService;
import com.example.cit.domain.log.log.entity.PlayerLog;
import com.example.cit.domain.log.log.repository.PlayerLogRepository;
import com.example.cit.domain.member.member.entity.Member;
import com.querydsl.core.group.GroupBy;
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
    private final GameMapService gameMapService;

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
        return playerLogRepository.findTop1ByLogTypeAndUserIdAndGameMapStageAndGameMapStepAndGameMapDifficultyOrderByModifyDateDesc(
                "STAGECLEAR", memberId, gameMap.getStage(), gameMap.getStep(), gameMap.getDifficulty());


    }

    public Optional<PlayerLog> findByUserIdAndGameMapId(Long memberId, Long gameMapId) {
        return playerLogRepository.findByUserIdAndGameMapIdAndLogType(memberId, gameMapId, "STAGECLEAR");
    }

    public List<PlayerLog> getStageClearLog(Long id, String stage) {
        return playerLogRepository.findByUserIdAndGameMapStageAndLogTypeAndDetailInt(id, stage, "STAGECLEAR", 1);
    }

    public Optional<PlayerLog> getHighestLog(Long id) {
        return playerLogRepository.findFirstByUserIdAndDetailIntOrderByGameMapIdDesc(id, 1);
    }

    public void setFirstGame(Member member) {
        GameMap firstGame = gameMapService.findGameMapById(1L).get();

        createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                firstGame.getId(), firstGame.getStage(), firstGame.getStep(), firstGame.getDifficulty(), firstGame.getLevel(),
                "", 0);
    }


    @Transactional
    public void batchPlayLog(Member member, GameMapDto gameMapDto, String result) {

        PlayerLog currentClearGameLog = playerLogRepository.findByUserIdAndGameMapIdAndLogTypeAndDetailInt(member.getId(), gameMapDto.id(), "STAGECLEAR", 1).orElse(null);
        PlayerLog currentGameLog = playerLogRepository.findByUserIdAndGameMapIdAndLogType(member.getId(), gameMapDto.id(), "STAGECLEAR").orElse(null);

        if(result.equals("clear")) {
            if(gameMapDto.level() == 3) {
                if(currentClearGameLog == null) {
                    currentGameLog.setDetailInt(1);

                    if (gameMapDto.difficulty().equals("Easy")) {
                        if(!gameMapDto.step().equals("1-3")) {
                            GameMap nextStepGame = gameMapService.findGameMapById(gameMapDto.id() + 7).get();
                            GameMap nextDifficultyGame = gameMapService.findGameMapById(gameMapDto.id() + 1).get();

                            if(findByUserIdAndGameMapId(member.getId(), nextStepGame.getId()).isEmpty()) {
                                createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                                        nextStepGame.getId(), nextStepGame.getStage(), nextStepGame.getStep(), nextStepGame.getDifficulty(), nextStepGame.getLevel(),
                                        "", 0);
                            }

                            if(findByUserIdAndGameMapId(member.getId(), nextDifficultyGame.getId()).isEmpty()) {
                                createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                                        nextDifficultyGame.getId(), nextDifficultyGame.getStage(), nextDifficultyGame.getStep(), nextDifficultyGame.getDifficulty(), nextDifficultyGame.getLevel(),
                                        "", 0);
                            }
                        }

                    } else if (gameMapDto.difficulty().equals("Normal")) {
                        GameMap nextDifficultyGame = gameMapService.findGameMapById(gameMapDto.id() + 1).get();

                        if(findByUserIdAndGameMapId(member.getId(), nextDifficultyGame.getId()).isEmpty()) {
                            createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                                    nextDifficultyGame.getId(), nextDifficultyGame.getStage(), nextDifficultyGame.getStep(), nextDifficultyGame.getDifficulty(), nextDifficultyGame.getLevel(),
                                    "", 0);
                        }
                    }
                } else {
                    currentGameLog.setDetailInt(1);
                    playerLogRepository.save(currentGameLog);
                }
            } else {
                GameMap nextLevelGame = gameMapService.findGameMapById(gameMapDto.id() + 1).get();

                if(currentGameLog != null) {
                    currentGameLog.setDetailInt(1);
                }

                if(findByUserIdAndGameMapId(member.getId(), nextLevelGame.getId()).isEmpty()) {
                    createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                            nextLevelGame.getId(), nextLevelGame.getStage(), nextLevelGame.getStep(), nextLevelGame.getDifficulty(), nextLevelGame.getLevel(),
                            "", 0);
                } else {
                    findByUserIdAndGameMapId(member.getId(), nextLevelGame.getId()).ifPresent(playerLog -> {
                        playerLog.setDetailInt(0);
                        playerLogRepository.save(playerLog);
                    });
                }
            }
        } else if (result.equals("fail")) {
            if(currentGameLog != null) {
                currentGameLog.setDetailInt(0);
            }
        }
    }

}
