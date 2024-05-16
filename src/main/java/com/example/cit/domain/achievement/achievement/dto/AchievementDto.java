package com.example.cit.domain.achievement.achievement.dto;

import com.example.cit.domain.achievement.achievement.entity.Achievement;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public record AchievementDto(
        @NonNull Long id,
        @NonNull LocalDateTime createDate,
        @NonNull String name,
        @NonNull String description,
        @NonNull int rewardExp,
        @NonNull int rewardJewel,
        String rewardIconSource,
        int getReward,
        int isAchieved
) {
    public AchievementDto(Achievement achievement, LocalDateTime createDate, int getReward, int isAchieved) {
        this(
                achievement.getId(),
                createDate,
                achievement.getName(),
                achievement.getDescription(),
                achievement.getRewardExp(),
                achievement.getRewardJewel(),
                achievement.getRewardIcon() != null ? achievement.getRewardIcon().getSourcePath() : null,
                getReward,
                isAchieved
        );
    }
}
