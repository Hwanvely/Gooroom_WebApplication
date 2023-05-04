package GooRoom.projectgooroom.domain.homePost;

import GooRoom.projectgooroom.domain.member.Member;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
public class Postmark {

    @Id @GeneratedValue
    @Column(name = "postmark_id")
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "home_post_id")
    private HomePost homePost;
}
