package GooRoom.projectgooroom.service.dto;

import GooRoom.projectgooroom.domain.member.Gender;
import GooRoom.projectgooroom.domain.member.Member;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Size;
import org.hibernate.validator.constraints.Range;

public record EmailSignupDto(@NotBlank(message = "이메일을 입력해주세요")
                             @Pattern(regexp = "^[\\\\w!#$%&’*+\\\\/=?`{|}~^-]+(?:\\\\.[\\\\w!#$%&’*+\\\\/=?`{|}~^-]+)*@(?:[a-zA-Z\\\\d-]+\\\\.)+[a-zA-Z]{2,6}$",
                                     message = "")
                             String email,

                             @NotBlank(message = "비밀번호를 입력해주세요")
                             @Pattern(regexp = "^.*(?=^.{8,16}$)(?=.*\\\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$",
                                     message = "비밀번호는 8~16 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
                             String password,

                             @NotBlank(message = "이름을 입력해주세요") @Size(min=2, message = "사용자 이름이 너무 짧습니다.")
                             @Pattern(regexp = "^[A-Za-z가-힣]+$", message = "사용자 이름은 한글 또는 알파벳만 입력해주세요.")
                             String name,

                             @NotBlank(message = "닉네임을 입력해주세요.")
                             @Size(min=2, message = "닉네임이 너무 짧습니다.")
                             @NotBlank String nickName,

                             String birthyear,

                             String birthday,

                             String mobile,

                             Gender gender

                             ) {

    public Member toEntity(){
        return Member.builder().email(email).password(password).name(name).nickname(nickName).birthday(birthday)
                .birthyear(birthyear).mobile(mobile).gender(gender).build();
    }

}
