package com.example.cit.domain.log.dto;

import com.example.cit.domain.item.profileIcon.dto.ProfileClearRateDto;
import com.example.cit.domain.log.log.dto.PlayerLogDto;
import org.springframework.lang.NonNull;

import java.util.List;

public record ProfileDetailLogDto(
        @NonNull long clearCount,
        @NonNull long executionCount
) {
}