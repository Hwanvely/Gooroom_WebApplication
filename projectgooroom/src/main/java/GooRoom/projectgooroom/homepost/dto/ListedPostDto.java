package GooRoom.projectgooroom.homepost.dto;

import GooRoom.projectgooroom.global.embedded.RentType;
import GooRoom.projectgooroom.homepost.domain.HomePost;
import GooRoom.projectgooroom.homepost.domain.PostStatus;
import GooRoom.projectgooroom.global.embedded.ResidenceType;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

@Data
public class ListedPostDto{
        private String title;
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        private PostStatus postStatus;
        private String city;
        private String dong;
        private String roadName;
        private String buildingNumber;
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        private ResidenceType residenceType;

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        private RentType rentType;
        private int roomPrice;
        private Long postId;
        private String nickname;
        private int age;

        public ListedPostDto(HomePost homePost, String nickname, int age) {
                this.title = homePost.getTitle();
                this.postStatus = homePost.getPostStatus();
                this.city = homePost.getAddress().getCity();
                this.dong = homePost.getAddress().getDong();
                this.roadName = homePost.getAddress().getRoadName();
                this.buildingNumber = homePost.getAddress().getBuildingNumber();
                this.residenceType = homePost.getResidenceType();
                this.rentType = homePost.getRentType();
                this.roomPrice = homePost.getRoomPrice();
                this.postId = homePost.getId();
                this.nickname = nickname;
                this.age = age;
        }
}
