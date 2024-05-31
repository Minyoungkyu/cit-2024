package com.example.cit.domain.school.schoolClass.entity;

import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.school.school.entity.School;
import com.example.cit.global.jpa.base.BaseTime;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@Setter
public class SchoolClass extends BaseTime {
    @Column(unique = true)
    private String code;

    //학년
    private int grade;
    //반
    private int classNo;
    //특수반 여부
    private boolean isSpecial;
    //특수반 이름
    private String specialName;

    @ManyToOne(fetch = LAZY)
    private School school;

    @ManyToMany(fetch = LAZY, mappedBy = "schoolClasses")
    @ToString.Exclude
    @Builder.Default
    private List<Member> members = new ArrayList<>();

    @OneToMany(fetch = LAZY)
    @ToString.Exclude
    @Builder.Default
    private List<Member> players = new ArrayList<>();

    @OneToMany(fetch = LAZY, mappedBy = "studentClass")
    @ToString.Exclude
    @Builder.Default
    private Set<Member> students = new HashSet<>();
}