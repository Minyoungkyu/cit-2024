package com.example.cit.domain.log.dto;

import com.example.cit.domain.achievement.achievement.dto.AchievementDto;
import com.example.cit.domain.gameMap.gameMap.dto.GameMapDto;
import com.example.cit.domain.gameMap.gameMap.entity.GameMap;
import com.example.cit.domain.item.profileIcon.dto.ProfileClearRateDto;
import com.example.cit.domain.item.profileIcon.entity.ProfileIcon;
import com.example.cit.domain.log.log.dto.PlayerLogDto;
import com.example.cit.domain.log.log.entity.PlayerLog;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import org.springframework.lang.NonNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;

import java.time.LocalDateTime;
import java.util.List;

public record ProfileLogDto(
        PlayerLogDto playerLogDto,
        @NonNull List<ProfileClearRateDto> profileClearRateDtoList
) {
}