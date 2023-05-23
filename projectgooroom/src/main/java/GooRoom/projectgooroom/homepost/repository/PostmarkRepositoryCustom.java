package GooRoom.projectgooroom.homepost.repository;

import GooRoom.projectgooroom.homepost.dto.ListedPostDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface PostmarkRepositoryCustom {
    Page<ListedPostDto> findAllByMember_Id(Long memberId, Pageable pageable);
}
