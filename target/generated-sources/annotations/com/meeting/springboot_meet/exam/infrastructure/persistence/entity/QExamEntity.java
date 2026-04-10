package com.meeting.springboot_meet.exam.infrastructure.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QExamEntity is a Querydsl query type for ExamEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QExamEntity extends EntityPathBase<ExamEntity> {

    private static final long serialVersionUID = 1707261307L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QExamEntity examEntity = new QExamEntity("examEntity");

    public final DateTimePath<java.time.Instant> createdAt = createDateTime("createdAt", java.time.Instant.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<QuestionEntity, QQuestionEntity> questions = this.<QuestionEntity, QQuestionEntity>createList("questions", QuestionEntity.class, QQuestionEntity.class, PathInits.DIRECT2);

    public final BooleanPath showResultToStudent = createBoolean("showResultToStudent");

    public final com.meeting.springboot_meet.auth.infrastructure.persistence.entity.QUserEntity teacher;

    public final StringPath title = createString("title");

    public QExamEntity(String variable) {
        this(ExamEntity.class, forVariable(variable), INITS);
    }

    public QExamEntity(Path<? extends ExamEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QExamEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QExamEntity(PathMetadata metadata, PathInits inits) {
        this(ExamEntity.class, metadata, inits);
    }

    public QExamEntity(Class<? extends ExamEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.teacher = inits.isInitialized("teacher") ? new com.meeting.springboot_meet.auth.infrastructure.persistence.entity.QUserEntity(forProperty("teacher")) : null;
    }

}

