package com.example.cit.domain.achievement.dto;

import com.example.cit.domain.achievement.entity.Achievement;
import com.example.cit.domain.item.item.entity.Item;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public record AchievementDto(
        @NonNull Long id,
        @NonNull LocalDateTime createDate,
        @NonNull LocalDateTime updateDate,
        @NonNull String name,
        @NonNull String description,
        @NonNull String logType,
        @NonNull String detail1,
        @NonNull int detail2

) {
    public AchievementDto(Achievement achievement) {
        this(
                achievement.getId(),
                achievement.getCreateDate(),
                achievement.getModifyDate(),
                achievement.getName(),
                achievement.getDescription(),
                achievement.getLogType(),
                achievement.getDetail1(),
                achievement.getDetail2()
        );
    }
}
