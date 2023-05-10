package GooRoom.projectgooroom.homepost.dto;

import GooRoom.projectgooroom.global.embedded.Address;
import GooRoom.projectgooroom.homepost.domain.HomePost;
import GooRoom.projectgooroom.homepost.domain.PostStatus;
import GooRoom.projectgooroom.homepost.domain.RentType;
import GooRoom.projectgooroom.homepost.domain.ResidenceType;
import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public record HomePostDto(
        String title,
        Boolean hasHome,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        PostStatus postStatus,
        LocalDateTime lastEditTime,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        ResidenceType residenceType,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        RentType rentType,

        int roomPrice,
        Address address,
        String content

) {
    public HomePost toEntity(){
        return HomePost.builder()
                .title(title)
                .hasHome(hasHome)
                .postStatus(postStatus)
                .lastEditTime(lastEditTime)
                .residenceType(residenceType)
                .rentType(rentType)
                .roomPrice(roomPrice)
                .address(address)
                .content(content)
                .build();
    }

}
