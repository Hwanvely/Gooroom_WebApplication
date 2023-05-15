package GooRoom.projectgooroom.homepost.dto;

import GooRoom.projectgooroom.homepost.domain.HomePost;
import GooRoom.projectgooroom.homepost.domain.PostStatus;
import GooRoom.projectgooroom.homepost.domain.ResidenceType;
import com.fasterxml.jackson.annotation.JsonFormat;

public class ListedPostDto{
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
        private int roomPrice;
        private Long postId;
        private String nickname;

        public ListedPostDto(HomePost homePost, String nickname) {
                this.title = homePost.getTitle();
                this.postStatus = homePost.getPostStatus();
                this.city = homePost.getAddress().getCity();
                this.dong = homePost.getAddress().getDong();
                this.roadName = homePost.getAddress().getRoadName();
                this.buildingNumber = homePost.getAddress().getBuildingNumber();
                this.zipcode = homePost.getAddress().getZipcode();
                this.residenceType = homePost.getResidenceType();
                this.roomPrice = homePost.getRoomPrice();
                this.postId = homePost.getId();
                this.nickname = nickname;
        }

        public String getTitle() {
                return title;
        }

        public PostStatus getPostStatus() {
                return postStatus;
        }

        public String getCity() {
                return city;
        }

        public String getDong() {
                return dong;
        }

        public String getRoadName() {
                return roadName;
        }

        public String getBuildingNumber() {
                return buildingNumber;
        }

        public String getZipcode() {
                return zipcode;
        }

        public ResidenceType getResidenceType() {
                return residenceType;
        }

        public int getRoomPrice() {
                return roomPrice;
        }

        public Long getPostId() {
                return postId;
        }

        public String getNickname() {
                return nickname;
        }
}
