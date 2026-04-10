package com.meeting.springboot_meet.evaluation.infrastructure.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAttemptEntity is a Querydsl query type for AttemptEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAttemptEntity extends EntityPathBase<AttemptEntity> {

    private static final long serialVersionUID = 209813940L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAttemptEntity attemptEntity = new QAttemptEntity("attemptEntity");

    public final ListPath<AnswerEntity, QAnswerEntity> answers = this.<AnswerEntity, QAnswerEntity>createList("answers", AnswerEntity.class, QAnswerEntity.class, PathInits.DIRECT2);

    public final com.meeting.springboot_meet.exam.infrastructure.persistence.entity.QExamEntity exam;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final NumberPath<Double> score = createNumber("score", Double.class);

    public final DateTimePath<java.time.Instant> startTime = createDateTime("startTime", java.time.Instant.class);

    public final com.meeting.springboot_meet.auth.infrastructure.persistence.entity.QUserEntity student;

    public final DateTimePath<java.time.Instant> submitTime = createDateTime("submitTime", java.time.Instant.class);

    public QAttemptEntity(String variable) {
        this(AttemptEntity.class, forVariable(variable), INITS);
    }

    public QAttemptEntity(Path<? extends AttemptEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAttemptEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAttemptEntity(PathMetadata metadata, PathInits inits) {
        this(AttemptEntity.class, metadata, inits);
    }

    public QAttemptEntity(Class<? extends AttemptEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.exam = inits.isInitialized("exam") ? new com.meeting.springboot_meet.exam.infrastructure.persistence.entity.QExamEntity(forProperty("exam"), inits.get("exam")) : null;
        this.student = inits.isInitialized("student") ? new com.meeting.springboot_meet.auth.infrastructure.persistence.entity.QUserEntity(forProperty("student")) : null;
    }

}

