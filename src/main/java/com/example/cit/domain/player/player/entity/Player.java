package com.example.cit.domain.player.player.entity;

import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.player.inventroy.entity.Inventory;
import com.example.cit.global.jpa.base.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.util.ArrayList;
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
public class Player extends BaseTime {
    @NotNull
    private String nickname;
    @NotNull
    private int exp;
    @NotNull
    private int gems;

    @OneToOne(fetch = LAZY, cascade = ALL)
    @ToString.Exclude
    private Member member;

    @OneToMany(mappedBy = "player", cascade = ALL, orphanRemoval = true)
    @ToString.Exclude
    @Builder.Default
    private List<Inventory> inventories = new ArrayList<>();
}
