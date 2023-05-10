package GooRoom.projectgooroom.member.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;

public record UpdatePasswordDto( //@NotBlank(message = "비밀번호를 입력해주세요")
                                String checkPassword,

//                                @NotBlank(message = "비밀번호를 입력해주세요")
//                                @Pattern(regexp = "^.*(?=^.{8,16}$)(?=.*\\\\d)(?=.*[a-zA-Z])(?=.*[!@#$%^&+=]).*$",
//                                        message = "비밀번호는 8~16 자리이면서 1개 이상의 알파벳, 숫자, 특수문자를 포함해야합니다.")
                                String toBePassword) {
}
