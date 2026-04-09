package com.meeting.springboot_meet.classroom.infrastructure.persistence.repository;

import com.meeting.springboot_meet.auth.infrastructure.persistence.entity.UserEntity;
import com.meeting.springboot_meet.auth.infrastructure.persistence.repository.SpringDataUserRepository;
import com.meeting.springboot_meet.classroom.domain.model.Classroom;
import com.meeting.springboot_meet.classroom.domain.repository.ClassroomRepository;
import com.meeting.springboot_meet.classroom.infrastructure.persistence.entity.ClassroomEntity;
import com.meeting.springboot_meet.classroom.infrastructure.persistence.mapper.ClassroomMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ClassroomRepositoryImpl implements ClassroomRepository {

    private final SpringDataClassroomRepository springDataClassroomRepository;
    private final SpringDataUserRepository springDataUserRepository;
    private final ClassroomMapper classroomMapper;

    @Override
    public Classroom save(Classroom classroom) {
        ClassroomEntity entity = classroomMapper.toEntity(classroom);
        
        // Cần fetch UserEntity để set teacher
        if (classroom.getTeacherId() != null) {
            UserEntity teacher = springDataUserRepository.findById(classroom.getTeacherId())
                    .orElseThrow(() -> new RuntimeException("Teacher not found"));
            entity.setTeacher(teacher);
        }

        ClassroomEntity savedEntity = springDataClassroomRepository.save(entity);
        return classroomMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Classroom> findById(Long id) {
        return springDataClassroomRepository.findById(id).map(classroomMapper::toDomain);
    }

    @Override
    public List<Classroom> findByTeacherId(Long teacherId) {
        return springDataClassroomRepository.findByTeacherId(teacherId).stream()
                .map(classroomMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        springDataClassroomRepository.deleteById(id);
    }
}
