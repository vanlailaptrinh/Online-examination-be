package com.meeting.springboot_meet.classroom.domain.repository;

import com.meeting.springboot_meet.classroom.domain.model.Invitation;
import java.util.Optional;

public interface InvitationRepository {
    Invitation save(Invitation invitation);
    Optional<Invitation> findByToken(String token);
    void deleteById(Long id);
}
