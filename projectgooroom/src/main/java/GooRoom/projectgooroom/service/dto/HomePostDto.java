package GooRoom.projectgooroom.service.dto;

import GooRoom.projectgooroom.domain.Address;
import GooRoom.projectgooroom.domain.homePost.HomePost;
import GooRoom.projectgooroom.domain.homePost.PostStatus;
import GooRoom.projectgooroom.domain.homePost.RentType;
import GooRoom.projectgooroom.domain.homePost.ResidenceType;
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
