package com.example.cit.domain.item.profileIcon.dto;

import com.example.cit.domain.achievement.dto.AchievementDto;
import com.example.cit.domain.item.item.entity.Item;
import com.example.cit.domain.item.profileIcon.entity.ProfileIcon;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;

public record ProfileDto(
        @NonNull Long id,
        @NonNull LocalDateTime createDate,
        @NonNull LocalDateTime updateDate,
        @NonNull String name,
        @NonNull String description,
        @NonNull String sourcePath,
        @NonNull int price,
        AchievementDto achievement
) {
    public ProfileDto(ProfileIcon ProfileIcon) {
        this(
                ProfileIcon.getId(),
                ProfileIcon.getCreateDate(),
                ProfileIcon.getModifyDate(),
                ProfileIcon.getName(),
                ProfileIcon.getDescription(),
                ProfileIcon.getSourcePath(),
                ProfileIcon.getPrice(),
                ProfileIcon.getAchievement() != null ? new AchievementDto(ProfileIcon.getAchievement()) : null
        );
    }
}
