package GooRoom.projectgooroom.global.ouath2;

import GooRoom.projectgooroom.domain.member.Gender;
import GooRoom.projectgooroom.domain.member.LoginType;
import GooRoom.projectgooroom.domain.member.Member;
import GooRoom.projectgooroom.domain.member.Role;
import GooRoom.projectgooroom.global.ouath2.userinfo.NaverOAuth2UserInfo;
import GooRoom.projectgooroom.global.ouath2.userinfo.OAuth2UserInfo;
import com.nimbusds.oauth2.sdk.util.StringUtils;
import lombok.Builder;
import lombok.Getter;

import java.util.Map;
import java.util.UUID;

/**
 * 각 소셜에서 받아오는 데이터가 다르므로
 * 소셜별로 데이터를 받는 데이터를 분기 처리
 */
@Getter
public class OAuthAttributes {

    private String nameAttributeKey; // OAuth2 로그인 진행 시 키가 되는 필드 값, PK와 같은 의미
    private OAuth2UserInfo oauth2UserInfo; // 소셜 타입별 로그인 유저 정보(닉네임, 이메일, 프로필 사진 등등)

    @Builder
    public OAuthAttributes(String nameAttributeKey, OAuth2UserInfo oauth2UserInfo) {
        this.nameAttributeKey = nameAttributeKey;
        this.oauth2UserInfo = oauth2UserInfo;
    }

    /**
     * SocialType에 맞는 메소드 호출하여 OAuthAttributes 객체 반환
     * 파라미터 : userNameAttributeName -> OAuth2 로그인 시 키(PK)가 되는 값 / attributes : OAuth 서비스의 유저 정보들
     * 소셜별 of 메소드(ofGoogle, ofKaKao, ofNaver)들은 각각 소셜 로그인 API에서 제공하는
     * 회원의 식별값(id), attributes, nameAttributeKey를 저장 후 build
     */
    public static OAuthAttributes of(LoginType loginType,
                                     String userNameAttributeName, Map<String, Object> attributes) {

        if (loginType == LoginType.GOOGLE) {

        }
        if (loginType == LoginType.KAKAO) {
            
        }
        return ofNaver(userNameAttributeName, attributes);
    }



    public static OAuthAttributes ofNaver(String userNameAttributeName, Map<String, Object> attributes) {
        return OAuthAttributes.builder()
                .nameAttributeKey(userNameAttributeName)
                .oauth2UserInfo(new NaverOAuth2UserInfo(attributes))
                .build();
    }

    /**
     * of메소드로 OAuthAttributes 객체가 생성되어, 유저 정보들이 담긴 OAuth2UserInfo가 소셜 타입별로 주입된 상태
     * OAuth2UserInfo에서 socialId(식별값), nickname, imageUrl을 가져와서 build
     * email에는 UUID로 중복 없는 랜덤 값 생성
     * role은 GUEST로 설정
     */
    public Member toEntity(LoginType loginType, OAuth2UserInfo oauth2UserInfo) {
        return Member.builder()
                .loginType(loginType)
                .socialId(oauth2UserInfo.getId())
                .name(oauth2UserInfo.getName())
                .email(oauth2UserInfo.getEmail())
                .nickname(oauth2UserInfo.getNickname())
                .gender(fromString(oauth2UserInfo.getGender()))
                .birthday(oauth2UserInfo.getBirthDay())
                .birthyear(oauth2UserInfo.getBirthYear())
                .mobile(oauth2UserInfo.getMobile())
                .role(Role.GUEST)
                .build();
    }

    public static Gender fromString(String value) {
        if (StringUtils.isBlank(value)) {
            return null;
        }
        switch (value.toUpperCase()) {
            case "M":
                return Gender.MALE;
            case "F":
                return Gender.FEMALE;
            default:
                throw new IllegalArgumentException("Invalid gender value: " + value);
        }
    }
}
