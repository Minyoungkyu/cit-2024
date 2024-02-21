package com.example.cit.domain.school.school.dto;

import com.example.cit.domain.school.school.entity.School;
import jakarta.validation.constraints.NotNull;

public record SchoolDto(
        @NotNull long id,
        @NotNull String name,
        @NotNull String location,
        @NotNull String phoneNo
) {

    public SchoolDto(School school) {
        this(school.getId(), school.getName(), school.getLocation(), school.getPhoneNo());
    }

}

