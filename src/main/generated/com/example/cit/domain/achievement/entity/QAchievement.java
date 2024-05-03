package com.example.cit.domain.achievement.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;


/**
 * QAchievement is a Querydsl query type for Achievement
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAchievement extends EntityPathBase<Achievement> {

    private static final long serialVersionUID = -2081649348L;

    public static final QAchievement achievement = new QAchievement("achievement");

    public final com.example.cit.global.jpa.base.QBaseTime _super = new com.example.cit.global.jpa.base.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    public final StringPath description = createString("description");

    public final StringPath detail1 = createString("detail1");

    public final NumberPath<Integer> detail2 = createNumber("detail2", Integer.class);

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final StringPath logType = createString("logType");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final StringPath name = createString("name");

    public QAchievement(String variable) {
        super(Achievement.class, forVariable(variable));
    }

    public QAchievement(Path<? extends Achievement> path) {
        super(path.getType(), path.getMetadata());
    }

    public QAchievement(PathMetadata metadata) {
        super(Achievement.class, metadata);
    }

}

