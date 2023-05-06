package GooRoom.projectgooroom.domain.member;

import GooRoom.projectgooroom.service.MemberInformationDto;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;

@Entity
@Getter @Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder
@AllArgsConstructor
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

    ///연관관계 편의 메소드
    public void addMember(Member member){
        this.member = member;
        if(member.getMemberInformation()!=this){
            member.setMemberInformation(this);
        }
    }

    public void editInformation(MemberInformationDto informationDto){
        this.smokingType = informationDto.getSmokingType();
        this.drinkingType = informationDto.getDrinkingType();
        this.sleepingHabitType = informationDto.getSleepingHabitType();
        this.wakeupTime = informationDto.getWakeupTime();
        this.organizeType = informationDto.getOrganizeType();
        this.cleanupType = informationDto.getCleanupType();
        this.profileImage = informationDto.getProfileImage();
        this.introduce = informationDto.getIntroduce();
    }
}
