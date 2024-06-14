package com.example.cit.domain.item.profileIcon.entity;

import com.example.cit.domain.achievement.achievement.entity.Achievement;
import com.example.cit.global.jpa.base.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

//플레이어 프로필 아이콘 jpa 엔티티
@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@Setter
public class ProfileIcon extends BaseTime {

    @OneToOne(fetch = LAZY)
    private Achievement achievement;

    private String name;
    private String description;
    private String sourcePath;
    private int price;
}