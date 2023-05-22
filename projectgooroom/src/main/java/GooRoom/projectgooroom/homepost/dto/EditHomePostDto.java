package GooRoom.projectgooroom.homepost.dto;

import GooRoom.projectgooroom.homepost.domain.PostStatus;
import GooRoom.projectgooroom.global.embedded.RentType;

public record EditHomePostDto(
        String title,
        PostStatus postStatus,
        RentType rentType,
        int roomPrice,
        String city,
        String dong,
        String roadName,
        String buildingNumber,
        String content)
{

}
