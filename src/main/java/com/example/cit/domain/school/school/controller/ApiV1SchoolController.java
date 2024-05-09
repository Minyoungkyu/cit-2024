package com.example.cit.domain.school.school.controller;

import com.example.cit.domain.school.school.dto.SchoolDto;
import com.example.cit.domain.school.school.entity.School;
import com.example.cit.domain.school.school.service.SchoolService;
import com.example.cit.global.rsData.RsData;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping(value = "/api/v1/schools", consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
@Tag(name = "ApiV1SchoolController", description = "학교관리 컨트롤러")
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ApiV1SchoolController {

    private final SchoolService schoolService;

    public record CreateSchoolRequestBody(
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
    ) {}

    public record CreateSchoolResponseBody(SchoolDto schoolDto) {}

    @PostMapping(value = "/createSchool")
    @Operation(summary = "관리자 - 학교생성")
    @PreAuthorize("hasRole('ROLE_SYSTEMADMIN')")
    @Transactional
    public RsData<CreateSchoolResponseBody> createSchool(@Valid @RequestBody CreateSchoolRequestBody body) {

        School school = schoolService.createSchool(
                body.region,
                body.administrativeDistrict,
                body.schoolLevel,
                body.highSchoolType,
                body.schoolName,
                body.establishmentType,
                body.coeducationType,
                body.areaType,
                body.address,
                body.phoneNumber
        );

        return RsData.of(
                "%s 가 생성되었습니다.".formatted(body.schoolName),
                new CreateSchoolResponseBody(
                        new SchoolDto(school)
                )
        );
    }


}
