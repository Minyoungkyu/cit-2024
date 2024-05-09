package com.example.cit.domain.school.school.dto;

import com.example.cit.domain.school.school.entity.School;
import jakarta.validation.constraints.NotNull;

public record SchoolDto(
        long id,
        String region,
        String administrativeDistrict,
        String schoolLevel,
        String highSchoolType,
        String schoolName,
        String establishmentType,
        String coeducationType,
        String areaType,
        String address,
        String phoneNumber
) {

    public SchoolDto(School school) {
        this(
                school.getId(),
                school.getRegion(),
                school.getAdministrativeDistrict(),
                school.getSchoolLevel(),
                school.getHighSchoolType(),
                school.getSchoolName(),
                school.getEstablishmentType(),
                school.getCoeducationType(),
                school.getAreaType(),
                school.getAddress(),
                school.getPhoneNumber()
        );
    }

}

