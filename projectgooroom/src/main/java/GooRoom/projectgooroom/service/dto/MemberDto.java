package GooRoom.projectgooroom.service.dto;

import GooRoom.projectgooroom.domain.member.Gender;
import GooRoom.projectgooroom.domain.member.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serial;

/**
 * 이메일 회원가입 시 DTO
 */
@Data
@NoArgsConstructor
public class MemberDto {
    private String name;
    private String nickname;
    private String password;
    private String email;
    private String mobile;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Gender gender;

    private String birthyear;
    private String birthday;

    @Builder
    public MemberDto(String name, String nickname, String email, String mobile, Gender gender, String birthyear, String birthday) {
        this.name = name;
        this.nickname = nickname;
        this.email = email;
        this.mobile = mobile;
        this.gender = gender;
        this.birthyear = birthyear;
        this.birthday = birthday;
    }
}
