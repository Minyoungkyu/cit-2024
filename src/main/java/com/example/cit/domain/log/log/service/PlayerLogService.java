package com.example.cit.domain.log.log.service;

import com.example.cit.domain.achievement.playerAchievement.service.PlayerAchievementService;
import com.example.cit.domain.gameMap.gameMap.dto.GameMapDto;
import com.example.cit.domain.gameMap.gameMap.entity.GameMap;
import com.example.cit.domain.gameMap.gameMap.service.GameMapService;
import com.example.cit.domain.log.log.dto.PlayerLogDto;
import com.example.cit.domain.log.log.entity.PlayerLog;
import com.example.cit.domain.log.log.repository.PlayerLogRepository;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.player.player.service.PlayerService;
import com.querydsl.core.group.GroupBy;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlayerLogService {

    private final PlayerLogRepository playerLogRepository;
    private final GameMapService gameMapService;
    private final PlayerAchievementService playerAchievementService;
    private final PlayerService playerService;

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

        List<PlayerLog> playerLogList = playerLogRepository.findByUserIdAndGameMapStageAndLogTypeAndDetailIntGreaterThanEqual(id, stage, "STAGECLEAR", 1);

        Stream<Optional<PlayerLog>> additionalLogsStream = Stream.of(
                stage.equals("2") ? playerLogRepository.findByUserIdAndGameMapIdAndLogTypeAndDetailIntGreaterThanEqual(id, 30L, "STAGECLEAR", 1) : Optional.empty(),
                stage.equals("3") ? playerLogRepository.findByUserIdAndGameMapIdAndLogTypeAndDetailIntGreaterThanEqual(id, 58L, "STAGECLEAR", 1) : Optional.empty()
        );

        List<PlayerLog> additionalLogs = additionalLogsStream
                .filter(Optional::isPresent)
                .map(Optional::get)
                .toList();

        playerLogList.addAll(additionalLogs);

        return playerLogList;
    }

    public Optional<PlayerLog> getHighestLog(Long id) {
        return playerLogRepository.findFirstByUserIdAndDetailIntOrderByGameMapIdDesc(id, 1);
    }

    public long getStageClearCount(Long playerId, String difficulty) {
        return playerLogRepository.countByUserIdAndGameMapDifficultyAndLogTypeAndDetailIntGreaterThanEqual(playerId, difficulty, "STAGECLEAR", 1);
    }

    public void setFirstGame(Member member) {
        GameMap firstGame = gameMapService.findGameMapById(1L).get();

        createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                firstGame.getId(), firstGame.getStage(), firstGame.getStep(), firstGame.getDifficulty(), firstGame.getLevel(),
                "", 0);
    }

    public List<PlayerLogDto> getSwitchLog(Member member, String step, String diff) {
        return playerLogRepository.findByUserIdAndGameMapStepAndGameMapDifficulty(member.getId(), step, diff)
                .stream().map(PlayerLogDto::new).toList();
    }

    @Transactional
    public void batchPlayLogV2(Member member, GameMapDto gameMapDto, int playerScore, String result) {
        PlayerLog currentGameLog = playerLogRepository.findByUserIdAndGameMapIdAndLogType(member.getId(), gameMapDto.id(), "STAGECLEAR").orElse(null);

        if ( gameMapDto.id() == 2 || gameMapDto.id() == 30 || gameMapDto.id() == 58) {
            if (currentGameLog != null && currentGameLog.getDetailInt() == 0) {
                currentGameLog.setDetailInt(1);
                playerLogRepository.save(currentGameLog);
                makeNextGameLog(member, gameMapDto);

                playerAchievementService.checkStageClearAchievement(member, gameMapDto); // 업적달성 조회 및 추가

                // 보상 지급
                if (gameMapDto.rewardItem() != null) {
                    playerService.addRewardToPlayer(gameMapDto.rewardExp(), gameMapDto.rewardJewel(), gameMapDto.rewardItem());
                } else {
                    playerService.addRewardToPlayer(gameMapDto.rewardExp(), gameMapDto.rewardJewel());
                }
            }
        }

        if (currentGameLog != null) {
            if (currentGameLog.getDetailInt() >= 1) {

                // Todo: detailInt 갱신
                if (currentGameLog.getDetailInt() < playerScore) {
                    currentGameLog.setDetailInt(playerScore);
                    playerLogRepository.save(currentGameLog);
                }

                return;  // 이미 클리어 했던 게임

            } else if (currentGameLog.getDetailInt() == 0) {

                currentGameLog.setDetailInt(playerScore); // 게임 클리어 처리 Todo: detailInt update
                playerLogRepository.save(currentGameLog);

                // 보상 지급
                if ( gameMapDto.rewardItem() != null) playerService.addRewardToPlayer(gameMapDto.rewardExp(), gameMapDto.rewardJewel(), gameMapDto.rewardItem());
                else playerService.addRewardToPlayer(gameMapDto.rewardExp(), gameMapDto.rewardJewel());

                if (gameMapDto.level() != 3) {
                    makeNextGameLog(member, gameMapDto); // level 3 아니면 다음게임로그 생성

                } else {
                    
                    playerAchievementService.checkStageClearAchievement(member, gameMapDto); // level 3 클리어시 업적달성 조회 및 추가작업

                    switch (gameMapDto.difficulty()) {
                        case "Easy":
                            makeNextStepGameLog(member, gameMapDto);
                            makeNextGameLog(member, gameMapDto);
                            break;
                        case "Normal":
                            makeNextGameLog(member, gameMapDto);
                            break;
                        case "Hard":
                            return;
                    }
                }
            }
        } else {
            // currentGameLog 가 없는 경우. (있으면 안되는 경우)
        }
    }

    private void makeNextGameLog(Member member, GameMapDto gameMapDto) {
        GameMap nextGame = gameMapService.findGameMapById(gameMapDto.id() + 1).get();

        createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                nextGame.getId(), nextGame.getStage(), nextGame.getStep(), nextGame.getDifficulty(), nextGame.getLevel(),
                "", 0);
    }

    private void makeNextStepGameLog(Member member, GameMapDto gameMapDto) {
        if (gameMapDto.level() == 88) return;

        GameMap nextStepGame = gameMapService.findGameMapById(gameMapDto.id() + 7).get();

        createPlayerLog("STAGECLEAR", member.getUsername(), member.getId(),
                nextStepGame.getId(), nextStepGame.getStage(), nextStepGame.getStep(), nextStepGame.getDifficulty(), nextStepGame.getLevel(),
                "", 0);
    }


    @Transactional
    public void batchPlayLog(Member member, GameMapDto gameMapDto, String result) {

        PlayerLog currentClearGameLog = playerLogRepository.findByUserIdAndGameMapIdAndLogTypeAndDetailIntGreaterThanEqual(member.getId(), gameMapDto.id(), "STAGECLEAR", 1).orElse(null);
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
