package GooRoom.projectgooroom.homepost.dto;

import GooRoom.projectgooroom.homepost.domain.HomePost;
import GooRoom.projectgooroom.homepost.domain.PostStatus;
import GooRoom.projectgooroom.global.embedded.RentType;
import GooRoom.projectgooroom.global.embedded.ResidenceType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class GetHomePostDto{
    private boolean postmark;

    private String nickname;
    private int age;
    private String title;
    private Boolean hasHome;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private PostStatus postStatus;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private LocalDateTime lastEditTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ResidenceType residenceType;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private RentType rentType;
    private int roomPrice;

    private String city;
    private String dong;
    private String roadName;
    private String buildingNumber;

    private String content;


    public GetHomePostDto(HomePost homePost, String nickname, int age, boolean postmark) {
        this.title = homePost.getTitle();
        this.hasHome = homePost.getHasHome();
        this.postStatus = homePost.getPostStatus();
        this.lastEditTime = homePost.getLastEditTime();
        this.residenceType = homePost.getResidenceType();
        this.rentType = homePost.getRentType();
        this.roomPrice = homePost.getRoomPrice();
        this.city = homePost.getAddress().getCity();
        this.dong = homePost.getAddress().getDong();
        this.roadName = homePost.getAddress().getRoadName();
        this.buildingNumber = homePost.getAddress().getBuildingNumber();
        this.content = homePost.getContent();
        this.nickname = nickname;
        this.age = age;
        this.postmark = postmark;
    }
}
