package GooRoom.projectgooroom.domain.member;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Member {

    @Id @GeneratedValue
    @Column(name = "member_id")
    private Long id;

    private String refreshToken;

//    //@notnull
    private String nickname;

//    //@notnull
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    //@notnull
    private String name;

    //@notnull
    private String email;

    //@notnull
    private String mobile;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String birthday;

    private String birthyear;

    @Enumerated(EnumType.STRING)
    private LoginType loginType; // KAKAO, NAVER, GOOGLE

    private String socialId;

    // 유저 권한 설정 메소드
    public void authorizeUser() {
        this.role = Role.USER;
    }

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member")
    private MemberInformation memberInformation;

    ///연관관계 편의 메소드
    public void addMemberInformation(MemberInformation information){
        this.memberInformation = information;
        if(information.getMember()!=this){
            information.addMember(this);
        }
    }

    public void updateRefreshToken(String updateRefreshToken){
        this.refreshToken = updateRefreshToken;
    }

    public void passwordEncode(PasswordEncoder passwordEncoder){
        this.password = passwordEncoder.encode(this.password);
    }

    public void updatePassword(PasswordEncoder passwordEncoder, String updatePassword){
        this.password = passwordEncoder.encode(updatePassword);
    }

    public void updateNickname(String updateNickname){
        this.nickname = updateNickname;
    }
}
