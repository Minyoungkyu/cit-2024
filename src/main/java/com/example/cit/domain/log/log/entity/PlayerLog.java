package com.example.cit.domain.log.log.entity;

import com.example.cit.global.jpa.base.BaseTime;
import jakarta.persistence.Entity;
import lombok.*;

import static lombok.AccessLevel.PROTECTED;

// 플레이어 해금 추적 및 클리어 점수를 관리하는 로그 jpa entity
@Entity
@NoArgsConstructor(access = PROTECTED)
@AllArgsConstructor(access = PROTECTED)
@Builder
@Getter
@Setter
public class PlayerLog extends BaseTime {

    private String logType;
    private String username;
    private Long userId;
    private Long gameMapId;
    private String gameMapStage;
    private String gameMapStep;
    private String gameMapDifficulty;
    private Integer gameMapLevel;
    private String detailText;
    private Integer detailInt;

}
