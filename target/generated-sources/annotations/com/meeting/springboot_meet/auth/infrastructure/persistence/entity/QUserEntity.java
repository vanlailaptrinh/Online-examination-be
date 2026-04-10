package com.meeting.springboot_meet.auth.infrastructure.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUserEntity is a Querydsl query type for UserEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUserEntity extends EntityPathBase<UserEntity> {

    private static final long serialVersionUID = -633584322L;

    public static final QUserEntity userEntity = new QUserEntity("userEntity");

    public final DateTimePath<java.time.Instant> createdAt = createDateTime("createdAt", java.time.Instant.class);

    public final StringPath email = createString("email");

    public final BooleanPath enabled = createBoolean("enabled");

    public final StringPath fullName = createString("fullName");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<RefreshTokenEntity, QRefreshTokenEntity> refreshTokens = this.<RefreshTokenEntity, QRefreshTokenEntity>createList("refreshTokens", RefreshTokenEntity.class, QRefreshTokenEntity.class, PathInits.DIRECT2);

    public final SetPath<com.meeting.springboot_meet.auth.domain.model.UserRole, EnumPath<com.meeting.springboot_meet.auth.domain.model.UserRole>> roles = this.<com.meeting.springboot_meet.auth.domain.model.UserRole, EnumPath<com.meeting.springboot_meet.auth.domain.model.UserRole>>createSet("roles", com.meeting.springboot_meet.auth.domain.model.UserRole.class, EnumPath.class, PathInits.DIRECT2);

    public final ListPath<UserProviderEntity, QUserProviderEntity> userProviders = this.<UserProviderEntity, QUserProviderEntity>createList("userProviders", UserProviderEntity.class, QUserProviderEntity.class, PathInits.DIRECT2);

    public QUserEntity(String variable) {
        super(UserEntity.class, forVariable(variable));
    }

    public QUserEntity(Path<? extends UserEntity> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUserEntity(PathMetadata metadata) {
        super(UserEntity.class, metadata);
    }

}

