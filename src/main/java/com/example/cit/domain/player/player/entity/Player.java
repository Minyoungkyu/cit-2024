package com.example.cit.domain.player.player.entity;

import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.global.jpa.base.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import static jakarta.persistence.CascadeType.ALL;
import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@Setter
public class Player extends BaseTime {
    @NotNull
    private String nickname;

    @OneToOne(fetch = LAZY, cascade = ALL)
    @ToString.Exclude
    private Member member;

}
