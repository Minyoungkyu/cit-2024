package com.example.cit.domain.stage.stageRecord.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QStageRecord is a Querydsl query type for StageRecord
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QStageRecord extends EntityPathBase<StageRecord> {

    private static final long serialVersionUID = 915502956L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QStageRecord stageRecord = new QStageRecord("stageRecord");

    public final com.example.cit.global.jpa.base.QBaseTime _super = new com.example.cit.global.jpa.base.QBaseTime(this);

    public final StringPath codeRecord = createString("codeRecord");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final NumberPath<Integer> durationTime = createNumber("durationTime", Integer.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final BooleanPath isClear = createBoolean("isClear");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final com.example.cit.domain.player.player.entity.QPlayer player;

    public final com.example.cit.domain.stage.stage.entity.QStage stage;

    public final NumberPath<Integer> tryCount = createNumber("tryCount", Integer.class);

    public QStageRecord(String variable) {
        this(StageRecord.class, forVariable(variable), INITS);
    }

    public QStageRecord(Path<? extends StageRecord> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QStageRecord(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QStageRecord(PathMetadata metadata, PathInits inits) {
        this(StageRecord.class, metadata, inits);
    }

    public QStageRecord(Class<? extends StageRecord> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.player = inits.isInitialized("player") ? new com.example.cit.domain.player.player.entity.QPlayer(forProperty("player"), inits.get("player")) : null;
        this.stage = inits.isInitialized("stage") ? new com.example.cit.domain.stage.stage.entity.QStage(forProperty("stage")) : null;
    }

}

