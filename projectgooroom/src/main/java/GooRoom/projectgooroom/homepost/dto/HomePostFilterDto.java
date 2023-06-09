package GooRoom.projectgooroom.homepost.dto;

import GooRoom.projectgooroom.homepost.domain.PostStatus;
import GooRoom.projectgooroom.global.embedded.RentType;
import GooRoom.projectgooroom.global.embedded.ResidenceType;
import com.fasterxml.jackson.annotation.JsonFormat;

public record HomePostFilterDto(
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        PostStatus postStatus,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        RentType rentType,
        int minPrice,
        int maxPrice,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        ResidenceType residenceType,
        String dong,
        int minAge,
        int maxAge,
        Boolean hasHome
) {
}
