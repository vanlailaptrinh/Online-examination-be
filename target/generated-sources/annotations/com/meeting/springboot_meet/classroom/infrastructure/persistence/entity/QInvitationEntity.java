package com.meeting.springboot_meet.classroom.infrastructure.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QInvitationEntity is a Querydsl query type for InvitationEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QInvitationEntity extends EntityPathBase<InvitationEntity> {

    private static final long serialVersionUID = -692809809L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QInvitationEntity invitationEntity = new QInvitationEntity("invitationEntity");

    public final QClassroomEntity classroom;

    public final DateTimePath<java.time.Instant> createdAt = createDateTime("createdAt", java.time.Instant.class);

    public final StringPath email = createString("email");

    public final DateTimePath<java.time.Instant> expiresAt = createDateTime("expiresAt", java.time.Instant.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath token = createString("token");

    public final BooleanPath used = createBoolean("used");

    public QInvitationEntity(String variable) {
        this(InvitationEntity.class, forVariable(variable), INITS);
    }

    public QInvitationEntity(Path<? extends InvitationEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QInvitationEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QInvitationEntity(PathMetadata metadata, PathInits inits) {
        this(InvitationEntity.class, metadata, inits);
    }

    public QInvitationEntity(Class<? extends InvitationEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.classroom = inits.isInitialized("classroom") ? new QClassroomEntity(forProperty("classroom"), inits.get("classroom")) : null;
    }

}

