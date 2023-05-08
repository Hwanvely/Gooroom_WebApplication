package GooRoom.projectgooroom.service.dto;

import java.util.Optional;

public record MemberUpdateDto(Optional<String> nickname, Optional<String> mobile) {

}
