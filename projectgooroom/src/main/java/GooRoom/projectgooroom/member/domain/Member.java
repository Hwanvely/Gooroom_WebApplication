package GooRoom.projectgooroom.member.domain;

import GooRoom.projectgooroom.homepost.domain.HomePost;
import GooRoom.projectgooroom.homepost.domain.Postmark;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

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

    @NotNull
    private String nickname;

    @NotNull
    private String password;

    @Enumerated(EnumType.STRING)
    private Role role;

    @NotNull
    private String name;

    @NotNull
    private String email;

    @NotNull
    private String mobile;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private String birthday;

    private String birthyear;

    @Enumerated(EnumType.STRING)
    private LoginType loginType; // KAKAO, NAVER, GOOGLE

    private String socialId;

    // 유저 권한 설정 메소드
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member", cascade = CascadeType.ALL)
    private MemberInformation memberInformation;


    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<HomePost> homePostList = new ArrayList<>();

    @OneToMany(mappedBy = "member", cascade = CascadeType.ALL)
    private List<Postmark> postmarkList = new ArrayList<>();

    @Transactional
    public void authorizeUser() {
        this.role = Role.USER;
    }
    //비밀번호 변경, 회원 탈퇴 시, 비밀번호를 확인하며, 이때 비밀번호의 일치여부를 판단하는 메서드입니다.

    public boolean matchPassword(PasswordEncoder passwordEncoder, String checkPassword){
        return passwordEncoder.matches(checkPassword, getPassword());
    }

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

    public void updateMobile(String update_mobile) {
        this.mobile = update_mobile;
    }

    public void updateNickname(String updateNickname){
        this.nickname = updateNickname;
    }
}
