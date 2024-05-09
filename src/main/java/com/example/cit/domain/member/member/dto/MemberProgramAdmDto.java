package com.example.cit.domain.member.member.dto;

import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.program.program.dto.ProgramDto;
import com.example.cit.domain.program.program.entity.Program;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.lang.NonNull;

import java.time.LocalDateTime;
import java.util.List;

import static lombok.AccessLevel.PROTECTED;

@NoArgsConstructor(access = PROTECTED)
@Getter
public class MemberProgramAdmDto {
    @NonNull
    private long id;
    @NonNull
    private LocalDateTime createDate;
    @NonNull
    private LocalDateTime modifyDate;
    @NonNull
    private String username;
    @NonNull
    private String name;
    @NonNull
    private String cellphoneNo;
    @NonNull
    private List<String> authorities;
    @NotNull
    private String department;
    @NotNull
    private String position;
    @NotNull
    private String extensionNo;
    @NotNull
    private List<ProgramDto> responsibilities;

    public MemberProgramAdmDto(Member member, List<ProgramDto> responsibilities) {
        this.id = member.getId();
        this.createDate = member.getCreateDate();
        this.modifyDate = member.getModifyDate();
        this.username = member.getUsername();
        this.name = member.getName();
        this.cellphoneNo = member.getCellphoneNo();
        this.authorities = member.getAuthoritiesAsStringList();
        this.department = member.getDepartment();
        this.position = member.getPosition();
        this.extensionNo = member.getExtensionNo();
        this.responsibilities = responsibilities;
    }
}