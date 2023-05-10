package GooRoom.projectgooroom.member.domain;

import GooRoom.projectgooroom.member.dto.MemberInformationDto;
import jakarta.persistence.*;
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
    @Enumerated(EnumType.STRING)
    private WakeupType wakeupType;

    @Enumerated(EnumType.STRING)
    private OrganizeType organizeType;

    @Enumerated(EnumType.STRING)
    private CleanupType cleanupType;

    @Lob
    private String introduce;

    public void addMember(Member member) {
        this.member = member;
        if(member.getMemberInformation()!=this)
            member.addMemberInformation(this);
    }

    public void editInformation(MemberInformationDto informationDto) {
        this.smokingType = informationDto.getSmokingType();
        this.drinkingType = informationDto.getDrinkingType();
        this.sleepingHabitType = informationDto.getSleepingHabitType();
        this.wakeupType = informationDto.getWakeupType();
        this.organizeType = informationDto.getOrganizeType();
        this.cleanupType = informationDto.getCleanupType();
        this.introduce = informationDto.getIntroduce();
    }

    public void addProfileImage(String filePath){
        this.profileImage = filePath;
    }
}
