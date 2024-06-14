package com.example.cit.domain.item.profileIcon.dto;

import com.example.cit.domain.item.profileIcon.entity.ProfileIcon;
import org.springframework.lang.NonNull;

// 게임 플레이어 프로필 아이콘 보상용 DTO

public record ProfileRewardDto(
        @NonNull Long id,
        @NonNull String name,
        @NonNull String sourcePath
) {
    public ProfileRewardDto(ProfileIcon ProfileIcon) {
        this(
                ProfileIcon.getId(),
                ProfileIcon.getName(),
                ProfileIcon.getSourcePath()
        );
    }
}