package com.example.cit.domain.school.school.service;

import com.example.cit.domain.school.school.repository.SchoolRepository;
import com.example.cit.domain.school.school.entity.School;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class SchoolService {

    private final SchoolRepository schoolRepository;

    @Transactional
    public School createSchool(String region, String administrativeDistrict, String schoolLevel, String HighSchoolType, String schoolName,
                               String establishmentType, String coeducationType, String areaType, String address, String phoneNumber) {

        School school = School.builder()
                .region(region)
                .administrativeDistrict(administrativeDistrict)
                .schoolLevel(schoolLevel)
                .highSchoolType(HighSchoolType)
                .schoolName(schoolName)
                .establishmentType(establishmentType)
                .coeducationType(coeducationType)
                .areaType(areaType)
                .address(address)
                .phoneNumber(phoneNumber)
                .build();

        schoolRepository.save(school);

        return school;
    }
}
