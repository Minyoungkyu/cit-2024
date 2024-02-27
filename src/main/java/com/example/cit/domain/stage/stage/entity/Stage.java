package com.example.cit.domain.stage.stage.entity;

import com.example.cit.domain.stage.stageRecord.entity.StageRecord;
import com.example.cit.global.jpa.base.BaseTime;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
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
public class Stage extends BaseTime {

    private String map;
    private String section;
    private String difficulty;
    private int level;
    private String editorAutoComplete;
    private String editorMessage;
    private String clearGoal;
    private String cocosInfo;
    @Column(columnDefinition = "TEXT")
    private String guideText;
    private String guideImage;
    @Column(columnDefinition = "TEXT")
    private String commandGuide;
    private int clearExp;
    private int clearJewel;

    @OneToMany(fetch = LAZY, mappedBy = "stage")
    @ToString.Exclude
    @Builder.Default
    private List<StageRecord> stageRecords = new ArrayList<>();
}
