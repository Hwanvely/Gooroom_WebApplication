package GooRoom.projectgooroom.homepost.repository;

import GooRoom.projectgooroom.homepost.dto.ListedPostmarkDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface PostmarkRepositoryCustom {
    PageImpl<ListedPostmarkDto> findAllByMember_Id(Long memberId, Pageable pageable);
}
