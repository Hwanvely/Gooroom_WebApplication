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

    @Enumerated(EnumType.STRING)
    private LoginType loginType;

    private String socialId;

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
    private String phoneNumber;

    @Enumerated(EnumType.STRING)
    private Gender gender;

    private LocalDate birth;

    @OneToOne(fetch = FetchType.LAZY, mappedBy = "member")
    private MemberInformation memberInformation;

    public void updateRefreshToken(String updateRefreshToken){
        this.refreshToken = updateRefreshToken;
    }
}
