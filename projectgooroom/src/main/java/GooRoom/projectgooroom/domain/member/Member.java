package GooRoom.projectgooroom.domain.member;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDate;

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
    public void authorizeUser() {
        this.role = Role.USER;
    }

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member")
    private MemberInformation memberInformation;

    public void updateRefreshToken(String updateRefreshToken){
        this.refreshToken = updateRefreshToken;
    }
}
