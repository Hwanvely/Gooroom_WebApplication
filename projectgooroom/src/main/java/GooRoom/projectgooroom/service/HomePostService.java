package GooRoom.projectgooroom.service;

import GooRoom.projectgooroom.domain.homePost.HomePost;
import GooRoom.projectgooroom.domain.member.Member;
import GooRoom.projectgooroom.repository.HomePostRepository;
import GooRoom.projectgooroom.repository.MemberRepository;
import GooRoom.projectgooroom.service.dto.HomePostDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class HomePostService {

    private final HomePostRepository homePostRepository;
    private final MemberRepository memberRepository;

    /**
     * HomePost 생성
     * @param memberId
     * @param homePostDto
     * @return HomePost
     */
    @Transactional
    public Long createHomePost(Long memberId, HomePostDto homePostDto){
        Member member = memberRepository.findMemberById(memberId).get();
        HomePost post = homePostDto.toEntity();
        post.addMember(member);

        return homePostRepository.save(post).getId();
    }

    /**
     * Member가 작성한 전체 HomePost 페이징 조회
     * @param memberId
     * @param pageable
     * @return
     */
    public Page<HomePost> findAllPostsByMember(Long memberId, Pageable pageable){
        return homePostRepository.findHomePostsByMemberId(memberId, pageable);
    }

    /**
     * 전체 HomePost 페이징 조회
     * @param pageable
     * @return
     */
    public Page<HomePost> findAllPosts(Pageable pageable){
        return homePostRepository.findAll(pageable);
    }

    /**
     * ID를 통해 HomePost 조회
     * @param postId
     * @return
     */
    public HomePost findOne(Long postId){
        return homePostRepository.findHomePostById(postId);
    }

}
