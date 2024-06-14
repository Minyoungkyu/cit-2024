package com.example.cit.domain.school.school.dto;

import com.example.cit.domain.school.school.entity.School;
import jakarta.validation.constraints.NotNull;

//사용기관의 학교 list 를 위한 dto
public record SchoolInputListDto(
        long id,
        String region,
        String administrativeDistrict,
        String schoolName
) {

    public SchoolInputListDto(School school) {
        this(
                school.getId(),
                school.getRegion(),
                school.getAdministrativeDistrict(),
                school.getSchoolName()
        );
    }

    public static String getName(School school) {
        return school.getSchoolName();
    }
}

