package GooRoom.projectgooroom.service.dto;

import java.util.Optional;

public record MemberUpdateDto(Optional<String> nickName, Optional<String> mobile) {

}
