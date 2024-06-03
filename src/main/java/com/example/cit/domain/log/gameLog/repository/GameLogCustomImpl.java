package com.example.cit.domain.log.gameLog.repository;

import com.example.cit.domain.log.gameLog.detail.executionLog.entity.QExecutionLog;
import com.example.cit.domain.log.gameLog.entity.GameLog;
import com.example.cit.domain.log.gameLog.entity.QGameLog;
import com.example.cit.domain.member.member.entity.QMember;
import com.example.cit.domain.program.program.entity.QProgram;
import com.example.cit.domain.school.school.entity.QSchool;
import com.example.cit.domain.school.schoolClass.entity.QSchoolClass;
import com.querydsl.core.BooleanBuilder;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.time.LocalDateTime;
import java.util.List;

@RequiredArgsConstructor
public class GameLogCustomImpl implements GameLogCustom {

    private final JPAQueryFactory queryFactory;
    private final EntityManager entityManager;

    @Override
    public List<GameLog> findStatLogs(long programId, long schoolId, int grade, LocalDateTime startDate, LocalDateTime endDate) {
        QGameLog gameLog = QGameLog.gameLog;
        QMember member = QMember.member;
        QSchoolClass schoolClass = QSchoolClass.schoolClass;
        QSchool school = QSchool.school;
        QProgram program = QProgram.program;

        BooleanBuilder whereClause = new BooleanBuilder();
        whereClause.and(programIdCondition(programId, program))
                .and(schoolIdCondition(schoolId, schoolClass))
                .and(gradeAndClassNoCondition(grade, schoolClass))
                .and(dateCondition(startDate, endDate, gameLog))
                .and(gameLog.executionLog.isNotNull());

        return queryFactory
                .selectDistinct(gameLog)
                .from(gameLog)
                .join(member).on(gameLog.userId.eq(member.id))
                .leftJoin(member.studentClass, schoolClass)
                .leftJoin(schoolClass.school, school)
                .leftJoin(school.programs, program)
                .where(whereClause)
                .fetch();
    }

    @Override
    public Page<GameLog> findStatLogs(long programId, long schoolId, int grade, LocalDateTime startDate, LocalDateTime endDate, Pageable pageable) {
        QGameLog gameLog = QGameLog.gameLog;
        QMember member = QMember.member;
        QSchoolClass schoolClass = QSchoolClass.schoolClass;
        QSchool school = QSchool.school;
        QProgram program = QProgram.program;

        BooleanBuilder whereClause = new BooleanBuilder();
        whereClause.and(programIdCondition(programId, program))
                .and(schoolIdCondition(schoolId, schoolClass))
                .and(gradeAndClassNoCondition(grade, schoolClass))
                .and(dateCondition(startDate, endDate, gameLog))
                .and(gameLog.executionLog.isNotNull());

        List<GameLog> content = queryFactory
                .selectDistinct(gameLog)
                .from(gameLog)
                .join(member).on(gameLog.userId.eq(member.id))
                .leftJoin(member.studentClass, schoolClass)
                .leftJoin(schoolClass.school, school)
                .leftJoin(school.programs, program)
                .where(whereClause)
                .offset(pageable.getOffset())
                .limit(pageable.getPageSize())
                .fetch();

        long total = queryFactory
                .selectDistinct(gameLog)
                .from(gameLog)
                .join(member).on(gameLog.userId.eq(member.id))
                .leftJoin(member.studentClass, schoolClass)
                .leftJoin(schoolClass.school, school)
                .leftJoin(school.programs, program)
                .where(whereClause)
                .fetch().size();

        return new PageImpl<>(content, pageable, total);
    }

    private BooleanExpression programIdCondition(long programId, QProgram program) {
        return programId != 0 ? program.id.eq(programId) : null;
    }

    private BooleanExpression schoolIdCondition(long schoolId, QSchoolClass schoolClass) {
        return schoolId != 0 ? schoolClass.school.id.eq(schoolId) : null;
    }

    private BooleanExpression gradeAndClassNoCondition(int grade, QSchoolClass schoolClass) {
        return grade != 0 ? schoolClass.grade.eq(grade) : null;
    }

    private BooleanExpression dateCondition(LocalDateTime startDate, LocalDateTime endDate, QGameLog gameLog) {
        if (startDate == null && endDate == null) {
            return null;
        } else if (startDate != null && endDate != null) {
            return gameLog.createDate.between(startDate, endDate);
        } else if (startDate != null) {
            return gameLog.createDate.after(startDate);
        } else {
            return gameLog.createDate.before(endDate);
        }
    }
}
