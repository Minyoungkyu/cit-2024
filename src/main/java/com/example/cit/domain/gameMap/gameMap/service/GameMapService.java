package com.example.cit.domain.gameMap.gameMap.service;

import com.example.cit.domain.gameMap.gameMap.dto.GameMapDto;
import com.example.cit.domain.gameMap.gameMap.entity.GameMap;
import com.example.cit.domain.gameMap.gameMap.repository.GameMapRepository;
import com.example.cit.domain.gameMap.requireParts.entity.RequireParts;
import com.example.cit.domain.log.log.entity.PlayerLog;
import com.example.cit.domain.log.log.service.PlayerLogService;
import com.example.cit.global.exceptions.GlobalException;
import com.example.cit.global.rq.Rq;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class GameMapService {

    private final GameMapRepository gameMapRepository;
    private final PlayerLogService playerLogService;
    private final Rq rq;

    @Transactional
    public GameMap createGameMap(String stage, String step, String difficulty, int level, String editorAutoComplete, String editorMessage,
                              String clearGoal, String cocosInfo, String guideText, String guideImage, String commandGuide,
                              int rewardExp, int rewardJewel) {

        GameMap gameMap = GameMap.builder()
                .stage(stage)
                .step(step)
                .difficulty(difficulty)
                .level(level)
                .editorAutoComplete(editorAutoComplete)
                .editorMessage(editorMessage)
                .clearGoal(clearGoal)
                .cocosInfo(cocosInfo)
                .guideText(guideText)
                .guideImage(guideImage)
                .commandGuide(commandGuide)
                .rewardExp(rewardExp)
                .rewardJewel(rewardJewel)
                .rewardItem(null)
                .build();

        gameMapRepository.save(gameMap);

        return gameMap;
    }

    public Optional<GameMap> findGameMapById(Long gameMapId) {
        return gameMapRepository.findById(gameMapId);
    }

    public GameMap checkAccessAndGetGameMap(Long gameMapId) {
        playerLogService.findByUserIdAndGameMapId(rq.getMember().getId(), gameMapId)
                .orElseThrow(() -> new GlobalException("403-1", "잘못 된 접근입니다."));

        return findGameMapById(gameMapId).orElseThrow(() -> new GlobalException("404-1", "게임 맵을 찾을 수 없습니다."));
    }

    public GameMap getGameMapForTest(String gameInfo) {
        if( gameInfo.equals("tutorial1")) return gameMapRepository.findByStepAndLevel("tutorial", 1).get();
        else if (gameInfo.equals("tutorial2")) return gameMapRepository.findByStepAndLevel("tutorial", 2).get();

        String[] parts = gameInfo.split("");

        String numberPart = parts[0] + "-" + parts[1];
        String letterPart = parts[2];
        letterPart = switch (letterPart) {
            case "e" -> "Easy";
            case "n" -> "Normal";
            case "h" -> "Hard";
            default -> "";
        };
        int lastNumber = Integer.parseInt(parts[3]);

        return gameMapRepository.findByStepAndDifficultyAndLevel(numberPart, letterPart, lastNumber).get();
    }
}
