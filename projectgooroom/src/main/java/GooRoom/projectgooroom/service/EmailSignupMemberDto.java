package GooRoom.projectgooroom.service;

import GooRoom.projectgooroom.domain.member.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

/**
 * 이메일 회원가입 시 DTO
 */
@Data
public class EmailSignupMemberDto {
    private String name;
    private String nickname;
    private String password;
    private String email;
    private String mobile;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Gender gender;
    private String birthyear;
    private String birthday;
}
