package com.meeting.springboot_meet.classroom.infrastructure.persistence.repository;

import com.meeting.springboot_meet.classroom.domain.model.Invitation;
import com.meeting.springboot_meet.classroom.domain.repository.InvitationRepository;
import com.meeting.springboot_meet.classroom.infrastructure.persistence.entity.ClassroomEntity;
import com.meeting.springboot_meet.classroom.infrastructure.persistence.entity.InvitationEntity;
import com.meeting.springboot_meet.classroom.infrastructure.persistence.mapper.InvitationMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class InvitationRepositoryImpl implements InvitationRepository {

    private final SpringDataInvitationRepository springDataInvitationRepository;
    private final SpringDataClassroomRepository springDataClassroomRepository;
    private final InvitationMapper invitationMapper;

    @Override
    public Invitation save(Invitation invitation) {
        InvitationEntity entity = invitationMapper.toEntity(invitation);
        
        if (invitation.getClassroomId() != null) {
            ClassroomEntity classroom = springDataClassroomRepository.findById(invitation.getClassroomId())
                    .orElseThrow(() -> new RuntimeException("Classroom not found"));
            entity.setClassroom(classroom);
        }

        InvitationEntity savedEntity = springDataInvitationRepository.save(entity);
        return invitationMapper.toDomain(savedEntity);
    }

    @Override
    public Optional<Invitation> findByToken(String token) {
        return springDataInvitationRepository.findByToken(token).map(invitationMapper::toDomain);
    }

    @Override
    public void deleteById(Long id) {
        springDataInvitationRepository.deleteById(id);
    }
}
