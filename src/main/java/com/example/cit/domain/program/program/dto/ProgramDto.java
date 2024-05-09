package com.example.cit.domain.program.program.dto;

import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.player.player.dto.PlayerDto;
import com.example.cit.domain.program.program.entity.Program;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

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
    private List<Member> responsibleMembers;

    public ProgramDto(Program program) {
        this.id = program.getId();
        this.createDate = program.getCreateDate();
        this.modifyDate = program.getModifyDate();
        this.name = program.getName();
        this.startDate = program.getStartDate();
        this.endDate = program.getEndDate();
        this.city = program.getCity();
        this.administrativeDistrict = program.getAdministrativeDistrict();
        this.responsibleMembers = program.getMembers();
    }
}
