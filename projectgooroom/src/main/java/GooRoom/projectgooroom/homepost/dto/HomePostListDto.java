package GooRoom.projectgooroom.homepost.dto;

import java.util.List;

public record HomePostListDto(
        int totalMates,
        List<ListedPostDto> mateList
) {
}
