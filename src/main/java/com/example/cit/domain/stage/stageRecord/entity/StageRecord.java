package com.example.cit.domain.stage.stageRecord.entity;

import com.example.cit.domain.player.player.entity.Player;
import com.example.cit.domain.stage.stage.entity.Stage;
import com.example.cit.global.jpa.base.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import static jakarta.persistence.FetchType.LAZY;
import static lombok.AccessLevel.PROTECTED;

@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@Setter
public class StageRecord extends BaseTime {

    @Column(columnDefinition = "TEXT")
    private String codeRecord;
    private boolean isClear;
    private int durationTime;
    private int tryCount;

    @ManyToOne(fetch = LAZY)
    private Stage stage;

    @ManyToOne(fetch = LAZY)
    private Player player;
}
