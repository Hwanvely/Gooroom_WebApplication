package GooRoom.projectgooroom.homepost.repository;

import GooRoom.projectgooroom.homepost.dto.ListedPostDto;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

public interface PostmarkRepositoryCustom {
    PageImpl<ListedPostDto> findAllByMember_Id(Long memberId, Pageable pageable);

    void deleteAllByPostID(Long postId);
    void deleteAllByMemberID(Long memberId);
}
