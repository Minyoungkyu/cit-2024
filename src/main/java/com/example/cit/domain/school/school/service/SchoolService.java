package com.example.cit.domain.school.school.service;

import com.example.cit.domain.school.school.SchoolRepository;
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
    public School createSchool(String name, String location, String phoneNo) {

        School school = School.builder()
                .name(name)
                .location(location)
                .phoneNo(phoneNo)
                .build();

        schoolRepository.save(school);

        return school;
    }
}
