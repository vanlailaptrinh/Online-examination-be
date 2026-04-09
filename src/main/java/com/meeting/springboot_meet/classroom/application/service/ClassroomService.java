package com.meeting.springboot_meet.classroom.application.service;

import com.meeting.springboot_meet.auth.domain.model.User;
import com.meeting.springboot_meet.auth.domain.repository.UserRepository;
import com.meeting.springboot_meet.classroom.domain.model.Classroom;
import com.meeting.springboot_meet.classroom.domain.model.ClassroomMember;
import com.meeting.springboot_meet.classroom.domain.model.Invitation;
import com.meeting.springboot_meet.classroom.domain.repository.ClassroomMemberRepository;
import com.meeting.springboot_meet.classroom.domain.repository.ClassroomRepository;
import com.meeting.springboot_meet.classroom.domain.repository.InvitationRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;

import java.time.Instant;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class ClassroomService {

    private final ClassroomRepository classroomRepository;
    private final ClassroomMemberRepository classroomMemberRepository;
    private final UserRepository userRepository;
    private final JavaMailSender mailSender;
    private final RedisTemplate<String, String> redisTemplate;

    @Value("${app.frontend.url:http://localhost:3000}")
    private String frontendUrl;

    @Transactional
    public Classroom createClassroom(String name, String description, Long teacherId) {
        Classroom classroom = Classroom.builder()
                .name(name)
                .description(description)
                .teacherId(teacherId)
                .createdAt(Instant.now())
                .build();
        return classroomRepository.save(classroom);
    }

    public List<Classroom> getTeacherClassrooms(Long teacherId) {
        return classroomRepository.findByTeacherId(teacherId);
    }

    @Transactional
    public Invitation inviteStudent(Long classroomId, String email) {
        String token = UUID.randomUUID().toString();
        String redisKey = "CLASS_INVITE:" + token;
        String redisValue = email + ":" + classroomId;

        // Lưu vào Redis với hạn 7 ngày
        redisTemplate.opsForValue().set(redisKey, redisValue, 7, TimeUnit.DAYS);

        try {
            String link = frontendUrl + "/classrooms/join?token=" + token;
            SimpleMailMessage message = new SimpleMailMessage();
            message.setTo(email);
            message.setSubject("Lời mời tham gia lớp học");
            message.setText("Bạn được mời tham gia lớp học. Hãy bấm vào đường link sau để tham gia: " + link);
            mailSender.send(message);
        } catch (Exception ex) {
            System.err.println("Failed to send email: " + ex.getMessage());
        }
        
        return Invitation.builder()
                .email(email)
                .classroomId(classroomId)
                .token(token)
                .build();
    }

    @Transactional
    public void joinClassroom(String token) {
        String redisKey = "CLASS_INVITE:" + token;
        String redisValue = redisTemplate.opsForValue().get(redisKey);

        if (redisValue == null) {
            throw new RuntimeException("Lời mời không hợp lệ hoặc đã hết hạn.");
        }

        String[] parts = redisValue.split(":");
        if (parts.length != 2) {
            throw new RuntimeException("Dữ liệu lời mời bị lỗi.");
        }
        
        String invitedEmail = parts[0];
        Long classroomId = Long.parseLong(parts[1]);

        User user = userRepository.findByEmail(invitedEmail)
                .orElseThrow(() -> new RuntimeException("Tài khoản chưa được đăng ký bằng email này. Vui lòng đăng ký trước!"));
        
        Long userId = user.getId();

        if (classroomMemberRepository.findByClassroomIdAndUserId(classroomId, userId).isPresent()) {
            throw new RuntimeException("Bạn đã là thành viên của lớp học này rồi.");
        }

        ClassroomMember member = ClassroomMember.builder()
                .classroomId(classroomId)
                .userId(userId)
                .status("ACCEPTED")
                .joinedAt(Instant.now())
                .build();
        
        classroomMemberRepository.save(member);
        
        // Vô hiệu hoá token sau khi dùng
        redisTemplate.delete(redisKey);
    }

    public List<ClassroomMember> getClassroomMembers(Long classroomId) {
        return classroomMemberRepository.findByClassroomId(classroomId);
    }
}
