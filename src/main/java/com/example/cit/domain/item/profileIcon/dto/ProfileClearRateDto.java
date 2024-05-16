package com.example.cit.domain.item.profileIcon.dto;

import org.springframework.lang.NonNull;

public record ProfileClearRateDto(
        @NonNull String difficulty,
        @NonNull long clearCount
) {
}
