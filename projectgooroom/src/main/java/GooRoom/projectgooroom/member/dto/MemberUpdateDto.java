package GooRoom.projectgooroom.member.dto;

import java.util.Optional;

public record MemberUpdateDto(Optional<String> nickname, Optional<String> mobile) {

}
