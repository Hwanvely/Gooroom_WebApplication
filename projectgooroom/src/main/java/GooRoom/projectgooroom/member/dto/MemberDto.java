package GooRoom.projectgooroom.member.dto;

import GooRoom.projectgooroom.member.domain.Gender;
import GooRoom.projectgooroom.member.domain.Member;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 이메일 회원가입 시 DTO
 */
@Data
@NoArgsConstructor
public class MemberDto {
    private String name;
    private String nickname;
    private String email;
    private String mobile;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Gender gender;

    private String birthyear;
    private String birthday;

    @Builder
    public MemberDto(Member member) {
        this.name = member.getName();
        this.nickname = member.getNickname();
        this.email = member.getEmail();
        this.mobile = member.getMobile();
        this.gender = member.getGender();
        this.birthyear = member.getBirthyear();
        this.birthday = member.getBirthday();
    }
}
