package GooRoom.projectgooroom.domain.homePost;

import GooRoom.projectgooroom.domain.Address;
import GooRoom.projectgooroom.domain.member.Member;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

import java.time.LocalDateTime;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class HomePost {

    @Id
    @GeneratedValue
    @Column(name = "home_post_id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "member_id")
    private Member member;

    @NotNull
    private String title;
    @NotNull
    private Boolean hasHome;

    @Enumerated(EnumType.STRING)
    private PostStatus postStatus;

    private LocalDateTime lastEditTime;

    @Enumerated(EnumType.STRING)
    @NotNull
    private ResidenceType residenceType;

    @Enumerated(EnumType.STRING)
    @NotNull
    private RentType rentType;

    @NotNull
    private int roomPrice;

    @Embedded
    @NotNull
    private Address address;

    @Lob
    private String content;

    private String roomImage;
}
