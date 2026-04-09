package com.meeting.springboot_meet.classroom.domain.repository;

import com.meeting.springboot_meet.classroom.domain.model.Classroom;
import java.util.List;
import java.util.Optional;

public interface ClassroomRepository {
    Classroom save(Classroom classroom);
    Optional<Classroom> findById(Long id);
    List<Classroom> findByTeacherId(Long teacherId);
    void deleteById(Long id);
}
