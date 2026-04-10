package com.meeting.springboot_meet.classroom.infrastructure.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClassroomMemberEntity is a Querydsl query type for ClassroomMemberEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClassroomMemberEntity extends EntityPathBase<ClassroomMemberEntity> {

    private static final long serialVersionUID = 1443088925L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClassroomMemberEntity classroomMemberEntity = new QClassroomMemberEntity("classroomMemberEntity");

    public final QClassroomEntity classroom;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> joinedAt = createDateTime("joinedAt", java.time.Instant.class);

    public final EnumPath<ClassroomMemberEntity.MemberStatus> status = createEnum("status", ClassroomMemberEntity.MemberStatus.class);

    public final com.meeting.springboot_meet.auth.infrastructure.persistence.entity.QUserEntity user;

    public QClassroomMemberEntity(String variable) {
        this(ClassroomMemberEntity.class, forVariable(variable), INITS);
    }

    public QClassroomMemberEntity(Path<? extends ClassroomMemberEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClassroomMemberEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClassroomMemberEntity(PathMetadata metadata, PathInits inits) {
        this(ClassroomMemberEntity.class, metadata, inits);
    }

    public QClassroomMemberEntity(Class<? extends ClassroomMemberEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.classroom = inits.isInitialized("classroom") ? new QClassroomEntity(forProperty("classroom"), inits.get("classroom")) : null;
        this.user = inits.isInitialized("user") ? new com.meeting.springboot_meet.auth.infrastructure.persistence.entity.QUserEntity(forProperty("user")) : null;
    }

}

