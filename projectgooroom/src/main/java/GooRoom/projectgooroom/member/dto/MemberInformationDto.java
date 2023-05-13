package GooRoom.projectgooroom.member.dto;

import GooRoom.projectgooroom.member.domain.CleanupType;
import GooRoom.projectgooroom.member.domain.DrinkingType;
import GooRoom.projectgooroom.member.domain.OrganizeType;
import GooRoom.projectgooroom.member.domain.WakeupType;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Lob;
import lombok.*;

/**
 * MemberInformation 입력 DTO
 */
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MemberInformationDto {
    private Boolean smokingType;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private DrinkingType drinkingType;
    private Boolean sleepingHabitType;
    private WakeupType wakeupType;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private OrganizeType organizeType;

    @JsonFormat(shape = JsonFormat.Shape.STRING)
    private CleanupType cleanupType;

    @Lob
    private String introduce;
}
