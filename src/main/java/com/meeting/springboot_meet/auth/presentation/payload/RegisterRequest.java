package com.meeting.springboot_meet.auth.presentation.payload;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RegisterRequest {
    @NotBlank(message = "Email không được để trống")
    @Email(message = "Định dạng email không hợp lệ")
    private String email;

    @NotBlank(message = "Mật khẩu không được để trống")
    @Size(min = 6, message = "Mật khẩu phải có ít nhất 6 ký tự")
    private String password;

    /**
     * Role người dùng chọn khi đăng ký: "STUDENT" hoặc "TEACHER".
     * Nếu không truyền, mặc định là STUDENT.
     */
    @Pattern(
        regexp = "STUDENT|TEACHER",
        message = "Role chỉ được là STUDENT hoặc TEACHER"
    )
    private String role;
}
