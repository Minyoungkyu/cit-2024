package com.example.cit.domain.program.program.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProgram is a Querydsl query type for Program
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProgram extends EntityPathBase<Program> {

    private static final long serialVersionUID = -505050810L;

    public static final QProgram program = new QProgram("program");

    public final com.example.cit.global.jpa.base.QBaseTime _super = new com.example.cit.global.jpa.base.QBaseTime(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createDate = _super.createDate;

    //inherited
    public final NumberPath<Long> id = _super.id;

    public final ListPath<com.example.cit.domain.member.member.entity.Member, com.example.cit.domain.member.member.entity.QMember> members = this.<com.example.cit.domain.member.member.entity.Member, com.example.cit.domain.member.member.entity.QMember>createList("members", com.example.cit.domain.member.member.entity.Member.class, com.example.cit.domain.member.member.entity.QMember.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifyDate = _super.modifyDate;

    public final StringPath name = createString("name");

    public final ListPath<com.example.cit.domain.school.school.entity.School, com.example.cit.domain.school.school.entity.QSchool> schools = this.<com.example.cit.domain.school.school.entity.School, com.example.cit.domain.school.school.entity.QSchool>createList("schools", com.example.cit.domain.school.school.entity.School.class, com.example.cit.domain.school.school.entity.QSchool.class, PathInits.DIRECT2);

    public QProgram(String variable) {
        super(Program.class, forVariable(variable));
    }

    public QProgram(Path<? extends Program> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProgram(PathMetadata metadata) {
        super(Program.class, metadata);
    }

}
