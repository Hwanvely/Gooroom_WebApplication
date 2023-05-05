package GooRoom.projectgooroom.service;

import GooRoom.projectgooroom.domain.member.Gender;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDate;

@Data
public class EmailSignupMemberDto {
    private String name;
    private String nickname;
    private String password;
    private String email;
    private String phoneNumber;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private Gender gender;

    @JsonFormat(pattern = "yyyy-MM-dd")
    private LocalDate birth;
}
