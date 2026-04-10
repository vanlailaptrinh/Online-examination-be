package com.meeting.springboot_meet.classroom.infrastructure.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClassroomEntity is a Querydsl query type for ClassroomEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClassroomEntity extends EntityPathBase<ClassroomEntity> {

    private static final long serialVersionUID = -1220514077L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClassroomEntity classroomEntity = new QClassroomEntity("classroomEntity");

    public final DateTimePath<java.time.Instant> createdAt = createDateTime("createdAt", java.time.Instant.class);

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final SetPath<ClassroomMemberEntity, QClassroomMemberEntity> members = this.<ClassroomMemberEntity, QClassroomMemberEntity>createSet("members", ClassroomMemberEntity.class, QClassroomMemberEntity.class, PathInits.DIRECT2);

    public final StringPath name = createString("name");

    public final com.meeting.springboot_meet.auth.infrastructure.persistence.entity.QUserEntity teacher;

    public QClassroomEntity(String variable) {
        this(ClassroomEntity.class, forVariable(variable), INITS);
    }

    public QClassroomEntity(Path<? extends ClassroomEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClassroomEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClassroomEntity(PathMetadata metadata, PathInits inits) {
        this(ClassroomEntity.class, metadata, inits);
    }

    public QClassroomEntity(Class<? extends ClassroomEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.teacher = inits.isInitialized("teacher") ? new com.meeting.springboot_meet.auth.infrastructure.persistence.entity.QUserEntity(forProperty("teacher")) : null;
    }

}

