package com.example.cit.domain.achievement.playerAchievement.service;

import com.example.cit.domain.achievement.achievement.dto.AchievementDto;
import com.example.cit.domain.achievement.achievement.entity.Achievement;
import com.example.cit.domain.achievement.achievement.service.AchievementService;
import com.example.cit.domain.achievement.playerAchievement.entity.PlayerAchievement;
import com.example.cit.domain.achievement.playerAchievement.repository.PlayerAchievementRepository;
import com.example.cit.domain.gameMap.gameMap.dto.GameMapDto;
import com.example.cit.domain.log.gameLog.detail.killCountLog.entity.KillCountLog;
import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.player.inventroy.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class PlayerAchievementService {

    private final PlayerAchievementRepository playerAchievementRepository;
    private final AchievementService achievementService;

    @Transactional
    public void checkStageClearAchievement(Member member, GameMapDto gameMapDto) {
        Optional<Achievement> opAchievement = achievementService.getAchievement("STAGE CLEAR", gameMapDto.id());
        opAchievement.ifPresent(achievement -> this.addAchievementToPlayerIfEmpty(member, achievement));
    }

    @Transactional
    public void checkPlayerLevelAchievement(Member member) {
        int level = 1;
        int requiredExp = 6;
        int exp = member.getPlayer().getExp();

        while (exp >= requiredExp) {
            exp -= requiredExp;
            level++;
            requiredExp += 6;
        }

        Optional<Achievement> opAchievement = achievementService.getAchievementLessThanCondition("PLAYER LEVEL", level);
        opAchievement.ifPresent(achievement -> this.addAchievementToPlayerIfEmpty(member, achievement));
    }

    @Transactional
    public void checkPurchaseEquipmentAchievement(Member member) {

        long count = member.getPlayer().getInventories().stream()
                .filter(inventory -> inventory.getItem().getPrice() > 0)
                .count();

        Optional<Achievement> opAchievement = achievementService.getAchievementLessThanCondition("PURCHASE EQUIPMENT", (int) count);
        opAchievement.ifPresent(achievement -> this.addAchievementToPlayerIfEmpty(member, achievement));
    }

    @Transactional
    public void checkPurchaseProfileIconAchievement(Member member) {

        long count = member.getPlayer().getProfileInventories().stream()
                .filter(profileInventory -> profileInventory.getProfileIcon().getPrice() > 0)
                .count();

        Optional<Achievement> opAchievement = achievementService.getAchievementLessThanCondition("PURCHASE ICON", (int) count);
        opAchievement.ifPresent(achievement -> this.addAchievementToPlayerIfEmpty(member, achievement));
    }

    @Transactional
    public void checkKillCountAchievement(Member member, KillCountLog killCountLog) {
        Optional<Achievement> opAchievementNormalKill = achievementService.getAchievementLessThanCondition("COUNT NORMAL", killCountLog.getNormal_count());
        Optional<Achievement> opAchievementBossKill = achievementService.getAchievementLessThanCondition("COUNT BOSS", killCountLog.getBoss_count());

        opAchievementNormalKill.ifPresent(achievement -> this.addAchievementToPlayerIfEmpty(member, achievement));
        opAchievementBossKill.ifPresent(achievement -> this.addAchievementToPlayerIfEmpty(member, achievement));
    }

    @Transactional
    public void addAchievementToPlayerIfEmpty(Member member, Achievement achievement) {
        if(playerAchievementRepository.findByPlayerIdAndAchievementId(member.getPlayer().getId(), achievement.getId()).isEmpty()) {
            PlayerAchievement playerAchievement = PlayerAchievement.builder()
                    .player(member.getPlayer())
                    .achievement(achievement)
                    .build();

            playerAchievementRepository.save(playerAchievement);
        }
    }

    @Transactional
    public void updateGetReward(Member member, AchievementDto achievement) {
        playerAchievementRepository.findByPlayerIdAndAchievementId(member.getPlayer().getId(), achievement.id())
                .ifPresent(playerAchievement -> {
                    playerAchievement.setGetReward(true);
                    playerAchievementRepository.save(playerAchievement);
                });
    }
}
