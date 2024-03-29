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
import org.apache.commons.io.IOUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.*;
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
//        playerLogService.findByUserIdAndGameMapId(rq.getMember().getId(), gameMapId)
//                .orElseThrow(() -> new GlobalException("403-1", "잘못 된 접근입니다."));

        return findGameMapById(gameMapId).orElseThrow(() -> new GlobalException("404-1", "게임 맵을 찾을 수 없습니다."));
    }

    // Todo: 나중에 삭제 테스트용
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

    // Todo: 나중에 삭제 테스트용
    public String getGameMapForTest2(String gameInfo, String editorValue) throws IOException, InterruptedException {
        GameMap gameMap = getGameMapForTest(gameInfo);

        Resource resource = new ClassPathResource("python/script/logic.py");
        File tempScript = File.createTempFile("logic", ".py");
        try (InputStream is = resource.getInputStream(); OutputStream os = new FileOutputStream(tempScript)) {
            IOUtils.copy(is, os);
        }

        tempScript.deleteOnExit(); // 프로그램 종료 시 임시 파일 삭제

        ProcessBuilder processBuilder = new ProcessBuilder(
                "C:\\Users\\android-hy\\AppData\\Local\\Programs\\Python\\Python312\\python.exe",
                tempScript.getAbsolutePath(), gameMap.getCocosInfo(), editorValue);
        processBuilder.redirectErrorStream(true);
        Process process = processBuilder.start();  // 프로세스 시작

        BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));

        StringBuilder jsonOutput = new StringBuilder();
        String line;

        while ((line = reader.readLine()) != null) {
            jsonOutput.append(line);
        }

//        System.out.println("jsonOutput : " + jsonOutput);

        return jsonOutput.toString();
    }
}
