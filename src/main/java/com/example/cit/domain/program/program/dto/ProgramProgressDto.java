package com.example.cit.domain.program.program.dto;

import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.domain.school.school.dto.SchoolProgressDto;
import com.example.cit.domain.school.school.entity.School;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

// 사업 진행률 관련 dto

public record ProgramProgressDto (
        long id,
        String programName,
        String memberNames,
        LocalDate startDate,
        LocalDate endDate,
        List<SchoolProgressDto> schoolProgressDtoList
) {
    public ProgramProgressDto(Program program, List<SchoolProgressDto> schoolProgressDtoList) {
        this(
                program.getId(),
                program.getName(),
                program.getMembers().stream().map(Member::getName).collect(Collectors.joining(", ")),
                program.getStartDate(),
                program.getEndDate(),
                (schoolProgressDtoList == null || schoolProgressDtoList.isEmpty()) ? null : schoolProgressDtoList
        );
    }
}
