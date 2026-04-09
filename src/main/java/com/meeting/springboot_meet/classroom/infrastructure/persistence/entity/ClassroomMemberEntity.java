package com.meeting.springboot_meet.classroom.infrastructure.persistence.entity;

import com.meeting.springboot_meet.auth.infrastructure.persistence.entity.UserEntity;
import jakarta.persistence.*;
import lombok.*;

import java.time.Instant;

@Entity
@Table(name = "classroom_members", uniqueConstraints = {
    @UniqueConstraint(columnNames = {"classroom_id", "user_id"})
})
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ClassroomMemberEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "classroom_id", nullable = false)
    private ClassroomEntity classroom;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private UserEntity user;

    @Builder.Default
    @Enumerated(EnumType.STRING)
    private MemberStatus status = MemberStatus.PENDING;

    @Builder.Default
    private Instant joinedAt = Instant.now();

    public enum MemberStatus {
        PENDING,
        ACCEPTED,
        REJECTED
    }
}
