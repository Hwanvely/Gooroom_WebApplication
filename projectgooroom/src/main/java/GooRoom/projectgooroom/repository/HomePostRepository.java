package GooRoom.projectgooroom.repository;

import GooRoom.projectgooroom.domain.homePost.HomePost;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;



@Repository
public interface HomePostRepository extends JpaRepository<HomePost, Long> {

    /**
     * 전체 HomePost 페이징 조회
     * @param pageable the pageable to request a paged result, can be {@link Pageable#unpaged()}, must not be
     *          {@literal null}.
     * @return
     */
    @Override
    Page<HomePost> findAll(Pageable pageable);

    /**
     * HomePostId를 통해 HomePost 1개 조회
     * @param postId
     * @return
     */
    HomePost findHomePostById(Long postId);

    /**
     * memberId를 통해 HomePost 페이징 조회
     * @param memberId
     * @param pageable
     * @return
     */
    Page<HomePost> findHomePostsByMemberId(Long memberId, Pageable pageable);
}
