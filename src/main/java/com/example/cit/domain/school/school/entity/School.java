package com.example.cit.domain.school.school.entity;

import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.player.player.entity.Player;
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
    private String name;
    private String location;
    private String phoneNo;

    @OneToMany(mappedBy = "school", cascade = ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<SchoolClass> schoolClasses = new ArrayList<>();

    @ManyToMany(fetch = LAZY)
    @ToString.Exclude
    @Builder.Default
    private List<Member> members = new ArrayList<>();

}
