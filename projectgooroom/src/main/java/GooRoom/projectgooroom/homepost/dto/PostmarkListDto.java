package GooRoom.projectgooroom.homepost.dto;

import java.util.List;

public record PostmarkListDto(
        int totalPostmark,
        List<ListedPostmarkDto> postmarkList
) {
}
