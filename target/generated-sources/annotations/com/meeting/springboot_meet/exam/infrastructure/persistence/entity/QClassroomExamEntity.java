package com.meeting.springboot_meet.exam.infrastructure.persistence.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QClassroomExamEntity is a Querydsl query type for ClassroomExamEntity
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QClassroomExamEntity extends EntityPathBase<ClassroomExamEntity> {

    private static final long serialVersionUID = 1666074396L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QClassroomExamEntity classroomExamEntity = new QClassroomExamEntity("classroomExamEntity");

    public final com.meeting.springboot_meet.classroom.infrastructure.persistence.entity.QClassroomEntity classroom;

    public final NumberPath<Integer> durationInMinutes = createNumber("durationInMinutes", Integer.class);

    public final DateTimePath<java.time.Instant> endTime = createDateTime("endTime", java.time.Instant.class);

    public final QExamEntity exam;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final DateTimePath<java.time.Instant> startTime = createDateTime("startTime", java.time.Instant.class);

    public QClassroomExamEntity(String variable) {
        this(ClassroomExamEntity.class, forVariable(variable), INITS);
    }

    public QClassroomExamEntity(Path<? extends ClassroomExamEntity> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QClassroomExamEntity(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QClassroomExamEntity(PathMetadata metadata, PathInits inits) {
        this(ClassroomExamEntity.class, metadata, inits);
    }

    public QClassroomExamEntity(Class<? extends ClassroomExamEntity> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.classroom = inits.isInitialized("classroom") ? new com.meeting.springboot_meet.classroom.infrastructure.persistence.entity.QClassroomEntity(forProperty("classroom"), inits.get("classroom")) : null;
        this.exam = inits.isInitialized("exam") ? new QExamEntity(forProperty("exam"), inits.get("exam")) : null;
    }

}

