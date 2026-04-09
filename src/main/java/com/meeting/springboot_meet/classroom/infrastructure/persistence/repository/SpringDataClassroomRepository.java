package com.meeting.springboot_meet.classroom.infrastructure.persistence.repository;

import com.meeting.springboot_meet.classroom.infrastructure.persistence.entity.ClassroomEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SpringDataClassroomRepository extends JpaRepository<ClassroomEntity, Long> {
    List<ClassroomEntity> findByTeacherId(Long teacherId);
}
