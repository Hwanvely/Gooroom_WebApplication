package GooRoom.projectgooroom.homepost.dto;

import GooRoom.projectgooroom.global.embedded.Address;
import GooRoom.projectgooroom.homepost.domain.HomePost;
import GooRoom.projectgooroom.homepost.domain.PostStatus;
import GooRoom.projectgooroom.global.embedded.RentType;
import GooRoom.projectgooroom.global.embedded.ResidenceType;
import com.fasterxml.jackson.annotation.JsonFormat;

public record HomePostDto(
        String title,
        Boolean hasHome,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        PostStatus postStatus,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        ResidenceType residenceType,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        RentType rentType,

        int roomPrice,
        //Address
        String city,
        String dong,
        String roadName,
        String buildingNumber,

        String content
) {
    public HomePost toEntity(){
        return HomePost.builder()
                .title(title)
                .hasHome(hasHome)
                .postStatus(postStatus)
                .residenceType(residenceType)
                .rentType(rentType)
                .roomPrice(roomPrice)
                .address(Address.builder()
                        .city(city)
                        .dong(dong)
                        .roadName(roadName)
                        .buildingNumber(buildingNumber)
                        .build())
                .content(content)
                .build();
    }

}
