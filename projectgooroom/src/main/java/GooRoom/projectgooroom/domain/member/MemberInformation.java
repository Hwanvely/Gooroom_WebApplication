package GooRoom.projectgooroom.domain.member;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Entity
@Getter @Setter
@NotNull
public class MemberInformation {

    @Id @GeneratedValue
    @Column(name = "member_information_id")
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    private Member member;
    private String profileImage;

    private Boolean smokingType;

    @Enumerated(EnumType.STRING)
    private DrinkingType drinkingType;
    private Boolean sleepingHabitType;
    private int wakeupTime;

    @Enumerated(EnumType.STRING)
    private OrganizeType organizeType;

    @Enumerated(EnumType.STRING)
    private CleanupType cleanupType;

    @Lob
    private String introduce;
}
