package GooRoom.projectgooroom.service;

import GooRoom.projectgooroom.domain.member.CleanupType;
import GooRoom.projectgooroom.domain.member.DrinkingType;
import GooRoom.projectgooroom.domain.member.Member;
import GooRoom.projectgooroom.domain.member.OrganizeType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Lob;
import lombok.Data;

/**
 * MemberInformation 입력 DTO
 */
@Data
public class MemberInformationDto {
    private Boolean smokingType;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private DrinkingType drinkingType;
    private Boolean sleepingHabitType;
    private int wakeupTime;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private OrganizeType organizeType;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private CleanupType cleanupType;

    @Lob
    private String introduce;
}
