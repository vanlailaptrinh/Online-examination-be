package com.meeting.springboot_meet.classroom.infrastructure.persistence.repository;

import com.meeting.springboot_meet.auth.infrastructure.persistence.entity.UserEntity;
import com.meeting.springboot_meet.auth.infrastructure.persistence.repository.SpringDataUserRepository;
import com.meeting.springboot_meet.classroom.domain.model.ClassroomMember;
import com.meeting.springboot_meet.classroom.domain.repository.ClassroomMemberRepository;
import com.meeting.springboot_meet.classroom.infrastructure.persistence.entity.ClassroomEntity;
import com.meeting.springboot_meet.classroom.infrastructure.persistence.entity.ClassroomMemberEntity;
import com.meeting.springboot_meet.classroom.infrastructure.persistence.mapper.ClassroomMemberMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@RequiredArgsConstructor
public class ClassroomMemberRepositoryImpl implements ClassroomMemberRepository {

    private final SpringDataClassroomMemberRepository springDataClassroomMemberRepository;
    private final SpringDataClassroomRepository springDataClassroomRepository;
    private final SpringDataUserRepository springDataUserRepository;
    private final ClassroomMemberMapper classroomMemberMapper;

    @Override
    public ClassroomMember save(ClassroomMember member) {
        ClassroomMemberEntity entity = classroomMemberMapper.toEntity(member);
        
        if (member.getClassroomId() != null) {
            ClassroomEntity classroom = springDataClassroomRepository.findById(member.getClassroomId())
                    .orElseThrow(() -> new RuntimeException("Classroom not found"));
            entity.setClassroom(classroom);
        }

        if (member.getUserId() != null) {
            UserEntity user = springDataUserRepository.findById(member.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found"));
            entity.setUser(user);
        }

        ClassroomMemberEntity savedEntity = springDataClassroomMemberRepository.save(entity);
        return classroomMemberMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<ClassroomMember> findByClassroomIdAndUserId(Long classroomId, Long userId) {
        return springDataClassroomMemberRepository.findByClassroomIdAndUserId(classroomId, userId)
                .map(classroomMemberMapper::toDomain);
    }

    @Override
    public List<ClassroomMember> findByClassroomId(Long classroomId) {
        return springDataClassroomMemberRepository.findByClassroomId(classroomId).stream()
                .map(classroomMemberMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public List<ClassroomMember> findByUserId(Long userId) {
        return springDataClassroomMemberRepository.findByUserId(userId).stream()
                .map(classroomMemberMapper::toDomain)
                .collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        springDataClassroomMemberRepository.deleteById(id);
    }
}
