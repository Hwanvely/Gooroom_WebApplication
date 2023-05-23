package GooRoom.projectgooroom.homepost.dto;

import java.util.List;

public record HomePostListDto(
        long totalMates,
        List<ListedPostDto> mateList
) {
}
