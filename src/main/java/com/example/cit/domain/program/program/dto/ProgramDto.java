package com.example.cit.domain.program.program.dto;

import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.player.player.dto.PlayerDto;
import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.domain.school.school.entity.School;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

import static com.example.cit.domain.school.school.entity.QSchool.school;
import static lombok.AccessLevel.PROTECTED;

// 사업 관련 기본 정보 dto
@NoArgsConstructor(access = PROTECTED)
@Getter
public class ProgramDto {
    @NonNull
    private long id;
    @NonNull
    private LocalDateTime createDate;
    @NonNull
    private LocalDateTime modifyDate;
    @NonNull
    private String name;
    @NonNull
    private LocalDate startDate;
    @NonNull
    private LocalDate endDate;
    @NonNull
    private String city;
    @NonNull
    private String administrativeDistrict;
    @NonNull
    private List<String> responsibleMemberNames;
    @NonNull
    private List<String> schoolsNames;

    public ProgramDto(Program program) {
        this.id = program.getId();
        this.createDate = program.getCreateDate();
        this.modifyDate = program.getModifyDate();
        this.name = program.getName();
        this.startDate = program.getStartDate();
        this.endDate = program.getEndDate();
        this.city = program.getCity();
        this.administrativeDistrict = program.getAdministrativeDistrict();
        this.responsibleMemberNames = program.getMembers().stream()
                .map(Member::getName)
                .collect(Collectors.toList());
        this.schoolsNames = program.getSchools().stream()
                .map(School::getSchoolName)
                .collect(Collectors.toList());
    }
}
