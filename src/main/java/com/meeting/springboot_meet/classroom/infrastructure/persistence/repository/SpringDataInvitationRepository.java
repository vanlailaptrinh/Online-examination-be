package com.meeting.springboot_meet.classroom.infrastructure.persistence.repository;

import com.meeting.springboot_meet.classroom.infrastructure.persistence.entity.InvitationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SpringDataInvitationRepository extends JpaRepository<InvitationEntity, Long> {
    Optional<InvitationEntity> findByToken(String token);
}
