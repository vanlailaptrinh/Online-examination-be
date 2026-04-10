package com.meeting.springboot_meet.auth.infrastructure.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserProviderEntity is a Querydsl query type for UserProviderEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserProviderEntity extends EntityPathBase<UserProviderEntity> {

    private static final long serialVersionUID = 1846391695L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QUserProviderEntity userProviderEntity = new QUserProviderEntity("userProviderEntity");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath passwordHash = createString("passwordHash");

    public final EnumPath<com.meeting.springboot_meet.auth.domain.model.ProviderType> provider = createEnum("provider", com.meeting.springboot_meet.auth.domain.model.ProviderType.class);

    public final StringPath providerUserId = createString("providerUserId");

    public final QUserEntity user;

    public QUserProviderEntity(String variable) {
        this(UserProviderEntity.class, forVariable(variable), INITS);
    }

    public QUserProviderEntity(Path<? extends UserProviderEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QUserProviderEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QUserProviderEntity(PathMetadata metadata, PathInits inits) {
        this(UserProviderEntity.class, metadata, inits);
    }

    public QUserProviderEntity(Class<? extends UserProviderEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.user = inits.isInitialized("user") ? new QUserEntity(forProperty("user")) : null;
    }

}

