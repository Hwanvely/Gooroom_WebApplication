package GooRoom.projectgooroom.homepost.domain;

import GooRoom.projectgooroom.member.domain.Member;
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

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "member_id")
    private Member member;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.REMOVE)
    @JoinColumn(name = "home_post_id")
    private HomePost homePost;

    private Long postId;

    public Postmark(Member member, HomePost homePost) {
        this.member = member;
        this.homePost = homePost;
        this.postId = homePost.getId();
    }
}
