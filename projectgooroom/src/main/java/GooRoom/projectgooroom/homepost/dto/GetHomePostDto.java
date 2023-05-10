package GooRoom.projectgooroom.homepost.dto;

import GooRoom.projectgooroom.global.embedded.Address;
import GooRoom.projectgooroom.homepost.domain.HomePost;
import GooRoom.projectgooroom.homepost.domain.PostStatus;
import GooRoom.projectgooroom.homepost.domain.RentType;
import GooRoom.projectgooroom.homepost.domain.ResidenceType;
import com.fasterxml.jackson.annotation.JsonFormat;
import org.springframework.core.io.InputStreamResource;
import org.springframework.util.MultiValueMap;
import org.springframework.web.multipart.MultipartFile;

import java.time.LocalDateTime;

public class GetHomePostDto{
    private InputStreamResource profileImage;
    private InputStreamResource roomImage;
    private String nickname;
    private String title;
    private Boolean hasHome;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private PostStatus postStatus;
    private LocalDateTime lastEditTime;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private ResidenceType residenceType;
    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private RentType rentType;
    private int roomPrice;
    private Address address;
    private String content;


    public GetHomePostDto(InputStreamResource profileImage, InputStreamResource roomImage, HomePost homePost, String nickname) {
        this.profileImage = profileImage;
        this.roomImage = roomImage;
        this.title = homePost.getTitle();
        this.hasHome = homePost.getHasHome();
        this.postStatus = homePost.getPostStatus();
        this.lastEditTime = homePost.getLastEditTime();
        this.residenceType = homePost.getResidenceType();
        this.rentType = homePost.getRentType();
        this.roomPrice = homePost.getRoomPrice();
        this.address = homePost.getAddress();
        this.content = homePost.getContent();
        this.nickname = nickname;
    }
}
