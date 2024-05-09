package com.example.cit.domain.school.school.entity;

import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.player.player.entity.Player;
import com.example.cit.domain.program.program.entity.Program;
import com.example.cit.domain.school.schoolClass.entity.SchoolClass;
import com.example.cit.global.jpa.base.BaseTime;
import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@Setter
public class School extends BaseTime {
    private String region; // 시도
    private String administrativeDistrict; // 행정구
    private String schoolLevel; // 학교급 (초, 중, 고)
    private String highSchoolType; // 고등학교 유형 (일반, 특성화, 자율형, 마이스터고, 외국인학교)
    private String schoolName; // 학교명
    private String establishmentType; // 설립유형 (국립, 공립, 사립)
    private String coeducationType; // 남녀공학 (남, 여, 남녀공학)
    private String areaType; // 지역유형 (도심, 도외, 제2도심)
    private String address;
    private String phoneNumber;

    @OneToMany(mappedBy = "school", cascade = ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<SchoolClass> schoolClasses = new ArrayList<>();

    @ManyToMany(fetch = LAZY)
    @ToString.Exclude
    @Builder.Default
    private List<Member> members = new ArrayList<>();

    @ManyToMany(fetch = LAZY)
    @ToString.Exclude
    @Builder.Default
    private List<Program> programs = new ArrayList<>();

}
