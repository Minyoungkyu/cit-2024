package com.example.cit.domain.gameMap.gameMap.service;

import com.example.cit.domain.gameMap.gameMap.dto.GameMapDto;
import com.example.cit.domain.gameMap.gameMap.entity.GameMap;
import com.example.cit.domain.gameMap.gameMap.repository.GameMapRepository;
import com.example.cit.domain.gameMap.requireParts.entity.RequireParts;
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

    public Optional<GameMap> findGameMapById(Long id) {
        return gameMapRepository.findById(id);
    }

}
