package com.example.cit.domain.player.player.entity;

import com.example.cit.domain.member.member.entity.Member;
import com.example.cit.domain.stage.stage.entity.Stage;
import com.example.cit.domain.stage.stageRecord.entity.StageRecord;
import com.example.cit.global.jpa.base.BaseTime;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.validation.constraints.NotBlank;
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

    @OneToOne(fetch = LAZY, cascade = ALL)
    @ToString.Exclude
    private Member member;

    @OneToMany(fetch = LAZY, mappedBy = "player")
    @ToString.Exclude
    @Builder.Default
    private List<StageRecord> stageRecords = new ArrayList<>();

    @ManyToOne(fetch = LAZY)
    private Stage lastPlayedStage;
}
