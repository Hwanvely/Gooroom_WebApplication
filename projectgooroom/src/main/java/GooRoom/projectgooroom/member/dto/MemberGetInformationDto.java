package GooRoom.projectgooroom.member.dto;

import GooRoom.projectgooroom.member.domain.*;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.persistence.Lob;

public record MemberGetInformationDto(
        String name,
        Gender gender,
        int age,
        Boolean smokingType,
        @JsonFormat(shape = JsonFormat.Shape.STRING)
        DrinkingType drinkingType,
        Boolean sleepingHabitType,
        WakeupType wakeupType,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        OrganizeType organizeType,

        @JsonFormat(shape = JsonFormat.Shape.STRING)
        CleanupType cleanupType,
        @Lob
        String introduce
) {
    
}
