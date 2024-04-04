package com.example.cit.global.initData;

import com.example.cit.domain.gameMap.gameMap.entity.GameMap;
import com.example.cit.domain.gameMap.gameMap.repository.GameMapRepository;
import com.example.cit.domain.gameMap.gameMap.service.GameMapService;
import com.example.cit.domain.gameMap.requireParts.service.RequirePartsService;
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

//@Profile("dev")
@Configuration
@RequiredArgsConstructor
public class Dev {

    private final MemberService memberService;
    private final GameMapService gameMapService;
    private final PlayerLogService playerLogService;
    private final ItemPartsService itemPartsService;
    private final ItemService itemService;
    private final InventoryService inventoryService;
    private final RequirePartsService requirePartsService;
    private final GameMapRepository gameMapRepository;

//    @Bean
//    @Order(4)
//    ApplicationRunner initDev() {
//        return args -> {
//            String backUrl = AppConfig.getSiteBackUrl();
//            String cmd = "npx openapi-typescript " + backUrl + "/v3/api-docs/apiV1 -o ./front/src/lib/types/api/v1/schema.d.ts";
//            Ut.cmd.runAsync(cmd);
//
//            Member memberUser1;
//
//            if (memberService.findByUsername("system").isEmpty()) {
//
//                String memberSystemPassword = "1234";
//                String memberAdminPassword = "1234";
//
//                Member memberSystem = memberService.join("system", memberSystemPassword, "슈퍼관리자", "010-1234-1234", 4).getData();
//                memberSystem.setRefreshToken("system");
//
//                Member memberAdmin = memberService.join("admin", memberAdminPassword, "홍길동", "010-1234-1234", 2).getData();
//                memberAdmin.setRefreshToken("admin");
//
//                memberUser1 = memberService.join("testUser1", memberSystemPassword, "", "", 1).getData();
//                memberUser1.setRefreshToken("testUser1");
//
//
//                Member memberUser2 = memberService.join("testUser2", memberAdminPassword, "", "", 1).getData();
//                memberUser2.setRefreshToken("testUser2");
//
//                ItemParts itemParts1 = itemPartsService.createItemParts("신발");
//
//                Item item1 = itemService.createItem(itemParts1, "똥신발", "정말 별로인 신발입니다.", "hero.move(), hero.turnRight(), hero.turnLeft()", "sourcePath", 0);
//                Item item2 = itemService.createItem(itemParts1, "그냥신발", "그냥 별로인 신발입니다.", "hero.move(), hero.turnRight(), hero.turnLeft()", "sourcePath", 12);
//
//                inventoryService.createInventory(memberUser1.getPlayer(), item1, false);
//                inventoryService.createInventory(memberUser1.getPlayer(), item2, false);
//
//                GameMap gameMap1 = gameMapService.createGameMap(
//                        "1", "tutorial", "0", 0,
//                        "go(),turnLeft(),turnRight()",
//                        "# 목표지점에 도달하세요.\n# 목표지점에 잘 도달하세요.\n",
//                        "목표지점에 도달하기",
//                        "stage = {\n" +
//                                "    \"stage\" : {\n" +
//                                "        \"map\" : 1,\n" +
//                                "        \"step\" : 0,\n" +
//                                "        \"diff\" : 0,\n" +
//                                "        \"level\" : 0,\n" +
//                                "        \"tile\" : [\n" +
//                                "            [0,0,0,0,0,0,0],\n" +
//                                "            [0,1,0,1,0,1,0],\n" +
//                                "            [0,0,0,0,0,0,0]\n" +
//                                "        ],\n" +
//                                "        \"bg\" : [\n" +
//                                "            [0,0,0,0,0,0,0],\n" +
//                                "            [0,0,0,0,0,0,0],\n" +
//                                "            [0,0,0,0,0,0,0]\n" +
//                                "        ],\n" +
//                                "        \"bg_extra_list\" : [\n" +
//                                "        ],\n" +
//                                "        \"goal_list\" : [\n" +
//                                "            {\"goal\": \"target\", \"pos\": [5,1]}\n" +
//                                "        ]\n" +
//                                "    },\n" +
//                                "    \"player\" : {\n" +
//                                "        \"pos\" : [1,1],\n" +
//                                "        \"dir\" : \"right\", \n" +
//                                "        \"hp\" : 100,\n" +
//                                "        \"status\" : 0,\n" +
//                                "        \"food_count\" : 0,\n" +
//                                "        \"rocket_parts_count\" : 0\n" +
//                                "    },\n" +
//                                "    \"item_list\" : [\n" +
//                                "    ]\n" +
//                                "}",
//                        "테스트용 텍스트입니다.0",
//                        "guideImage0",
//                        "테스트용 커멘드입니다.0",
//                        1,
//                        1);
//
//                GameMap gameMap11 = gameMapService.createGameMap("1", "1-1", "Easy", 1,
//                        "hero.test1();", "테스트용 메시지입니다.1", "테스트용 목표입니다.1", "cocosInfo", "테스트용 텍스트입니다.1", "guideImage1", "테스트용 커멘드입니다.1",
//                        1, 1);
//
//                GameMap gameMap12 = gameMapService.createGameMap("1", "1-1", "Easy", 2,
//                        "hero.test2();", "테스트용 메시지입니다.2", "테스트용 목표입니다.2", "cocosInfo", "테스트용 텍스트입니다.2", "guideImage2", "테스트용 커멘드입니다.2",
//                        1, 1);
//
//                GameMap gameMap13 = gameMapService.createGameMap("1", "1-1", "Easy", 3,
//                        "hero.test3();", "테스트용 메시지입니다.3", "테스트용 목표입니다.3", "cocosInfo", "테스트용 텍스트입니다.3", "guideImage3", "테스트용 커멘드입니다.3",
//                        1, 1);
//
//                GameMap gameMap2 = gameMapService.createGameMap("1", "1-2", "Easy", 1,
//                        "hero.test4();", "테스트용 메시지입니다.4", "테스트용 목표입니다.4", "cocosInfo", "테스트용 텍스트입니다.4", "guideImage4", "테스트용 커멘드입니다.4",
//                        1, 1);
//
//                GameMap gameMap21 = gameMapService.createGameMap("1", "1-2", "Easy", 2,
//                        "hero.test5();", "테스트용 메시지입니다.5", "테스트용 목표입니다.5", "cocosInfo", "테스트용 텍스트입니다.5", "guideImage5", "테스트용 커멘드입니다.5",
//                        1, 1);
//
//                requirePartsService.addRequireParts(gameMap2, itemParts1);
//                requirePartsService.addRequireParts(gameMap21, itemParts1);
//
//
//                playerLogService.createPlayerLog("STAGECLEAR", memberUser1.getUsername(), memberUser1.getId(),
//                        gameMap1.getId(), gameMap1.getStage(), gameMap1.getStep(), gameMap1.getDifficulty(), gameMap1.getLevel(),
//                        "", 1);
//
//                playerLogService.createPlayerLog("STAGECLEAR", memberUser1.getUsername(), memberUser1.getId(),
//                        gameMap11.getId(), gameMap11.getStage(), gameMap11.getStep(), gameMap11.getDifficulty(), gameMap11.getLevel(),
//                        "", 1);
//
//                playerLogService.createPlayerLog("STAGECLEAR", memberUser1.getUsername(), memberUser1.getId(),
//                        gameMap12.getId(), gameMap12.getStage(), gameMap12.getStep(), gameMap12.getDifficulty(), gameMap12.getLevel(),
//                        "", 1);
//
//                playerLogService.createPlayerLog("STAGECLEAR", memberUser1.getUsername(), memberUser1.getId(),
//                        gameMap13.getId(), gameMap13.getStage(), gameMap13.getStep(), gameMap13.getDifficulty(), gameMap13.getLevel(),
//                        "", 1);
//
//            }
//
//        };
//
//    }

    @Bean
    @Order(5)
    ApplicationRunner initStage() {
        return args -> {
            if (memberService.findByUsername("hadle").isEmpty()) {
                GameMap gameMapTutorial1 = gameMapService.createGameMap(
                        "1", "tutorial", "0", 1,
                        "go(),turnLeft(),turnRight()",
                        "# 이동 명령문 go()를 순차적으로 작성하여 목표지점에 도달하세요.\n" +
                                "\n" +
                                "go()\n",
                        "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"tutorial\",\n" +
                                "        \"diff\" : 0,\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [5,1]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,1],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}\n",
                        "이동방법을 배웁니다.\n" +
                                "\n" +
                                "go() 함수를 사용하면 플레이어가 바라보고 있는 방향으로 이동합니다.",
                        "go()",
                        "go(),turnLeft(),turnRight()",
                        1,
                        1);

                GameMap gameMapTutorial2 = gameMapService.createGameMap(
                        "1", "tutorial", "0", 2,
                        "go(),turnLeft(),turnRight()",
                        "# 이동 명령문 go()와 회전 명령문 turnRight()를 순차적으로 작성하여 목표 지점에 도달하세요.\n",
                        "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"tutorial\",\n" +
                                "        \"diff\" : 0,\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0],\n" +
                                "            [0,1,0,1,0],\n" +
                                "            [0,0,0,0,0],\n" +
                                "            [0,1,0,1,0],\n" +
                                "            [0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [3,3]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,1],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "플레이어 방향 설정을 배웁니다.\n" +
                                "\n" +
                                "\n" +
                                "turnLeft() 함수를 사용하면 플레이어가 왼쪽방향으로 틀게됩니다.\n" +
                                "\n" +
                                "turnRight() 함수를 사용하게 되면 플레이어가 오른쪽 방향으로 돌게됩니다.",
                        "go()\n" +
                                "turnLeft()\n" +
                                "turnRight()",
                        "go(),turnLeft(),turnRight()",
                        1,
                        1);

                GameMap gameMap11e1 = gameMapService.createGameMap(
                        "1", "1-1", "Easy", 1,
                        "go(),turnLeft(),turnRight()",
                        "# 이동 명령문 go()와 회전 명령문 turnLeft(), turnRight()를 순차적으로 작성하여 목표 지점에 도달하세요.\n",
                        "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-1\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [7,5]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,1],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "로켓발사장으로 이동 -1\n" +
                                "\n" +
                                "튜토리얼에서 배운내용을 활용하여\n" +
                                "\n" +
                                "목표 지점에 도달해보세요.\n" +
                                "\n" +
                                "go() 함수와 turnLeft() , turnRight()함수를 이용해 빠른 경로를 찾아보세요.",
                        "go()\n" +
                                "turnLeft()\n" +
                                "turnRight()",
                        "go(),turnLeft(),turnRight()",
                        1,
                        1);

                GameMap gameMap11e2 = gameMapService.createGameMap(
                        "1", "1-1", "Easy", 2,
                        "go(),turnLeft(),turnRight()",
                        "# 더 멀리 이동하기 위해  다음과 같이 이동 명령문의 괄호 안에 숫자를 넣어보세요.\n" +
                                "# go(2)\n",
                        "목표지점에 도달하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-1\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [2,2,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [9,1]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "로켓 발사장으로 이동 -2\n" +
                                "\n" +
                                "인수를 넣어 더멀리 쉽게 이동할수 있습니다!\n" +
                                "\n" +
                                "go(2) 를 넣어 목표지점에 더욱 쉽게 이동해보세요!",
                        "go에 인수 사용\n" +
                                "\n" +
                                "go()\n" +
                                "turnLeft()\n" +
                                "turnRight()",
                        "go(),turnLeft(),turnRight()",
                        1,
                        1);

                GameMap gameMap11e3 = gameMapService.createGameMap(
                        "1", "1-1", "Easy", 3,
                        "go(),turnLeft(),turnRight()",
                        "# 바닥에 있는 폭탄을 피해서 보급품을 획득하세요.\n",
                        "목표지점에 도달하기\n" +
                                "보급품 3개 획득하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-1\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"food\", \"pos\": [3,1], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"food\", \"pos\": [7,1], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"food\", \"pos\": [9,3], \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"food\", \"pos\": [3,7], \"status\": 1},\n" +
                                "            {\"id\":4, \"type\": \"bomb\", \"pos\": [3,2], \"status\": 1},\n" +
                                "            {\"id\":5, \"type\": \"bomb\", \"pos\": [8,3], \"status\": 1},\n" +
                                "            {\"id\":6, \"type\": \"bomb\", \"pos\": [6,5], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [9,7]},\n" +
                                "            {\"goal\": \"item\", \"type\": \"food\", \"count\": 3}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,1],\n" +
                                "        \"dir\" : \"down\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "        {\"status\": 1},\n" +
                                "        {\"status\": 1},\n" +
                                "        {\"status\": 1},\n" +
                                "        {\"status\": 1},\n" +
                                "        {\"status\": 1},\n" +
                                "        {\"status\": 1},\n" +
                                "        {\"status\": 1}\n" +
                                "    ]\n" +
                                "}",
                        "\t\n" +
                                "발사장앞\n" +
                                "\n" +
                                "로켓 발사장까지 거의 다왔습니다.\n" +
                                "발사장으로 이동하기전 보급품을 충분히 적재해야합니다.\n" +
                                "\n" +
                                "폭탄을 피하고 목표에서 요구하는 필요한 보급품을 획득하세요.",
                        "for 문 사용\n" +
                                "go()\n" +
                                "turnLeft()\n" +
                                "turnRight()",
                        "go(),turnLeft(),turnRight()",
                        1,
                        1);

                GameMap gameMap11n1 = gameMapService.createGameMap(
                        "1", "1-1", "Normal", 1,
                        "go(),turnLeft(),turnRight()",
                        "",
                        "목표지점에 도달하기\n" +
                                "보급품 2개 획득하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-1\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,2,0,0,0,0,0,0],\n" +
                                "            [0,1,2,1,0,1,0,1,0],\n" +
                                "            [0,0,2,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"food\", \"pos\": [3,1], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"food\", \"pos\": [5,3], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [7,5]},\n" +
                                "            {\"goal\": \"item\", \"type\": \"food\", \"count\": 2}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,1],\n" +
                                "        \"dir\" : \"down\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "\t\n" +
                                "로켓발사장으로 이동 -1\n" +
                                "\n" +
                                "튜토리얼에서 배운내용을 활용하여\n" +
                                "\n" +
                                "목표 지점에 도달해보세요.\n" +
                                "\n" +
                                "go() 함수와 turnLeft() , turnRight()함수를 이용해 빠른 경로를 찾아보세요.",
                        "go()\n" +
                                "turnLeft()\n" +
                                "turnRight()",
                        "go(),turnLeft(),turnRight()",
                        1,
                        1);

                GameMap gameMap11n2 = gameMapService.createGameMap(
                        "1", "1-1", "Normal", 2,
                        "go(),turnLeft(),turnRight()",
                        "",
                        "목표지점에 도달하기\n" +
                                "보급품 2개 획득하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-1\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [2,2,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"food\", \"pos\": [1,1], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"food\", \"pos\": [5,3], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"food\", \"pos\": [7,3], \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"bomb\", \"pos\": [4,3], \"status\": 1},\n" +
                                "            {\"id\":4, \"type\": \"bomb\", \"pos\": [6,3], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [9,1]}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,       \n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "로켓 발사장으로 이동 -2\n" +
                                "\n" +
                                "인수를 넣어 더멀리 쉽게 이동할수 있습니다!\n" +
                                "\n" +
                                "go(2) 를 넣어 목표지점에 더욱 쉽게 이동해보세요!",
                        "go에 인수 사용\n" +
                                "\n" +
                                "go()\n" +
                                "turnLeft()\n" +
                                "turnRight()",
                        "go(),turnLeft(),turnRight()",
                        1,
                        1);

                GameMap gameMap11n3 = gameMapService.createGameMap(
                        "1", "1-1", "Normal", 3,
                        "go(),turnLeft(),turnRight()",
                        "# 스위치를 밟으면 레이저가 켜지거나 꺼집니다. 레이저에 닿지 않게 조심하세요.\n",
                        "목표지점에 도달하기\n" +
                                "보급품 3개 획득하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-1\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,2,1,0,1,0,1,0,1,2],\n" +
                                "            [0,0,2,0,0,0,0,0,0,0,2],\n" +
                                "            [0,1,2,1,0,1,0,1,0,1,2],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"food\", \"pos\": [1,7], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"food\", \"pos\": [3,1], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"food\", \"pos\": [5,1], \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"food\", \"pos\": [9,3], \"status\": 1},\n" +
                                "            {\"id\":4, \"type\": \"bomb\", \"pos\": [1,6], \"status\": 1},\n" +
                                "            {\"id\":5, \"type\": \"bomb\", \"pos\": [6,1], \"status\": 1},\n" +
                                "            {\"id\":6, \"type\": \"bomb\", \"pos\": [6,5], \"status\": 1},\n" +
                                "            {\"id\":7, \"type\": \"bomb\", \"pos\": [8,5], \"status\": 1},\n" +
                                "            {\"id\":8, \"type\": \"bomb\", \"pos\": [9,4], \"status\": 1},\n" +
                                "            {\"id\":9, \"type\": \"laser_switch\", \"pos\": [1,5], \"laser_id\": [10], \"status\": 1},\n" +
                                "            {\"id\":10, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [3,3], \"pos_end\": [9,3], \"status\": 0}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [9,7]},\n" +
                                "            {\"goal\": \"item\", \"type\": \"food\", \"count\": 3}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,1],\n" +
                                "        \"dir\" : \"down\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "발사장앞\n" +
                                "\n" +
                                "로켓 발사장까지 거의 다왔습니다.\n" +
                                "발사장으로 이동하기전 보급품을 충분히 적재해야합니다.\n" +
                                "\n" +
                                "폭탄을 피하고 목표에서 요구하는 필요한 보급품을 획득하세요.",
                        "for 문 사용\n" +
                                "go()\n" +
                                "turnLeft()\n" +
                                "turnRight()",
                        "go(),turnLeft(),turnRight()",
                        1,
                        1);

                GameMap gameMap11h1 = gameMapService.createGameMap(
                        "1", "1-1", "Hard", 1,
                        "go(),turnLeft(),turnRight()",
                        "",
                        "목표지점에 도달하기\n" +
                                "보급품 2개 획득하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-1\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,2,0,0,0,0,0,0],\n" +
                                "            [0,1,2,1,0,1,0,1,0],\n" +
                                "            [0,0,2,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"food\", \"pos\": [3,1], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"food\", \"pos\": [1,5], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"bomb\", \"pos\": [3,2], \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"bomb\", \"pos\": [5,2], \"status\": 1},\n" +
                                "            {\"id\":4, \"type\": \"bomb\", \"pos\": [5,4], \"status\": 1},\n" +
                                "            {\"id\":5, \"type\": \"bomb\", \"pos\": [7,4], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [7,5]},\n" +
                                "            {\"goal\": \"item\", \"type\": \"food\", \"count\": 2}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,1],\n" +
                                "        \"dir\" : \"down\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "로켓발사장으로 이동 -1\n" +
                                "\n" +
                                "튜토리얼에서 배운내용을 활용하여\n" +
                                "\n" +
                                "목표 지점에 도달해보세요.\n" +
                                "\n" +
                                "go() 함수와 turnLeft() , turnRight()함수를 이용해 빠른 경로를 찾아보세요.",
                        "go()\n" +
                                "turnLeft()\n" +
                                "turnRight()",
                        "go(),turnLeft(),turnRight()",
                        1,
                        1);

                GameMap gameMap11h2 = gameMapService.createGameMap(
                        "1", "1-1", "Hard", 2,
                        "go(),turnLeft(),turnRight()",
                        "",
                        "목표지점에 도달하기\n" +
                                "보급품 3개 획득하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-1\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,2,2,2,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,2,2,2,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"food\", \"pos\": [7,1], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"food\", \"pos\": [5,3], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"food\", \"pos\": [9,3], \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"food\", \"pos\": [9,5], \"status\": 1},\n" +
                                "            {\"id\":4, \"type\": \"laser_switch\", \"pos\": [3,5], \"laser_id\": [5], \"status\": 1},\n" +
                                "            {\"id\":5, \"type\": \"laser\", \"dir\": \"v\", \"pos_start\": [3,0], \"pos_end\": [3,3], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [9,1]},\n" +
                                "            {\"goal\": \"item\", \"type\": \"food\", \"count\": 3}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "로켓 발사장으로 이동 -2\n" +
                                "\n" +
                                "인수를 넣어 더멀리 쉽게 이동할수 있습니다!\n" +
                                "\n" +
                                "go(2) 를 넣어 목표지점에 더욱 쉽게 이동해보세요!",
                        "go에 인수 사용\n" +
                                "\n" +
                                "go()\n" +
                                "turnLeft()\n" +
                                "turnRight()",
                        "go(),turnLeft(),turnRight()",
                        1,
                        1);

                GameMap gameMap11h3 = gameMapService.createGameMap(
                        "1", "1-1", "Hard", 3,
                        "go(),turnLeft(),turnRight()",
                        "",
                        "목표지점에 도달하기\n" +
                                "보급품 4개 획득하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-1\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [2,0,0,0,0,0,0,0,0,0,2],\n" +
                                "            [2,1,0,1,0,1,0,1,0,1,2],\n" +
                                "            [2,0,0,0,2,0,2,0,0,0,2],\n" +
                                "            [2,1,0,1,2,1,2,1,0,1,2],\n" +
                                "            [2,0,0,0,2,0,2,0,0,0,2],\n" +
                                "            [2,1,0,1,2,1,2,1,0,1,2],\n" +
                                "            [2,0,0,0,2,0,2,0,0,0,2],\n" +
                                "            [2,1,0,1,2,1,2,1,0,1,2],\n" +
                                "            [2,0,0,0,2,0,2,0,0,0,2]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"food\", \"pos\": [1,7], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"food\", \"pos\": [3,7], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"food\", \"pos\": [9,1], \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"food\", \"pos\": [9,7], \"status\": 1},\n" +
                                "            {\"id\":4, \"type\": \"laser_switch\", \"pos\": [5,3], \"laser_id\": [5,6], \"status\": 1},\n" +
                                "            {\"id\":5, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [1,5], \"pos_end\": [3,5], \"status\": 1},\n" +
                                "            {\"id\":6, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [7,3], \"pos_end\": [9,3], \"status\": 0},\n" +
                                "            {\"id\":7, \"type\": \"bomb\", \"pos\": [8,1], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [5,7]},\n" +
                                "            {\"goal\": \"item\", \"type\": \"food\", \"count\": 4}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,1],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "발사장앞\n" +
                                "\n" +
                                "로켓 발사장까지 거의 다왔습니다.\n" +
                                "발사장으로 이동하기전 보급품을 충분히 적재해야합니다.\n" +
                                "\n" +
                                "폭탄을 피하고 목표에서 요구하는 필요한 보급품을 획득하세요.",
                        "for 문 사용\n" +
                                "go()\n" +
                                "turnLeft()\n" +
                                "turnRight()",
                        "go(),turnLeft(),turnRight()",
                        1,
                        1);

                GameMap gameMap12e1 = gameMapService.createGameMap(
                        "1", "1-2", "Easy", 1,
                        "go(),turnLeft(),turnRight(),for i in range():",
                        "# for i in range(3): 명령어는 여러줄의 코드블록을 괄호안의 숫자만큼 반복합니다.\n" +
                                "# 탭을 사용하여 for 아래의 이동 명령문을 들여 쓰세요.\n",
                        "목표지점에 도달하기\n" +
                                "로켓부품 2개 획득하기\n" +
                                "코드 5줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-2\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"rocket_parts\", \"pos\": [5,7], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"rocket_parts\", \"pos\": [9,5], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [13,3]},\n" +
                                "            {\"goal\": \"item\", \"type\": \"rocket_parts\", \"count\": 2},\n" +
                                "            {\"goal\": \"line\", \"count\": 5}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,9],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "부품회수\n" +
                                "\n" +
                                "로켓을 만들어야합니다.\n" +
                                "로켓을 만들기위해 로켓 부품을 획득해 목표지점으로 이동하세요.",
                        "for 문 사용\n" +
                                "go()\n" +
                                "turnLeft()\n" +
                                "turnRight()",
                        "go(),turnLeft(),turnRight(),for i in range(3):",
                        1,
                        1);

                GameMap gameMap12e2 = gameMapService.createGameMap(
                        "1", "1-2", "Easy", 2,
                        "go(),turnLeft(),turnRight(),for i in range():",
                        "# for i in range(3): 명령어는 여러 줄의 코드 블록을 괄호 안의 숫자만큼 반복합니다.\n" +
                                "# 탭을 사용하여 for 아래의 이동 명령문을 들여 쓰세요.\n" +
                                "# 위의 스위치는 위쪽 방과 연결되어있고 아래의 스위치는 아래쪽 방들과 연결되어있습니다.\n",
                        "로켓부품 6개 획득하기\n" +
                                "코드 40줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-2\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "        {\"id\":0, \"type\": \"rocket_parts\", \"pos\": [7,1], \"status\": 1},\n" +
                                "        {\"id\":1, \"type\": \"rocket_parts\", \"pos\": [13,1], \"status\": 1},\n" +
                                "        {\"id\":2, \"type\": \"rocket_parts\", \"pos\": [19,1], \"status\": 1},\n" +
                                "        {\"id\":3, \"type\": \"rocket_parts\", \"pos\": [9,9], \"status\": 1},\n" +
                                "        {\"id\":4, \"type\": \"rocket_parts\", \"pos\": [15,9], \"status\": 1},\n" +
                                "        {\"id\":5, \"type\": \"rocket_parts\", \"pos\": [21,9], \"status\": 1},\n" +
                                "        {\"id\":6, \"type\": \"laser_switch\", \"pos\": [3,3], \"laser_id\": [10,11,12], \"status\": 1},\n" +
                                "        {\"id\":7, \"type\": \"laser_switch\", \"pos\": [3,7], \"laser_id\": [13,14,15], \"status\": 1},\n" +
                                "        {\"id\":8, \"type\": \"laser_switch\", \"pos\": [23,3], \"laser_id\": [10,11,12], \"status\": 1},\n" +
                                "        {\"id\":9, \"type\": \"laser_switch\", \"pos\": [23,7], \"laser_id\": [13,14,15], \"status\": 1},\n" +
                                "        {\"id\":10, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [6,4], \"pos_end\": [8,4], \"status\": 1},\n" +
                                "        {\"id\":11, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [12,4], \"pos_end\": [14,4], \"status\": 1},\n" +
                                "        {\"id\":12, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [18,4], \"pos_end\": [20,4], \"status\": 1},\n" +
                                "        {\"id\":13, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [6,6], \"pos_end\": [8,6], \"status\": 1},\n" +
                                "        {\"id\":14, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [12,6], \"pos_end\": [14,6], \"status\": 1},\n" +
                                "        {\"id\":15, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [18,6], \"pos_end\": [20,6], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"item\", \"type\": \"rocket_parts\", \"count\": 6},\n" +
                                "            {\"goal\": \"line\", \"count\": 40}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "레이저 주의-1\n" +
                                "\n" +
                                "앞에 보이는 레이저를 조심하세요!!\n" +
                                "레이저는  레이저 스위치를 동작시켜 레이저를 꺼야합니다!\n" +
                                "\n" +
                                "절대 레이저가 켜져있는 상태로 접근하지 마세요 \n" +
                                "\n" +
                                "\n" +
                                "레이저스위치를 작동시켜\n" +
                                "로켓을 만들기위해 필요한 로켓 부품을 획득하세요!",
                        "이중 for\n" +
                                "go()\n" +
                                "turnLeft()\n" +
                                "turnRight()\n" +
                                "최소 코드작성",
                        "go(),turnLeft(),turnRight(),for i in range(3):",
                        1,
                        1);

                GameMap gameMap12e3 = gameMapService.createGameMap(
                        "1", "1-2", "Easy", 3,
                        "go(),turnLeft(),turnRight(),for i in range():",
                        "# for i in range(3): 명령어는 여러 줄의 코드 블록을 괄호 안의 숫자만큼 반복합니다.\n" +
                                "# 탭을 사용하여 for 아래의 이동 명령문을 들여 쓰세요.\n" +
                                "# 왼쪽 스위치는 왼쪽 방 4개와 연결되어있고 오른쪽 스위치는 오른쪽 방 4개와 연결되어있습니다.\n",
                        "로켓부품 5개 획득하기\n" +
                                "코드 8줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-2\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"rocket_parts\", \"pos\": [7,1], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"rocket_parts\", \"pos\": [13,1], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"rocket_parts\", \"pos\": [21,1], \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"rocket_parts\", \"pos\": [27,1], \"status\": 1},\n" +
                                "            {\"id\":4, \"type\": \"rocket_parts\", \"pos\": [7,9], \"status\": 1},\n" +
                                "            {\"id\":5, \"type\": \"rocket_parts\", \"pos\": [13,9], \"status\": 1},\n" +
                                "            {\"id\":6, \"type\": \"rocket_parts\", \"pos\": [17,9], \"status\": 1},\n" +
                                "            {\"id\":7, \"type\": \"rocket_parts\", \"pos\": [23,9], \"status\": 1},\n" +
                                "            {\"id\":8, \"type\": \"laser_switch\", \"pos\": [3,3], \"laser_id\": [12,13,16,17], \"status\": 1},\n" +
                                "            {\"id\":9, \"type\": \"laser_switch\", \"pos\": [3,7], \"laser_id\": [12,13,16,17], \"status\": 1},\n" +
                                "            {\"id\":10, \"type\": \"laser_switch\", \"pos\": [29,3], \"laser_id\": [14,15,18,19], \"status\": 1},\n" +
                                "            {\"id\":11, \"type\": \"laser_switch\", \"pos\": [29,7], \"laser_id\": [14,15,18,19], \"status\": 1},\n" +
                                "            {\"id\":12, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [6,4], \"pos_end\": [8,4], \"status\": 1},\n" +
                                "            {\"id\":13, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [12,4], \"pos_end\": [14,4], \"status\": 1},\n" +
                                "            {\"id\":14, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [18,4], \"pos_end\": [20,4], \"status\": 1},\n" +
                                "            {\"id\":15, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [24,4], \"pos_end\": [26,4], \"status\": 1},\n" +
                                "            {\"id\":16, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [6,6], \"pos_end\": [8,6], \"status\": 1},\n" +
                                "            {\"id\":17, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [12,6], \"pos_end\": [14,6], \"status\": 1},\n" +
                                "            {\"id\":18, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [18,6], \"pos_end\": [20,6], \"status\": 1},\n" +
                                "            {\"id\":19, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [24,6], \"pos_end\": [26,6], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"item\", \"type\": \"rocket_parts\", \"count\": 8},\n" +
                                "            {\"goal\": \"line\", \"count\": 60}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "레이저 주의-2\n" +
                                "\n" +
                                "레이저 스위치를 동작시켜 레이저를 꺼야합니다!\n" +
                                "\n" +
                                "절대 레이저가 켜져있는 상태로 접근하지 마세요 \n" +
                                "\n" +
                                "\n" +
                                "레이저스위치를 작동시켜\n" +
                                "로켓을 만들기위해 필요한 로켓 부품을 획득하세요!",
                        "이중 for\n" +
                                "go()\n" +
                                "turnLeft()\n" +
                                "turnRight()",
                        "go(),turnLeft(),turnRight(),for i in range(3):",
                        1,
                        1);

                GameMap gameMap12n1 = gameMapService.createGameMap(
                        "1", "1-2", "Normal", 1,
                        "go(),turnLeft(),turnRight(),for i in range():",
                        "# for i in range(3): 명령어는 여러줄의 코드블록을 괄호안의 숫자만큼 반복합니다.\n" +
                                "# 탭을 사용하여 for 아래의 이동 명령문을 들여 쓰세요.\n",
                        "목표지점에 도달하기\n" +
                                "로켓부품 5개 획득하기\n" +
                                "코드 8줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-2\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"rocket_parts\", \"pos\": [1,5], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"rocket_parts\", \"pos\": [5,3], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"rocket_parts\", \"pos\": [5,7], \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"rocket_parts\", \"pos\": [5,11], \"status\": 1},\n" +
                                "            {\"id\":4, \"type\": \"rocket_parts\", \"pos\": [9,1], \"status\": 1},\n" +
                                "            {\"id\":5, \"type\": \"rocket_parts\", \"pos\": [9,5], \"status\": 1},\n" +
                                "            {\"id\":6, \"type\": \"rocket_parts\", \"pos\": [9,9], \"status\": 1},\n" +
                                "            {\"id\":7, \"type\": \"rocket_parts\", \"pos\": [13,7], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"target\", \"pos\": [13,3]},\n" +
                                "            {\"goal\": \"item\", \"type\": \"rocket_parts\", \"count\": 5},\n" +
                                "            {\"goal\": \"line\", \"count\": 8}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,9],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "부품회수\n" +
                                "\n" +
                                "로켓을 만들어야합니다.\n" +
                                "로켓을 만들기위해 로켓 부품을 획득해 목표지점으로 이동하세요.",
                        "for 문 사용\n" +
                                "go()\n" +
                                "turnLeft()\n" +
                                "turnRight()\n",
                        "go(),turnLeft(),turnRight(),for i in range(3):",
                        1,
                        1);

                GameMap gameMap12n2 = gameMapService.createGameMap(
                        "1", "1-2", "Normal", 2,
                        "go(),turnLeft(),turnRight(),for i in range():",
                        "# for i in range(3): 명령어는 여러 줄의 코드 블록을 괄호 안의 숫자만큼 반복합니다.\n" +
                                "# 탭을 사용하여 for 아래의 이동 명령문을 들여 쓰세요.\n" +
                                "# 위의 스위치는 위쪽 방과 연결되어있고 아래의 스위치는 아래쪽 방들과 연결되어있습니다.\n" +
                                "# 각 방의 스위치를 밟으면 로켓 부품이 노란색 마커 위에 떨어집니다.\n",
                        "로켓부품 6개 획득하기\n" +
                                "코드 60줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-2\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"laser_switch\", \"pos\": [3,3], \"laser_id\": [4,5,6], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"laser_switch\", \"pos\": [3,7], \"laser_id\": [7,8,9], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"laser_switch\", \"pos\": [23,3], \"laser_id\": [4,5,6], \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"laser_switch\", \"pos\": [23,7], \"laser_id\": [7,8,9], \"status\": 1},\n" +
                                "            {\"id\":4, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [6,4], \"pos_end\": [8,4], \"status\": 1},\n" +
                                "            {\"id\":5, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [12,4], \"pos_end\": [14,4], \"status\": 1},\n" +
                                "            {\"id\":6, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [18,4], \"pos_end\": [20,4], \"status\": 1},\n" +
                                "            {\"id\":7, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [6,6], \"pos_end\": [8,6], \"status\": 1},\n" +
                                "            {\"id\":8, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [12,6], \"pos_end\": [14,6], \"status\": 1},\n" +
                                "            {\"id\":9, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [18,6], \"pos_end\": [20,6], \"status\": 1},\n" +
                                "            {\"id\":10, \"type\": \"drop_switch\", \"pos\": [9,3], \"pos_drop\": [7,1], \"count\": 1, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":11, \"type\": \"drop_switch\", \"pos\": [15,3], \"pos_drop\": [15,1], \"count\": 1, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":12, \"type\": \"drop_switch\", \"pos\": [21,3], \"pos_drop\": [17,1], \"count\": 1, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":13, \"type\": \"drop_switch\", \"pos\": [5,9], \"pos_drop\": [9,7], \"count\": 1, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":14, \"type\": \"drop_switch\", \"pos\": [11,9], \"pos_drop\": [13,9], \"count\": 1, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":15, \"type\": \"drop_switch\", \"pos\": [17,9], \"pos_drop\": [21,9], \"count\": 1, \"drop_type\": \"rocket_parts\", \"status\": 3}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"item\", \"type\": \"rocket_parts\", \"count\": 6},\n" +
                                "            {\"goal\": \"line\", \"count\": 60}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "레이저 주의-1\n" +
                                "\n" +
                                "앞에 보이는 레이저를 조심하세요!!\n" +
                                "레이저는  레이저 스위치를 동작시켜 레이저를 꺼야합니다!\n" +
                                "\n" +
                                "절대 레이저가 켜져있는 상태로 접근하지 마세요 \n" +
                                "\n" +
                                "\n" +
                                "레이저스위치를 작동시켜\n" +
                                "로켓을 만들기위해 필요한 로켓 부품을 획득하세요!",
                        "이중 for\n" +
                                "go()\n" +
                                "turnLeft()\n" +
                                "turnRight()\n" +
                                "최소 코드작성",
                        "go(),turnLeft(),turnRight(),for i in range(3):",
                        1,
                        1);

                GameMap gameMap12n3 = gameMapService.createGameMap(
                        "1", "1-2", "Normal", 3,
                        "go(),turnLeft(),turnRight(),for i in range():",
                        "# for i in range(3): 명령어는 여러 줄의 코드 블록을 괄호 안의 숫자만큼 반복합니다.\n" +
                                "# 탭을 사용하여 for 아래의 이동 명령문을 들여 쓰세요.\n" +
                                "# 왼쪽 스위치는 왼쪽 방 4개와 연결되어있고 오른쪽 스위치는 오른쪽 방 4개와 연결되어있습니다.\n" +
                                "# 스위치가 있는 방은 스위치를 밟으면 로켓 부품이 노란색 마커 위에 떨어집니다.",
                        "로켓부품 8개 획득하기\n" +
                                "코드 100줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-2\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"laser_switch\", \"pos\": [3,3], \"laser_id\": [4,5,8,9], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"laser_switch\", \"pos\": [3,7], \"laser_id\": [4,5,8,9], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"laser_switch\", \"pos\": [29,3], \"laser_id\": [6,7,10,11], \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"laser_switch\", \"pos\": [29,7], \"laser_id\": [6,7,10,11], \"status\": 1},\n" +
                                "            {\"id\":4, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [6,4], \"pos_end\": [8,4], \"status\": 1},\n" +
                                "            {\"id\":5, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [12,4], \"pos_end\": [14,4], \"status\": 1},\n" +
                                "            {\"id\":6, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [18,4], \"pos_end\": [20,4], \"status\": 1},\n" +
                                "            {\"id\":7, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [24,4], \"pos_end\": [26,4], \"status\": 1},\n" +
                                "            {\"id\":8, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [6,6], \"pos_end\": [8,6], \"status\": 1},\n" +
                                "            {\"id\":9, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [12,6], \"pos_end\": [14,6], \"status\": 1},\n" +
                                "            {\"id\":10, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [18,6], \"pos_end\": [20,6], \"status\": 1},\n" +
                                "            {\"id\":11, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [24,6], \"pos_end\": [26,6], \"status\": 1},\n" +
                                "            {\"id\":12, \"type\": \"rocket_parts\", \"pos\": [13,1], \"status\": 1},\n" +
                                "            {\"id\":13, \"type\": \"rocket_parts\", \"pos\": [21,1], \"status\": 1},\n" +
                                "            {\"id\":14, \"type\": \"rocket_parts\", \"pos\": [13,9], \"status\": 1},\n" +
                                "            {\"id\":15, \"type\": \"rocket_parts\", \"pos\": [17,9], \"status\": 1},\n" +
                                "            {\"id\":16, \"type\": \"drop_switch\", \"pos\": [9,3], \"pos_drop\": [5,1], \"count\": 1, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":17, \"type\": \"drop_switch\", \"pos\": [25,3], \"pos_drop\": [27,1], \"count\": 1, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":18, \"type\": \"drop_switch\", \"pos\": [9,9], \"pos_drop\": [5,9], \"count\": 1, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":19, \"type\": \"drop_switch\", \"pos\": [23,7], \"pos_drop\": [25,7], \"count\": 1, \"drop_type\": \"rocket_parts\", \"status\": 3}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"item\", \"type\": \"rocket_parts\", \"count\": 8},\n" +
                                "            {\"goal\": \"line\", \"count\": 100}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "레이저 주의-2\n" +
                                "\n" +
                                "레이저 스위치를 동작시켜 레이저를 꺼야합니다!\n" +
                                "\n" +
                                "절대 레이저가 켜져있는 상태로 접근하지 마세요 \n" +
                                "\n" +
                                "\n" +
                                "레이저스위치를 작동시켜\n" +
                                "로켓을 만들기위해 필요한 로켓 부품을 획득하세요!",
                        "이중 for\n" +
                                "go()\n" +
                                "turnLeft()\n" +
                                "turnRight()",
                        "go(),turnLeft(),turnRight(),for i in range(3):",
                        1,
                        1);

                GameMap gameMap12h1 = gameMapService.createGameMap(
                        "1", "1-2", "Hard", 1,
                        "go(),turnLeft(),turnRight(),for i in range():",
                        "# for i in range(3): 명령어는 여러줄의 코드블록을 괄호안의 숫자만큼 반복합니다.\n" +
                                "# 탭을 사용하여 for 아래의 이동 명령문을 들여 쓰세요.\n",
                        "목표지점에 도달하기\n" +
                                "로켓부품 모두 획득하기\n" +
                                "코드 40줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-2\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"laser_switch\", \"pos\": [3,3], \"laser_id\": [4,5,6], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"laser_switch\", \"pos\": [3,7], \"laser_id\": [7,8,9], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"laser_switch\", \"pos\": [23,3], \"laser_id\": [4,5,6], \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"laser_switch\", \"pos\": [23,7], \"laser_id\": [7,8,9], \"status\": 1},\n" +
                                "            {\"id\":4, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [6,4], \"pos_end\": [8,4], \"status\": 1},\n" +
                                "            {\"id\":5, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [12,4], \"pos_end\": [14,4], \"status\": 1},\n" +
                                "            {\"id\":6, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [18,4], \"pos_end\": [20,4], \"status\": 1},\n" +
                                "            {\"id\":7, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [6,6], \"pos_end\": [8,6], \"status\": 1},\n" +
                                "            {\"id\":8, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [12,6], \"pos_end\": [14,6], \"status\": 1},\n" +
                                "            {\"id\":9, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [18,6], \"pos_end\": [20,6], \"status\": 1},\n" +
                                "            {\"id\":10, \"type\": \"drop_switch\", \"pos\": [9,3], \"pos_drop\": [7,1], \"count\": 3, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":11, \"type\": \"drop_switch\", \"pos\": [15,3], \"pos_drop\": [15,1], \"count\": 3, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":12, \"type\": \"drop_switch\", \"pos\": [21,3], \"pos_drop\": [17,1], \"count\": 3, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":13, \"type\": \"drop_switch\", \"pos\": [5,9], \"pos_drop\": [9,7], \"count\": 3, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":14, \"type\": \"drop_switch\", \"pos\": [11,9], \"pos_drop\": [13,9], \"count\": 3, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":15, \"type\": \"drop_switch\", \"pos\": [17,9], \"pos_drop\": [21,9], \"count\": 3, \"drop_type\": \"rocket_parts\", \"status\": 3}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"item\", \"type\": \"rocket_parts\", \"count\": 18},\n" +
                                "            {\"goal\": \"line\", \"count\": 100}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "부품회수\n" +
                                "\n" +
                                "로켓을 만들어야합니다.\n" +
                                "로켓을 만들기위해 로켓 부품을 획득해 목표지점으로 이동하세요.",
                        "for 문 사용\n" +
                                "go()\n" +
                                "turnLeft()\n" +
                                "turnRight()",
                        "go(),turnLeft(),turnRight(),for i in range(3):",
                        1,
                        1);

                GameMap gameMap12h2 = gameMapService.createGameMap(
                        "1", "1-2", "Hard", 2,
                        "go(),turnLeft(),turnRight(),for i in range():",
                        "# for i in range(3): 명령어는 여러 줄의 코드 블록을 괄호 안의 숫자만큼 반복합니다.\n" +
                                "# 탭을 사용하여 for 아래의 이동 명령문을 들여 쓰세요.\n" +
                                "# 위의 스위치는 위쪽 방과 연결되어있고 아래의 스위치는 아래쪽 방들과 연결되어있습니다.\n" +
                                "# 각 방의 스위치를 밟으면 로켓 부품이 노란색 마커 위에 떨어집니다.\n",
                        "로켓부품 18개 획득하기\n" +
                                "코드 100줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-2\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"laser_switch\", \"pos\": [3,3], \"laser_id\": [4,5,8,9], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"laser_switch\", \"pos\": [3,7], \"laser_id\": [4,5,8,9], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"laser_switch\", \"pos\": [29,3], \"laser_id\": [6,7,10,11], \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"laser_switch\", \"pos\": [29,7], \"laser_id\": [6,7,10,11], \"status\": 1},\n" +
                                "            {\"id\":4, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [6,4], \"pos_end\": [8,4], \"status\": 1},\n" +
                                "            {\"id\":5, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [12,4], \"pos_end\": [14,4], \"status\": 1},\n" +
                                "            {\"id\":6, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [18,4], \"pos_end\": [20,4], \"status\": 1},\n" +
                                "            {\"id\":7, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [24,4], \"pos_end\": [26,4], \"status\": 1},\n" +
                                "            {\"id\":8, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [6,6], \"pos_end\": [8,6], \"status\": 1},\n" +
                                "            {\"id\":9, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [12,6], \"pos_end\": [14,6], \"status\": 1},\n" +
                                "            {\"id\":10, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [18,6], \"pos_end\": [20,6], \"status\": 1},\n" +
                                "            {\"id\":11, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [24,6], \"pos_end\": [26,6], \"status\": 1},\n" +
                                "            {\"id\":12, \"type\": \"drop_switch\", \"pos\": [7,3], \"pos_drop\": [9,1], \"count\": 3, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":13, \"type\": \"drop_switch\", \"pos\": [15,3], \"pos_drop\": [13,1], \"count\": 3, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":14, \"type\": \"drop_switch\", \"pos\": [21,3], \"pos_drop\": [21,1], \"count\": 3, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":15, \"type\": \"drop_switch\", \"pos\": [23,3], \"pos_drop\": [23,1], \"count\": 3, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":16, \"type\": \"drop_switch\", \"pos\": [7,9], \"pos_drop\": [7,7], \"count\": 3, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":17, \"type\": \"drop_switch\", \"pos\": [11,9], \"pos_drop\": [13,9], \"count\": 3, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":18, \"type\": \"drop_switch\", \"pos\": [17,7], \"pos_drop\": [19,9], \"count\": 3, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":19, \"type\": \"drop_switch\", \"pos\": [23,7], \"pos_drop\": [23,9], \"count\": 3, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":20, \"type\": \"bomb\", \"pos\": [27,2], \"status\": 1},\n" +
                                "            {\"id\":21, \"type\": \"bomb\", \"pos\": [17,8], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"item\", \"type\": \"rocket_parts\", \"count\": 24},\n" +
                                "            {\"goal\": \"line\", \"count\": 200}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,5],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "레이저 주의-1\n" +
                                "\n" +
                                "앞에 보이는 레이저를 조심하세요!!\n" +
                                "레이저는  레이저 스위치를 동작시켜 레이저를 꺼야합니다!\n" +
                                "\n" +
                                "절대 레이저가 켜져있는 상태로 접근하지 마세요 \n" +
                                "\n" +
                                "\n" +
                                "레이저스위치를 작동시켜\n" +
                                "로켓을 만들기위해 필요한 로켓 부품을 획득하세요!",
                        "이중 for\n" +
                                "go()\n" +
                                "turnLeft()\n" +
                                "turnRight()\n" +
                                "최소 코드작성",
                        "go(),turnLeft(),turnRight(),for i in range(3):",
                        1,
                        1);

                GameMap gameMap12h3 = gameMapService.createGameMap(
                        "1", "1-2", "Hard", 3,
                        "go(),turnLeft(),turnRight(),for i in range():",
                        "# for i in range(3): 명령어는 여러 줄의 코드 블록을 괄호 안의 숫자만큼 반복합니다.\n" +
                                "# 탭을 사용하여 for 아래의 이동 명령문을 들여 쓰세요.\n" +
                                "# 왼쪽 스위치는 왼쪽 방 4개와 연결되어있고 오른쪽 스위치는 오른쪽 방 4개와 연결되어있습니다.\n" +
                                "# 각 방의 스위치를 밟으면 로켓 부품이 노란색 마커 위에 떨어지고 각 스위치는 세번씩 작동합니다.\n",
                        "로켓부품 24개 획득하기\n" +
                                "코드 200줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-2\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0],\n" +
                                "            [0,0,0,0,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0],\n" +
                                "            [0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0,0,0,0,2,0,0],\n" +
                                "            [0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0,1,0,1,2,1,0],\n" +
                                "            [0,0,0,0,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"laser_switch\", \"pos\": [3,5], \"laser_id\": [4,5,8,9], \"status\": 1},\n" +
                                "            {\"id\":1, \"type\": \"laser_switch\", \"pos\": [3,9], \"laser_id\": [4,5,8,9], \"status\": 1},\n" +
                                "            {\"id\":2, \"type\": \"laser_switch\", \"pos\": [29,5], \"laser_id\": [6,7,10,11], \"status\": 1},\n" +
                                "            {\"id\":3, \"type\": \"laser_switch\", \"pos\": [29,9], \"laser_id\": [6,7,10,11], \"status\": 1},\n" +
                                "            {\"id\":4, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [6,6], \"pos_end\": [8,6], \"status\": 1},\n" +
                                "            {\"id\":5, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [12,6], \"pos_end\": [14,6], \"status\": 1},\n" +
                                "            {\"id\":6, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [18,6], \"pos_end\": [20,6], \"status\": 1},\n" +
                                "            {\"id\":7, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [24,6], \"pos_end\": [26,6], \"status\": 1},\n" +
                                "            {\"id\":8, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [6,8], \"pos_end\": [8,8], \"status\": 1},\n" +
                                "            {\"id\":9, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [12,8], \"pos_end\": [14,8], \"status\": 1},\n" +
                                "            {\"id\":10, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [18,8], \"pos_end\": [20,8], \"status\": 1},\n" +
                                "            {\"id\":11, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [24,8], \"pos_end\": [26,8], \"status\": 1},\n" +
                                "            {\"id\":12, \"type\": \"drop_switch\", \"pos\": [7,5], \"pos_drop\": [9,3], \"count\": 3, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":13, \"type\": \"drop_switch\", \"pos\": [15,5], \"pos_drop\": [13,3], \"count\": 3, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":14, \"type\": \"drop_switch\", \"pos\": [21,5], \"pos_drop\": [21,3], \"count\": 3, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":15, \"type\": \"drop_switch\", \"pos\": [23,5], \"pos_drop\": [23,3], \"count\": 3, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":16, \"type\": \"drop_switch\", \"pos\": [7,11], \"pos_drop\": [7,9], \"count\": 3, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":17, \"type\": \"drop_switch\", \"pos\": [11,11], \"pos_drop\": [13,11], \"count\": 3, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":18, \"type\": \"drop_switch\", \"pos\": [17,9], \"pos_drop\": [19,11], \"count\": 3, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":19, \"type\": \"drop_switch\", \"pos\": [23,9], \"pos_drop\": [23,11], \"count\": 3, \"drop_type\": \"rocket_parts\", \"status\": 3},\n" +
                                "            {\"id\":20, \"type\": \"bomb\", \"pos\": [27,4], \"status\": 1},\n" +
                                "            {\"id\":21, \"type\": \"bomb\", \"pos\": [17,10], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"item\", \"type\": \"rocket_parts\", \"count\": 24},\n" +
                                "            {\"goal\": \"line\", \"count\": 200}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [1,7],\n" +
                                "        \"dir\" : \"right\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "레이저 주의-2\n" +
                                "\n" +
                                "레이저 스위치를 동작시켜 레이저를 꺼야합니다!\n" +
                                "\n" +
                                "절대 레이저가 켜져있는 상태로 접근하지 마세요 \n" +
                                "\n" +
                                "\n" +
                                "레이저스위치를 작동시켜\n" +
                                "로켓을 만들기위해 필요한 로켓 부품을 획득하세요!",
                        "이중 for\n" +
                                "go()\n" +
                                "turnLeft()\n" +
                                "turnRight()",
                        "go(),turnLeft(),turnRight(),for i in range(3):",
                        1,
                        1);

                GameMap gameMap13e1 = gameMapService.createGameMap(
                        "1", "1-3", "Easy", 1,
                        "go(),turnLeft(),turnRight(),for i in range():,set()",
                        "# set(‘고체추진제’)를 작성하여 로켓 재료를 장착합니다. \n" +
                                "# 노란색 마커에서 장착이 가능합니다.\n",
                        "고체추진제 3개 장착하기\n" +
                                "코드 10줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-3\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"solid_propellant\", \"pos\": [3,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0},\n" +
                                "            {\"id\":1, \"type\": \"solid_propellant\", \"pos\": [7,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0},\n" +
                                "            {\"id\":2, \"type\": \"solid_propellant\", \"pos\": [11,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0},\n" +
                                "            {\"id\":3, \"type\": \"solid_propellant\", \"pos\": [15,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"set\", \"type\": \"solid_propellant\", \"count\": 3},\n" +
                                "            {\"goal\": \"line\", \"count\": 10}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [9,7],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "발사장에서-1\n" +
                                "\n" +
                                "발사장에서 로켓을 조립해야합니다.\n" +
                                "\n" +
                                "set 명령어를 사용하여 부품을 장착해야합니다!",
                        "set(”고체추진제”)\n" +
                                "go()\n" +
                                "turnLeft()\n" +
                                "turnRight()",
                        "go(),turnLeft(),turnRight(),for i in range(3):,set('고체추진제')",
                        1,
                        1);

                GameMap gameMap13e2 = gameMapService.createGameMap(
                        "1", "1-3", "Easy", 2,
                        "go(),turnLeft(),turnRight(),for i in range():,set()",
                        "# set(‘액체연료’)를 작성하여로켓 재료를 장착합니다. \n" +
                                "# 노란색 마커에서 장착이 가능합니다.\n",
                        "액체연료 5개 장착하기\n" +
                                "코드 30줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-3\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,2,2,2,0,0,0,2,2,2,0,0,0,2,2,2,0,2,2,2,0,2,2,2,0,0,0,0,0,0],\n" +
                                "            [0,1,2,1,2,1,0,1,2,1,2,1,0,1,2,1,2,1,2,1,2,1,2,1,2,1,0,1,0,1,0],\n" +
                                "            [2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,2,2,2,0,2,2,2,2,2,2,2],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"liquid_fuel\", \"pos\": [3,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":1, \"type\": \"liquid_fuel\", \"pos\": [9,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":2, \"type\": \"liquid_fuel\", \"pos\": [15,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":3, \"type\": \"liquid_fuel\", \"pos\": [19,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":4, \"type\": \"liquid_fuel\", \"pos\": [23,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0}\n" +
                                "\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"set\", \"type\": \"liquid_fuel\", \"count\": 5},\n" +
                                "            {\"goal\": \"line\", \"count\": 30}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [9,7],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "발사장에서 -2\n" +
                                "\n" +
                                "이제 연료를 주입해야합니다.\n" +
                                "\n" +
                                "set 명령어를 사용하여 액체 연료를 주입하세요!\n" +
                                "\n" +
                                " for 문을 사용하면 더욱 쉽게 작성할수 있습니다!",
                        "최소코드작성\n" +
                                "set(”액체연료”)\n" +
                                "for\n" +
                                "go()\n" +
                                "turnRight()\n" +
                                "turnLeft()",
                        "go(),turnLeft(),turnRight(),for i in range(3):,set('고체추진제')",
                        1,
                        1);

                GameMap gameMap13e3 = gameMapService.createGameMap(
                        "1", "1-3", "Easy", 3,
                        "go(),turnLeft(),turnRight(),for i in range():,set()",
                        "# set(‘추가엔진’)를 작성하여 로켓 재료를 장착합니다. \n" +
                                "# 노란색 마커에서 장착이 가능합니다.\n",
                        "추가엔진 6개 장착하기\n" +
                                "코드 30줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-3\",\n" +
                                "        \"diff\" : \"Easy\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,2,1,2,1,0,1,0,1,2,1,2,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,2,2,0,2,2,2,2,2,2,2,0,2,2,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,2,2,0,2,2,2,2,2,2,2,0,2,2,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,2,1,2,1,0,1,0,1,2,1,2,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"engines\", \"pos\": [5,7], \"require_dir\": \"left\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":1, \"type\": \"engines\", \"pos\": [11,1], \"require_dir\": \"up\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":2, \"type\": \"engines\", \"pos\": [17,3], \"require_dir\": \"left\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":3, \"type\": \"engines\", \"pos\": [23,5], \"require_dir\": \"up\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":4, \"type\": \"engines\", \"pos\": [11,13], \"require_dir\": \"down\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":5, \"type\": \"engines\", \"pos\": [21,11], \"require_dir\": \"right\", \"name\": \"추가엔진\", \"status\": 0}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"set\", \"type\": \"engines\", \"count\": 6},\n" +
                                "            {\"goal\": \"line\", \"count\": 30}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [15,7],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "발사 임박\n" +
                                "\n" +
                                "마지막입니다! \n" +
                                "발사추진력을 얻기 위한 추가 엔진을 장착해주세요!\n" +
                                "\n" +
                                "역시나 for문을 사용해 작업 능률을 올려야합니다!",
                        "for\n" +
                                "최소코드작성\n" +
                                "set(”추가엔진”)\n" +
                                "go()\n" +
                                "turnLeft()\n" +
                                "turnRight()",
                        "go(),turnLeft(),turnRight(),for i in range(3):,set('고체추진제')",
                        1,
                        1);

                GameMap gameMap13n1 = gameMapService.createGameMap(
                        "1", "1-3", "Normal", 1,
                        "go(),turnLeft(),turnRight(),for i in range():,set()",
                        "# set(‘고체추진제’)를 작성하여 로켓 재료를 장착합니다. \n" +
                                "# 노란색 마커에서 장착이 가능합니다.\n",
                        "고체추진제 5개 장착하기\n" +
                                "코드 25줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-3\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"solid_propellant\", \"pos\": [3,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0},\n" +
                                "            {\"id\":1, \"type\": \"solid_propellant\", \"pos\": [7,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0},\n" +
                                "            {\"id\":2, \"type\": \"solid_propellant\", \"pos\": [11,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0},\n" +
                                "            {\"id\":3, \"type\": \"solid_propellant\", \"pos\": [17,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0},\n" +
                                "            {\"id\":4, \"type\": \"solid_propellant\", \"pos\": [19,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0},\n" +
                                "            {\"id\":5, \"type\": \"bomb\", \"pos\": [9,5], \"status\": 1},\n" +
                                "            {\"id\":6, \"type\": \"bomb\", \"pos\": [13,5], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"set\", \"type\": \"solid_propellant\", \"count\": 5},\n" +
                                "            {\"goal\": \"line\", \"count\": 25}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [11,9],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "발사장에서-1\n" +
                                "\n" +
                                "발사장에서 로켓을 조립해야합니다.\n" +
                                "\n" +
                                "set 명령어를 사용하여 부품을 장착해야합니다!",
                        "set(”고체추진제”)\n" +
                                "go()\n" +
                                "turnLeft()\n" +
                                "turnRight()",
                        "go(),turnLeft(),turnRight(),for i in range(3):,set('고체추진제')",
                        1,
                        1);

                GameMap gameMap13n2 = gameMapService.createGameMap(
                        "1", "1-3", "Normal", 2,
                        "go(),turnLeft(),turnRight(),for i in range():,set()",
                        "# set(‘액체연료’)를 작성하여 로켓 재료를 장착합니다. \n" +
                                "# 노란색 마커에서 장착이 가능합니다.\n" +
                                "# 각 스위치는 위쪽의 레이저와 연결되어 있습니다.\n",
                        "액체연료 5개 장착하기\n" +
                                "코드 35줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-3\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,2,2,2,0,0,0,2,2,2,0,0,0,2,2,2,0,2,2,2,0,2,2,2,0,0,0,0,0,0],\n" +
                                "            [0,1,2,1,2,1,0,1,2,1,2,1,0,1,2,1,2,1,2,1,2,1,2,1,2,1,0,1,0,1,0],\n" +
                                "            [2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,2,2,2,0,2,2,2,2,2,2,2],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"liquid_fuel\", \"pos\": [3,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":1, \"type\": \"liquid_fuel\", \"pos\": [9,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":2, \"type\": \"liquid_fuel\", \"pos\": [15,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":3, \"type\": \"liquid_fuel\", \"pos\": [19,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":4, \"type\": \"liquid_fuel\", \"pos\": [23,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":5, \"type\": \"laser_switch\", \"pos\": [5,9], \"laser_id\": [10], \"status\": 1},\n" +
                                "            {\"id\":6, \"type\": \"laser_switch\", \"pos\": [11,9], \"laser_id\": [11], \"status\": 1},\n" +
                                "            {\"id\":7, \"type\": \"laser_switch\", \"pos\": [15,9], \"laser_id\": [12], \"status\": 1},\n" +
                                "            {\"id\":8, \"type\": \"laser_switch\", \"pos\": [19,9], \"laser_id\": [13], \"status\": 1},\n" +
                                "            {\"id\":9, \"type\": \"laser_switch\", \"pos\": [23,9], \"laser_id\": [14], \"status\": 1},\n" +
                                "            {\"id\":10, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [2,4], \"pos_end\": [4,4], \"status\": 1},\n" +
                                "            {\"id\":11, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [8,4], \"pos_end\": [10,4], \"status\": 1},\n" +
                                "            {\"id\":12, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [14,4], \"pos_end\": [16,4], \"status\": 1},\n" +
                                "            {\"id\":13, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [18,4], \"pos_end\": [20,4], \"status\": 1},\n" +
                                "            {\"id\":14, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [22,4], \"pos_end\": [24,4], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"set\", \"type\": \"liquid_fuel\", \"count\": 5},\n" +
                                "            {\"goal\": \"line\", \"count\": 35}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [13,7],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "발사장에서 -2\n" +
                                "\n" +
                                "이제 연료를 주입해야합니다.\n" +
                                "\n" +
                                "set 명령어를 사용하여 액체 연료를 주입하세요!\n" +
                                "\n" +
                                " for 문을 사용하면 더욱 쉽게 작성할수 있습니다!",
                        "최소코드작성\n" +
                                "set(”액체연료”)\n" +
                                "for\n" +
                                "go()\n" +
                                "turnRight()\n" +
                                "turnLeft()",
                        "go(),turnLeft(),turnRight(),for i in range(3):,set('고체추진제')",
                        1,
                        1);

                GameMap gameMap13n3 = gameMapService.createGameMap(
                        "1", "1-3", "Normal", 3,
                        "go(),turnLeft(),turnRight(),for i in range():,set()",
                        "# set(‘추가엔진’)를 작성하여 로켓 재료를 장착합니다. \n" +
                                "# 노란색 마커에서 장착이 가능합니다.\n",
                        "추가엔진 6개 장착하기\n" +
                                "코드 45줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-3\",\n" +
                                "        \"diff\" : \"Normal\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,2,1,2,1,0,1,0,1,2,1,2,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,2,2,0,2,2,2,2,2,2,2,0,2,2,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,2,2,0,2,2,2,2,2,2,2,0,2,2,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,2,1,2,1,0,1,0,1,2,1,2,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"engines\", \"pos\": [5,7], \"require_dir\": \"left\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":1, \"type\": \"engines\", \"pos\": [11,1], \"require_dir\": \"up\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":2, \"type\": \"engines\", \"pos\": [17,3], \"require_dir\": \"left\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":3, \"type\": \"engines\", \"pos\": [23,5], \"require_dir\": \"up\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":4, \"type\": \"engines\", \"pos\": [11,13], \"require_dir\": \"down\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":5, \"type\": \"engines\", \"pos\": [21,11], \"require_dir\": \"right\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":6, \"type\": \"bomb\", \"pos\": [11,7], \"status\": 1},\n" +
                                "            {\"id\":7, \"type\": \"bomb\", \"pos\": [19,7], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"set\", \"type\": \"engines\", \"count\": 6},\n" +
                                "            {\"goal\": \"line\", \"count\": 45}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [15,7],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "발사 임박\n" +
                                "\n" +
                                "마지막입니다! \n" +
                                "발사추진력을 얻기 위한 추가 엔진을 장착해주세요!\n" +
                                "\n" +
                                "역시나 for문을 사용해 작업 능률을 올려야합니다!",
                        "for\n" +
                                "최소코드작성\n" +
                                "set(”추가엔진”)\n" +
                                "go()\n" +
                                "turnLeft()\n" +
                                "turnRight()",
                        "go(),turnLeft(),turnRight(),for i in range(3):,set('고체추진제')",
                        1,
                        1);

                GameMap gameMap13h1 = gameMapService.createGameMap(
                        "1", "1-3", "Hard", 1,
                        "go(),turnLeft(),turnRight(),for i in range():,set()",
                        "# set(‘고체추진제’)를 작성하여 로켓 재료를 장착합니다.\n" +
                                "# 노란색 마커에서 장착이 가능합니다.\n",
                        "고체추진제 6개 장착하기\n" +
                                "코드 30줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-3\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 1,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2,2],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"solid_propellant\", \"pos\": [3,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0},\n" +
                                "            {\"id\":1, \"type\": \"solid_propellant\", \"pos\": [7,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0},\n" +
                                "            {\"id\":2, \"type\": \"solid_propellant\", \"pos\": [11,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0},\n" +
                                "            {\"id\":3, \"type\": \"solid_propellant\", \"pos\": [15,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0},\n" +
                                "            {\"id\":4, \"type\": \"solid_propellant\", \"pos\": [17,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0},\n" +
                                "            {\"id\":5, \"type\": \"solid_propellant\", \"pos\": [19,3], \"require_dir\": \"up\", \"name\": \"고체추진제\", \"status\": 0},\n" +
                                "            {\"id\":6, \"type\": \"bomb\", \"pos\": [5,5], \"status\": 1},\n" +
                                "            {\"id\":7, \"type\": \"bomb\", \"pos\": [9,5], \"status\": 1},\n" +
                                "            {\"id\":8, \"type\": \"bomb\", \"pos\": [13,7], \"status\": 1}\n" +
                                "\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"set\", \"type\": \"solid_propellant\", \"count\": 6},\n" +
                                "            {\"goal\": \"line\", \"count\": 30}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [11,9],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "발사장에서-1\n" +
                                "\n" +
                                "발사장에서 로켓을 조립해야합니다.\n" +
                                "\n" +
                                "set 명령어를 사용하여 부품을 장착해야합니다!",
                        "set(”고체추진제”)\n" +
                                "go()\n" +
                                "turnLeft()\n" +
                                "turnRight()",
                        "go(),turnLeft(),turnRight(),for i in range(3):,set('고체추진제')",
                        1,
                        1);

                GameMap gameMap13h2 = gameMapService.createGameMap(
                        "1", "1-3", "Hard", 2,
                        "go(),turnLeft(),turnRight(),for i in range():,set()",
                        "# set(‘액체연료’)를 작성하여 로켓 재료를 장착합니다. \n" +
                                "# 노란색 마커에서 장착이 가능합니다.\n" +
                                "# 각 스위치는 위쪽의 레이저와 연결되어 있습니다.\n",
                        "액체연료 13개 장착하기\n" +
                                "코드 50줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-3\",\n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 2,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,2,2,2,0,0,0,2,2,2,0,0,0,2,2,2,0,2,2,2,0,2,2,2,0,0,0,0,0,0],\n" +
                                "            [0,1,2,1,2,1,0,1,2,1,2,1,0,1,2,1,2,1,2,1,2,1,2,1,2,1,0,1,0,1,0],\n" +
                                "            [2,2,2,0,2,2,2,2,2,0,2,2,2,2,2,0,2,2,2,0,2,2,2,0,2,2,2,2,2,2,2],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"liquid_fuel\", \"pos\": [1,3], \"require_dir\": \"left\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":1, \"type\": \"liquid_fuel\", \"pos\": [3,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":2, \"type\": \"liquid_fuel\", \"pos\": [5,3], \"require_dir\": \"right\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":3, \"type\": \"liquid_fuel\", \"pos\": [7,3], \"require_dir\": \"left\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":4, \"type\": \"liquid_fuel\", \"pos\": [9,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":5, \"type\": \"liquid_fuel\", \"pos\": [11,3], \"require_dir\": \"right\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":6, \"type\": \"liquid_fuel\", \"pos\": [13,3], \"require_dir\": \"left\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":7, \"type\": \"liquid_fuel\", \"pos\": [15,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":8, \"type\": \"liquid_fuel\", \"pos\": [17,3], \"require_dir\": \"right\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":9, \"type\": \"liquid_fuel\", \"pos\": [19,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":10, \"type\": \"liquid_fuel\", \"pos\": [21,3], \"require_dir\": \"right\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":11, \"type\": \"liquid_fuel\", \"pos\": [23,1], \"require_dir\": \"up\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":12, \"type\": \"liquid_fuel\", \"pos\": [25,3], \"require_dir\": \"right\", \"name\": \"액체연료\", \"status\": 0},\n" +
                                "            {\"id\":13, \"type\": \"laser_switch\", \"pos\": [5,9], \"laser_id\": [18], \"status\": 1},\n" +
                                "            {\"id\":14, \"type\": \"laser_switch\", \"pos\": [11,9], \"laser_id\": [19], \"status\": 1},\n" +
                                "            {\"id\":15, \"type\": \"laser_switch\", \"pos\": [17,9], \"laser_id\": [20], \"status\": 1},\n" +
                                "            {\"id\":16, \"type\": \"laser_switch\", \"pos\": [19,9], \"laser_id\": [21], \"status\": 1},\n" +
                                "            {\"id\":17, \"type\": \"laser_switch\", \"pos\": [23,9], \"laser_id\": [22], \"status\": 1},\n" +
                                "            {\"id\":18, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [2,4], \"pos_end\": [4,4], \"status\": 1},\n" +
                                "            {\"id\":19, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [8,4], \"pos_end\": [10,4], \"status\": 1},\n" +
                                "            {\"id\":20, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [14,4], \"pos_end\": [16,4], \"status\": 1},\n" +
                                "            {\"id\":21, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [18,4], \"pos_end\": [20,4], \"status\": 1},\n" +
                                "            {\"id\":22, \"type\": \"laser\", \"dir\": \"h\", \"pos_start\": [22,4], \"pos_end\": [24,4], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"set\", \"type\": \"liquid_fuel\", \"count\": 13},\n" +
                                "            {\"goal\": \"line\", \"count\": 50}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [13,7],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "발사장에서 -2\n" +
                                "\n" +
                                "이제 연료를 주입해야합니다.\n" +
                                "\n" +
                                "set 명령어를 사용하여 액체 연료를 주입하세요!\n" +
                                "\n" +
                                " for 문을 사용하면 더욱 쉽게 작성할수 있습니다!",
                        "최소코드작성\n" +
                                "set(”액체연료”)\n" +
                                "for\n" +
                                "go()\n" +
                                "turnRight()\n" +
                                "turnLeft()",
                        "go(),turnLeft(),turnRight(),for i in range(3):,set('고체추진제')",
                        1,
                        1);

                GameMap gameMap13h3 = gameMapService.createGameMap(
                        "1", "1-3", "Hard", 3,
                        "go(),turnLeft(),turnRight(),for i in range():,set()",
                        "# set(‘추가엔진’)를 작성하여 로켓 재료를 장착합니다. \n" +
                                "# 노란색 마커에서 장착이 가능합니다.\n",
                        "추가엔진 18개 장착하기\n" +
                                "코드 50줄 이하로 작성하기",
                        "stage = {\n" +
                                "    \"stage\" : {\n" +
                                "        \"map\" : 1,\n" +
                                "        \"step\" : \"1-3\", \n" +
                                "        \"diff\" : \"Hard\",\n" +
                                "        \"level\" : 3,\n" +
                                "        \"tile\" : [\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,2,1,2,1,0,1,0,1,2,1,2,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,2,2,0,2,2,2,2,2,2,2,0,2,2,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0,1,0,1,0,1,2,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,2,2,2,0,2,2,2,2,2,2,2,0,2,2,2,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,2,1,2,1,0,1,0,1,2,1,2,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,2,2,2,0,0,0,0,0,2,2,2,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0],\n" +
                                "            [0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0,1,0],\n" +
                                "            [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]\n" +
                                "        ],\n" +
                                "        \"init_item_list\" : [\n" +
                                "            {\"id\":0, \"type\": \"engines\", \"pos\": [5,7], \"require_dir\": \"left\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":1, \"type\": \"engines\", \"pos\": [7,5], \"require_dir\": \"up\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":2, \"type\": \"engines\", \"pos\": [9,3], \"require_dir\": \"left\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":3, \"type\": \"engines\", \"pos\": [11,1], \"require_dir\": \"up\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":4, \"type\": \"engines\", \"pos\": [13,3], \"require_dir\": \"right\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":5, \"type\": \"engines\", \"pos\": [17,3], \"require_dir\": \"left\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":6, \"type\": \"engines\", \"pos\": [19,1], \"require_dir\": \"up\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":7, \"type\": \"engines\", \"pos\": [21,3], \"require_dir\": \"right\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":8, \"type\": \"engines\", \"pos\": [23,5], \"require_dir\": \"up\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":9, \"type\": \"engines\", \"pos\": [25,7], \"require_dir\": \"right\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":10, \"type\": \"engines\", \"pos\": [23,9], \"require_dir\": \"down\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":11, \"type\": \"engines\", \"pos\": [21,11], \"require_dir\": \"right\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":12, \"type\": \"engines\", \"pos\": [19,13], \"require_dir\": \"down\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":13, \"type\": \"engines\", \"pos\": [17,11], \"require_dir\": \"left\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":14, \"type\": \"engines\", \"pos\": [13,11], \"require_dir\": \"right\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":15, \"type\": \"engines\", \"pos\": [11,13], \"require_dir\": \"down\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":16, \"type\": \"engines\", \"pos\": [9,11], \"require_dir\": \"left\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":17, \"type\": \"engines\", \"pos\": [7,9], \"require_dir\": \"down\", \"name\": \"추가엔진\", \"status\": 0},\n" +
                                "            {\"id\":18, \"type\": \"bomb\", \"pos\": [11,7], \"status\": 1},\n" +
                                "            {\"id\":19, \"type\": \"bomb\", \"pos\": [19,7], \"status\": 1}\n" +
                                "        ],\n" +
                                "        \"goal_list\" : [\n" +
                                "            {\"goal\": \"set\", \"type\": \"engines\", \"count\": 18},\n" +
                                "            {\"goal\": \"line\", \"count\": 50}\n" +
                                "        ]\n" +
                                "    },\n" +
                                "    \"player\" : {\n" +
                                "        \"pos\" : [15,7],\n" +
                                "        \"dir\" : \"up\", \n" +
                                "        \"hp\" : 100,\n" +
                                "        \"status\" : 0,\n" +
                                "        \"food_count\" : 0,\n" +
                                "        \"rocket_parts_count\" : 0\n" +
                                "    },\n" +
                                "    \"item_list\" : [\n" +
                                "    ]\n" +
                                "}",
                        "발사 임박\n" +
                                "\n" +
                                "마지막입니다! \n" +
                                "발사추진력을 얻기 위한 추가 엔진을 장착해주세요!\n" +
                                "\n" +
                                "역시나 for문을 사용해 작업 능률을 올려야합니다!",
                        "for\n" +
                                "최소코드작성\n" +
                                "set(”추가엔진”)\n" +
                                "go()\n" +
                                "turnLeft()\n" +
                                "turnRight()",
                        "go(),turnLeft(),turnRight(),for i in range(3):,set('고체추진제')",
                        1,
                        1);

                Member memberUser2 = memberService.join("hadle", "1234", "", "", 1).getData();
                memberUser2.setRefreshToken("hadle");

                Member memberUser3 = memberService.join("test1", "1234", "", "", 1).getData();
                memberUser2.setRefreshToken("test1");

                Member memberUser4 = memberService.join("test2", "1234", "", "", 1).getData();
                memberUser2.setRefreshToken("test2");

                Member memberUser5 = memberService.join("test3", "1234", "", "", 1).getData();
                memberUser2.setRefreshToken("test3");

                Member memberUser6 = memberService.join("test4", "1234", "", "", 1).getData();
                memberUser2.setRefreshToken("test4");

                Member memberClassAdmin = memberService.join("class", "1234", "학급관리자", "010-1234-1234", 2).getData();
                memberClassAdmin.setRefreshToken("class");

                Member memberProgramAdmin = memberService.join("program", "1234", "사업관리자", "010-1234-1234", 3).getData();
                memberProgramAdmin.setRefreshToken("program");

                Member memberSystemAdmin = memberService.join("system", "1234", "시스템관리자", "010-1234-1234", 4).getData();
                memberSystemAdmin.setRefreshToken("system");

                ItemParts itemParts1 = itemPartsService.createItemParts("신발");
                ItemParts itemParts2 = itemPartsService.createItemParts("모듈");
                ItemParts itemParts3 = itemPartsService.createItemParts("장갑");
                ItemParts itemParts4 = itemPartsService.createItemParts("우주복");
                ItemParts itemParts5 = itemPartsService.createItemParts("헬멧");
                ItemParts itemParts6 = itemPartsService.createItemParts("총");

                Item item1 = itemService.createItem(itemParts1, "우주용 신발",
                        "이동과 회전 명령어를 사용할 수 있게 하는 장비.\n" +
                                "go() : 플레이어를 바라보고 있는 방향으로 이동시킨다.\n" +
                                "turnLeft() : 플레이어를 왼쪽으로 회전시킨다.\n" +
                                "turnRight() : 플레이어를 오른쪽으로 회전시킨다.",
                        "", "/img/inventory/icon_space_boots.png", 0);

                Item item2 = itemService.createItem(itemParts2, "Lv1 모듈",
                        "반복 명령어 for를 사용할 수 있게 하는 장비.",
                        "", "/img/inventory/icon_module.png", 0);

                Item item3 = itemService.createItem(itemParts3, "우주용 장갑",
                        "장착 명령어를 사용할 수 있게 하는 장비.\n" +
                                "set() : 입력된 아이템을 플레이어가 바라보고 있는 곳에 장착시킨다.",
                        "",
                        "/img/inventory/icon_space_gloves.png", 0);

                gameMapTutorial2.setRewardItem(item1);
                gameMap11e3.setRewardItem(item2);
                gameMap12e3.setRewardItem(item3);
                gameMapRepository.save(gameMapTutorial2);
                gameMapRepository.save(gameMap11e3);
                gameMapRepository.save(gameMap12e3);

                inventoryService.createInventory(memberUser2.getPlayer(), item1, false);
                inventoryService.createInventory(memberUser2.getPlayer(), item2, false);
                inventoryService.createInventory(memberUser2.getPlayer(), item3, false);

                // 1-1 itemParts1
                requirePartsService.addRequireParts(gameMap11e1, itemParts1);
                requirePartsService.addRequireParts(gameMap11e2, itemParts1);
                requirePartsService.addRequireParts(gameMap11e3, itemParts1);
                requirePartsService.addRequireParts(gameMap11n1, itemParts1);
                requirePartsService.addRequireParts(gameMap11n2, itemParts1);
                requirePartsService.addRequireParts(gameMap11n3, itemParts1);
                requirePartsService.addRequireParts(gameMap11h1, itemParts1);
                requirePartsService.addRequireParts(gameMap11h2, itemParts1);
                requirePartsService.addRequireParts(gameMap11h3, itemParts1);

                // 1-2 itemParts1
                requirePartsService.addRequireParts(gameMap12e1, itemParts1);
                requirePartsService.addRequireParts(gameMap12e2, itemParts1);
                requirePartsService.addRequireParts(gameMap12e3, itemParts1);
                requirePartsService.addRequireParts(gameMap12n1, itemParts1);
                requirePartsService.addRequireParts(gameMap12n2, itemParts1);
                requirePartsService.addRequireParts(gameMap12n3, itemParts1);
                requirePartsService.addRequireParts(gameMap12h1, itemParts1);
                requirePartsService.addRequireParts(gameMap12h2, itemParts1);
                requirePartsService.addRequireParts(gameMap12h3, itemParts1);

                // 1-2 itemParts2
                requirePartsService.addRequireParts(gameMap12e1, itemParts2);
                requirePartsService.addRequireParts(gameMap12e2, itemParts2);
                requirePartsService.addRequireParts(gameMap12e3, itemParts2);
                requirePartsService.addRequireParts(gameMap12n1, itemParts2);
                requirePartsService.addRequireParts(gameMap12n2, itemParts2);
                requirePartsService.addRequireParts(gameMap12n3, itemParts2);
                requirePartsService.addRequireParts(gameMap12h1, itemParts2);
                requirePartsService.addRequireParts(gameMap12h2, itemParts2);
                requirePartsService.addRequireParts(gameMap12h3, itemParts2);

                // 1-3 itemParts1
                requirePartsService.addRequireParts(gameMap13e1, itemParts1);
                requirePartsService.addRequireParts(gameMap13e2, itemParts1);
                requirePartsService.addRequireParts(gameMap13e3, itemParts1);
                requirePartsService.addRequireParts(gameMap13n1, itemParts1);
                requirePartsService.addRequireParts(gameMap13n2, itemParts1);
                requirePartsService.addRequireParts(gameMap13n3, itemParts1);
                requirePartsService.addRequireParts(gameMap13h1, itemParts1);
                requirePartsService.addRequireParts(gameMap13h2, itemParts1);
                requirePartsService.addRequireParts(gameMap13h3, itemParts1);

                // 1-1 itemParts2
                requirePartsService.addRequireParts(gameMap13e1, itemParts2);
                requirePartsService.addRequireParts(gameMap13e2, itemParts2);
                requirePartsService.addRequireParts(gameMap13e3, itemParts2);
                requirePartsService.addRequireParts(gameMap13n1, itemParts2);
                requirePartsService.addRequireParts(gameMap13n2, itemParts2);
                requirePartsService.addRequireParts(gameMap13n3, itemParts2);
                requirePartsService.addRequireParts(gameMap13h1, itemParts2);
                requirePartsService.addRequireParts(gameMap13h2, itemParts2);
                requirePartsService.addRequireParts(gameMap13h3, itemParts2);

                // 1-1 itemParts3
                requirePartsService.addRequireParts(gameMap13e1, itemParts3);
                requirePartsService.addRequireParts(gameMap13e2, itemParts3);
                requirePartsService.addRequireParts(gameMap13e3, itemParts3);
                requirePartsService.addRequireParts(gameMap13n1, itemParts3);
                requirePartsService.addRequireParts(gameMap13n2, itemParts3);
                requirePartsService.addRequireParts(gameMap13n3, itemParts3);
                requirePartsService.addRequireParts(gameMap13h1, itemParts3);
                requirePartsService.addRequireParts(gameMap13h2, itemParts3);
                requirePartsService.addRequireParts(gameMap13h3, itemParts3);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                    gameMapTutorial1.getId(), gameMapTutorial1.getStage(), gameMapTutorial1.getStep(), gameMapTutorial1.getDifficulty(), gameMapTutorial1.getLevel(),
                    "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                    gameMapTutorial2.getId(), gameMapTutorial2.getStage(), gameMapTutorial2.getStep(), gameMapTutorial2.getDifficulty(), gameMapTutorial2.getLevel(),
                    "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                    gameMap11e1.getId(), gameMap11e1.getStage(), gameMap11e1.getStep(), gameMap11e1.getDifficulty(), gameMap11e1.getLevel(),
                    "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                        gameMap11e2.getId(), gameMap11e2.getStage(), gameMap11e2.getStep(), gameMap11e2.getDifficulty(), gameMap11e2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                        gameMap11e3.getId(), gameMap11e3.getStage(), gameMap11e3.getStep(), gameMap11e3.getDifficulty(), gameMap11e3.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                        gameMap11n1.getId(), gameMap11n1.getStage(), gameMap11n1.getStep(), gameMap11n1.getDifficulty(), gameMap11n1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                        gameMap11n2.getId(), gameMap11n2.getStage(), gameMap11n2.getStep(), gameMap11n2.getDifficulty(), gameMap11n2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                        gameMap11n3.getId(), gameMap11n3.getStage(), gameMap11n3.getStep(), gameMap11n3.getDifficulty(), gameMap11n3.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                        gameMap11h1.getId(), gameMap11h1.getStage(), gameMap11h1.getStep(), gameMap11h1.getDifficulty(), gameMap11h1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                        gameMap11h2.getId(), gameMap11h2.getStage(), gameMap11h2.getStep(), gameMap11h2.getDifficulty(), gameMap11h2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                        gameMap11h3.getId(), gameMap11h3.getStage(), gameMap11h3.getStep(), gameMap11h3.getDifficulty(), gameMap11h3.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                        gameMap12e1.getId(), gameMap12e1.getStage(), gameMap12e1.getStep(), gameMap12e1.getDifficulty(), gameMap12e1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                        gameMap12e2.getId(), gameMap12e2.getStage(), gameMap12e2.getStep(), gameMap12e2.getDifficulty(), gameMap12e2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                        gameMap12e3.getId(), gameMap12e3.getStage(), gameMap12e3.getStep(), gameMap12e3.getDifficulty(), gameMap12e3.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                        gameMap12n1.getId(), gameMap12n1.getStage(), gameMap12n1.getStep(), gameMap12n1.getDifficulty(), gameMap12n1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                        gameMap12n2.getId(), gameMap12n2.getStage(), gameMap12n2.getStep(), gameMap12n2.getDifficulty(), gameMap12n2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                        gameMap12n3.getId(), gameMap12n3.getStage(), gameMap12n3.getStep(), gameMap12n3.getDifficulty(), gameMap12n3.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                        gameMap12h1.getId(), gameMap12h1.getStage(), gameMap12h1.getStep(), gameMap12h1.getDifficulty(), gameMap12h1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                        gameMap12h2.getId(), gameMap12h2.getStage(), gameMap12h2.getStep(), gameMap12h2.getDifficulty(), gameMap12h2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                        gameMap12h3.getId(), gameMap12h3.getStage(), gameMap12h3.getStep(), gameMap12h3.getDifficulty(), gameMap12h3.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                        gameMap13e1.getId(), gameMap13e1.getStage(), gameMap13e1.getStep(), gameMap13e1.getDifficulty(), gameMap13e1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                        gameMap13e2.getId(), gameMap13e2.getStage(), gameMap13e2.getStep(), gameMap13e2.getDifficulty(), gameMap13e2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                        gameMap13e3.getId(), gameMap13e3.getStage(), gameMap13e3.getStep(), gameMap13e3.getDifficulty(), gameMap13e3.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                        gameMap13n1.getId(), gameMap13n1.getStage(), gameMap13n1.getStep(), gameMap13n1.getDifficulty(), gameMap13n1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                        gameMap13n2.getId(), gameMap13n2.getStage(), gameMap13n2.getStep(), gameMap13n2.getDifficulty(), gameMap13n2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                        gameMap13n3.getId(), gameMap13n3.getStage(), gameMap13n3.getStep(), gameMap13n3.getDifficulty(), gameMap13n3.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                        gameMap13h1.getId(), gameMap13h1.getStage(), gameMap13h1.getStep(), gameMap13h1.getDifficulty(), gameMap13h1.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                        gameMap13h2.getId(), gameMap13h2.getStage(), gameMap13h2.getStep(), gameMap13h2.getDifficulty(), gameMap13h2.getLevel(),
                        "", 1);

                playerLogService.createPlayerLog("STAGECLEAR", memberUser2.getUsername(), memberUser2.getId(),
                        gameMap13h3.getId(), gameMap13h3.getStage(), gameMap13h3.getStep(), gameMap13h3.getDifficulty(), gameMap13h3.getLevel(),
                        "", 1);





            }
        };
    }
}