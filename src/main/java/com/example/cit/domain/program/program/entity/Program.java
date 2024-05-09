package com.example.cit.domain.program.program.entity;

import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.school.school.entity.School;
import com.example.cit.global.jpa.base.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;
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
public class Program extends BaseTime {

    @NotNull
    private String name;
    @NotNull
    private LocalDate startDate;
    @NotNull
    private LocalDate endDate;
    @NotNull
    private String city;
    @NotNull
    private String administrativeDistrict;

    @ManyToMany(fetch = LAZY)
    @Builder.Default
    @ToString.Exclude
    private List<Member> members = new ArrayList<>();

    @ManyToMany(fetch = LAZY)
    @Builder.Default
    @ToString.Exclude
    private List<School> schools = new ArrayList<>();





}
