package GooRoom.projectgooroom.homepost.dto;

import GooRoom.projectgooroom.global.embedded.RentType;
import GooRoom.projectgooroom.global.embedded.ResidenceType;
import GooRoom.projectgooroom.homepost.domain.PostStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class ListedPostmarkDto {
    private String title;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private PostStatus postStatus;
    private String city;
    private String dong;
    private String roadName;
    private String buildingNumber;
    private String zipcode;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ResidenceType residenceType;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private RentType rentType;
    private int roomPrice;
    private Long postId;
    private String nickname;
    private int age;
    private Long postmarkId;

    public ListedPostmarkDto(ListedPostDto postDto, Long postmarkId) {
        this.title = postDto.getTitle();
        this.postStatus = postDto.getPostStatus();
        this.city = postDto.getCity();
        this.dong = postDto.getDong();
        this.roadName = postDto.getRoadName();
        this.buildingNumber = postDto.getBuildingNumber();
        this.zipcode = postDto.getZipcode();
        this.residenceType = postDto.getResidenceType();
        this.rentType = postDto.getRentType();
        this.roomPrice = postDto.getRoomPrice();
        this.postId = postDto.getPostId();
        this.nickname = postDto.getNickname();
        this.age = postDto.getAge();
        this.postmarkId = postmarkId;
    }
}
