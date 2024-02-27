package com.example.cit.domain.stage.stage.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStage is a Querydsl query type for Stage
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStage extends EntityPathBase<Stage> {

    private static final long serialVersionUID = -420576180L;

    public static final QStage stage = new QStage("stage");

    public final com.example.cit.global.jpa.base.QBaseTime _super = new com.example.cit.global.jpa.base.QBaseTime(this);

    public final NumberPath<Integer> clearExp = createNumber("clearExp", Integer.class);

    public final StringPath clearGoal = createString("clearGoal");

    public final NumberPath<Integer> clearJewel = createNumber("clearJewel", Integer.class);

    public final StringPath cocosInfo = createString("cocosInfo");

    public final StringPath commandGuide = createString("commandGuide");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath difficulty = createString("difficulty");

    public final StringPath editorAutoComplete = createString("editorAutoComplete");

    public final StringPath editorMessage = createString("editorMessage");

    public final StringPath guideImage = createString("guideImage");

    public final StringPath guideText = createString("guideText");

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final NumberPath<Integer> level = createNumber("level", Integer.class);

    public final StringPath map = createString("map");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final StringPath section = createString("section");

    public final ListPath<com.example.cit.domain.stage.stageRecord.entity.StageRecord, com.example.cit.domain.stage.stageRecord.entity.QStageRecord> stageRecords = this.<com.example.cit.domain.stage.stageRecord.entity.StageRecord, com.example.cit.domain.stage.stageRecord.entity.QStageRecord>createList("stageRecords", com.example.cit.domain.stage.stageRecord.entity.StageRecord.class, com.example.cit.domain.stage.stageRecord.entity.QStageRecord.class, PathInits.DIRECT2);

    public QStage(String variable) {
        super(Stage.class, forVariable(variable));
    }

    public QStage(Path<? extends Stage> path) {
        super(path.getType(), path.getMetadata());
    }

    public QStage(PathMetadata metadata) {
        super(Stage.class, metadata);
    }

}

