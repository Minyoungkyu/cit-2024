package com.example.cit.global.initData;

import com.example.cit.domain.gameMap.gameMap.entity.GameMap;
import com.example.cit.domain.gameMap.gameMap.repository.GameMapRepository;
import com.example.cit.domain.gameMap.gameMap.service.GameMapService;
import com.example.cit.domain.item.item.entity.Item;
import com.example.cit.domain.item.item.service.ItemService;
import com.example.cit.domain.item.itemParts.entity.ItemParts;
import com.example.cit.domain.item.itemParts.service.ItemPartsService;
import com.example.cit.domain.log.log.service.PlayerLogService;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.member.member.service.MemberService;
import com.example.cit.domain.player.inventroy.service.InventoryService;
import com.example.cit.global.app.AppConfig;
import com.example.cit.standard.util.Ut;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.ApplicationRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.core.annotation.Order;

@Profile("dev")
@Configuration
@RequiredArgsConstructor
public class Dev {

    private final MemberService memberService;
    private final GameMapService gameMapService;
    private final PlayerLogService playerLogService;
    private final ItemPartsService itemPartsService;
    private final ItemService itemService;
    private final InventoryService inventoryService;

    @Bean
    @Order(4)
    ApplicationRunner initDev() {
        return args -> {
            String backUrl = AppConfig.getSiteBackUrl();
            String cmd = "npx openapi-typescript " + backUrl + "/v3/api-docs/apiV1 -o ./front/src/lib/types/api/v1/schema.d.ts";
            Ut.cmd.runAsync(cmd);

            Member memberUser1;

            if (memberService.findByUsername("system").isEmpty()) {

                String memberSystemPassword = "1234";
                String memberAdminPassword = "1234";

                Member memberSystem = memberService.join("system", memberSystemPassword, "슈퍼관리자", "010-1234-1234", 4).getData();
                memberSystem.setRefreshToken("system");

                Member memberAdmin = memberService.join("admin", memberAdminPassword, "홍길동", "010-1234-1234", 2).getData();
                memberAdmin.setRefreshToken("admin");

                memberUser1 = memberService.join("testUser1", memberSystemPassword, "", "", 1).getData();
                memberUser1.setRefreshToken("testUser1");


                Member memberUser2 = memberService.join("testUser2", memberAdminPassword, "", "", 1).getData();
                memberUser2.setRefreshToken("testUser2");

                ItemParts itemParts1 = itemPartsService.createItemParts("신발");

                Item item1 = itemService.createItem(itemParts1, "똥신발", "hero.move(), hero.turnRight(), hero.turnLeft()", "sourcePath", 0);

                inventoryService.createInventory(memberUser1.getPlayer(), item1, false);

                GameMap gameMap1 = gameMapService.createGameMap("1", "tutorial", "", 0,
                        "hero.test0();", "테스트용 메시지입니다.0", "테스트용 목표입니다.0", "cocosInfo", "테스트용 텍스트입니다.0", "guideImage0", "테스트용 커멘드입니다.0",
                        1, 1);

                GameMap gameMap11 = gameMapService.createGameMap("1", "1-1", "Easy", 1,
                        "hero.test1();", "테스트용 메시지입니다.1", "테스트용 목표입니다.1", "cocosInfo", "테스트용 텍스트입니다.1", "guideImage1", "테스트용 커멘드입니다.1",
                        1, 1);

                GameMap gameMap12 = gameMapService.createGameMap("1", "1-1", "Easy", 2,
                        "hero.test2();", "테스트용 메시지입니다.2", "테스트용 목표입니다.2", "cocosInfo", "테스트용 텍스트입니다.2", "guideImage2", "테스트용 커멘드입니다.2",
                        1, 1);

                GameMap gameMap13 = gameMapService.createGameMap("1", "1-1", "Easy", 3,
                        "hero.test3();", "테스트용 메시지입니다.3", "테스트용 목표입니다.3", "cocosInfo", "테스트용 텍스트입니다.3", "guideImage3", "테스트용 커멘드입니다.3",
                        1, 1);

                GameMap gameMap2 = gameMapService.createGameMap("1", "1-2", "Easy", 1,
                        "hero.test4();", "테스트용 메시지입니다.4", "테스트용 목표입니다.4", "cocosInfo", "테스트용 텍스트입니다.4", "guideImage4", "테스트용 커멘드입니다.4",
                        1, 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser1.getUsername(),
                        gameMap1.getId(), gameMap1.getStage(), gameMap1.getStep(), gameMap1.getDifficulty(), gameMap1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser1.getUsername(),
                        gameMap11.getId(), gameMap11.getStage(), gameMap11.getStep(), gameMap11.getDifficulty(), gameMap11.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser1.getUsername(),
                        gameMap12.getId(), gameMap12.getStage(), gameMap12.getStep(), gameMap12.getDifficulty(), gameMap12.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser1.getUsername(),
                        gameMap13.getId(), gameMap13.getStage(), gameMap13.getStep(), gameMap13.getDifficulty(), gameMap13.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser1.getUsername(),
                        gameMap2.getId(), gameMap2.getStage(), gameMap2.getStep(), gameMap2.getDifficulty(), gameMap2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECODE", memberUser1.getUsername(),
                        gameMap1.getId(), gameMap1.getStage(), gameMap1.getStep(), gameMap1.getDifficulty(), gameMap1.getLevel(),
                        "hero.turnRight();\nhero.move();", 1);

                playerLogService.createPlayerLog("STAGECODE", memberUser1.getUsername(),
                        gameMap11.getId(), gameMap11.getStage(), gameMap11.getStep(), gameMap11.getDifficulty(), gameMap11.getLevel(),
                        "hero.turnLeft();\nhero.move();", 1);

                playerLogService.createPlayerLog("STAGECODE", memberUser1.getUsername(),
                        gameMap12.getId(), gameMap12.getStage(), gameMap12.getStep(), gameMap12.getDifficulty(), gameMap12.getLevel(),
                        "hero.turnLeft();\nhero.turnLeft();\nhero.move();", 1);

                playerLogService.createPlayerLog("STAGECODE", memberUser1.getUsername(),
                        gameMap13.getId(), gameMap13.getStage(), gameMap13.getStep(), gameMap13.getDifficulty(), gameMap13.getLevel(),
                        "hero.turnRight();\nhero.turnRight();\nhero.move();", 1);

                playerLogService.createPlayerLog("STAGECODE", memberUser1.getUsername(),
                        gameMap2.getId(), gameMap2.getStage(), gameMap2.getStep(), gameMap2.getDifficulty(), gameMap2.getLevel(),
                        "hero.turnRight();\nhero.move();", 1);



            }




        };
    }
}