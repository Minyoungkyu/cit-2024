package com.example.cit.domain.school.schoolClass.entity;

import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.school.school.entity.School;
import com.example.cit.global.jpa.base.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@Setter
public class SchoolClass extends BaseTime {
    private String name;
    @Column(unique = true)
    private String code;

    @ManyToOne
    private School school;

    @ManyToMany(fetch = LAZY)
    @ToString.Exclude
    @Builder.Default
    private List<Member> members = new ArrayList<>();
}