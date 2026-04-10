package com.meeting.springboot_meet.exam.infrastructure.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QQuestionEntity is a Querydsl query type for QuestionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QQuestionEntity extends EntityPathBase<QuestionEntity> {

    private static final long serialVersionUID = 780606338L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QQuestionEntity questionEntity = new QQuestionEntity("questionEntity");

    public final StringPath content = createString("content");

    public final QExamEntity exam;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<OptionEntity, QOptionEntity> options = this.<OptionEntity, QOptionEntity>createList("options", OptionEntity.class, QOptionEntity.class, PathInits.DIRECT2);

    public final EnumPath<QuestionEntity.QuestionType> type = createEnum("type", QuestionEntity.QuestionType.class);

    public QQuestionEntity(String variable) {
        this(QuestionEntity.class, forVariable(variable), INITS);
    }

    public QQuestionEntity(Path<? extends QuestionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QQuestionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QQuestionEntity(PathMetadata metadata, PathInits inits) {
        this(QuestionEntity.class, metadata, inits);
    }

    public QQuestionEntity(Class<? extends QuestionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.exam = inits.isInitialized("exam") ? new QExamEntity(forProperty("exam"), inits.get("exam")) : null;
    }

}

