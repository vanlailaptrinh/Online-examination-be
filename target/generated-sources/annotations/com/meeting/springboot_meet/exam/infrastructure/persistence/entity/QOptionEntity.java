package com.meeting.springboot_meet.exam.infrastructure.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOptionEntity is a Querydsl query type for OptionEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOptionEntity extends EntityPathBase<OptionEntity> {

    private static final long serialVersionUID = 825864977L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOptionEntity optionEntity = new QOptionEntity("optionEntity");

    public final StringPath content = createString("content");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isCorrect = createBoolean("isCorrect");

    public final QQuestionEntity question;

    public QOptionEntity(String variable) {
        this(OptionEntity.class, forVariable(variable), INITS);
    }

    public QOptionEntity(Path<? extends OptionEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOptionEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOptionEntity(PathMetadata metadata, PathInits inits) {
        this(OptionEntity.class, metadata, inits);
    }

    public QOptionEntity(Class<? extends OptionEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.question = inits.isInitialized("question") ? new QQuestionEntity(forProperty("question"), inits.get("question")) : null;
    }

}

