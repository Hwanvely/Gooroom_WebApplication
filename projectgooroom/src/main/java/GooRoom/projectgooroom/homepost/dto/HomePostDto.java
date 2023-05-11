package GooRoom.projectgooroom.homepost.dto;

import GooRoom.projectgooroom.global.embedded.Address;
import GooRoom.projectgooroom.homepost.domain.HomePost;
import GooRoom.projectgooroom.homepost.domain.PostStatus;
import GooRoom.projectgooroom.homepost.domain.RentType;
import GooRoom.projectgooroom.homepost.domain.ResidenceType;
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
        String roadName,
        String buildingNumber,
        String zipcode,

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
                        .roadName(roadName)
                        .buildingNumber(buildingNumber)
                        .zipcode(zipcode)
                        .build())
                .content(content)
                .build();
    }

}
