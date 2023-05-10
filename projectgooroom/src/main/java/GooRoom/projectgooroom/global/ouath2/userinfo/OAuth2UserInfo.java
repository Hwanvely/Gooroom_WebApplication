package GooRoom.projectgooroom.global.ouath2.userinfo;

import java.util.Map;

public abstract class OAuth2UserInfo {

    protected Map<String, Object> attributes;

    public OAuth2UserInfo(Map<String, Object> attributes) {
        this.attributes = attributes;
    }

    public abstract String getId(); //소셜 식별 값 : 카카오 - "id", 네이버 - "id"

    public abstract String getNickname();

    public abstract String getName();

    public abstract String getEmail();

    public abstract String getGender();

    public abstract String getBirthDay();

    public abstract String getBirthYear();

    public abstract String getMobile();


}