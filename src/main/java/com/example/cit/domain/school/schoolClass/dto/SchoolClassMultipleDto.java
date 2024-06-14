package com.example.cit.domain.school.schoolClass.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import static lombok.AccessLevel.PROTECTED;

// 학급 일괄 생성을 위한 dto
@NoArgsConstructor(access = PROTECTED)
@Getter
public class SchoolClassMultipleDto {
    @NonNull
    private long id;
    @NonNull
    private int grade;
    @NonNull
    private String classNoMultiple;
    @NonNull
    private long memberId;
}
