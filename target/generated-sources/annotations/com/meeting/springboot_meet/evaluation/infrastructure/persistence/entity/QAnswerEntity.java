package com.meeting.springboot_meet.evaluation.infrastructure.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QAnswerEntity is a Querydsl query type for AnswerEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QAnswerEntity extends EntityPathBase<AnswerEntity> {

    private static final long serialVersionUID = 721008669L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QAnswerEntity answerEntity = new QAnswerEntity("answerEntity");

    public final QAttemptEntity attempt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final com.meeting.springboot_meet.exam.infrastructure.persistence.entity.QQuestionEntity question;

    public final SetPath<Long, NumberPath<Long>> selectedOptionIds = this.<Long, NumberPath<Long>>createSet("selectedOptionIds", Long.class, NumberPath.class, PathInits.DIRECT2);

    public QAnswerEntity(String variable) {
        this(AnswerEntity.class, forVariable(variable), INITS);
    }

    public QAnswerEntity(Path<? extends AnswerEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QAnswerEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QAnswerEntity(PathMetadata metadata, PathInits inits) {
        this(AnswerEntity.class, metadata, inits);
    }

    public QAnswerEntity(Class<? extends AnswerEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.attempt = inits.isInitialized("attempt") ? new QAttemptEntity(forProperty("attempt"), inits.get("attempt")) : null;
        this.question = inits.isInitialized("question") ? new com.meeting.springboot_meet.exam.infrastructure.persistence.entity.QQuestionEntity(forProperty("question"), inits.get("question")) : null;
    }

}

