package GooRoom.projectgooroom.homepost.dto;

import GooRoom.projectgooroom.homepost.domain.PostStatus;
import GooRoom.projectgooroom.homepost.domain.ResidenceType;
import com.fasterxml.jackson.annotation.JsonFormat;

public record HomePostListDto(
        String title,
        String birthyear,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        PostStatus postStatus,
        String city,
        String dong,
        String roadName,
        String buildingNumber,
        String zipcode,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        ResidenceType residenceType,
        int roomPrice,
        Long postId,
        String nickname
) {
}
