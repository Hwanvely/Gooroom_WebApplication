package GooRoom.projectgooroom.homepost.repository;

import GooRoom.projectgooroom.homepost.domain.HomePost;
import GooRoom.projectgooroom.homepost.dto.HomePostFilterDto;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;


public interface HomePostRepositoryCustom {
    /**
     * Filter조건에 맞는 HomePost 페이징 조회.
     * rentType, 가격대, 작성자 연령대, residenceType, 행정동, hasHome
     * @param pageable
     * @param homePostFilter
     * @return
     */
    Page<HomePost> findHomePostByFiter(Pageable pageable, HomePostFilterDto homePostFilter);
}
