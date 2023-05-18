package GooRoom.projectgooroom.homepost.service;

import GooRoom.projectgooroom.homepost.domain.HomePost;
import GooRoom.projectgooroom.homepost.domain.Postmark;
import GooRoom.projectgooroom.homepost.dto.HomePostFilterDto;
import GooRoom.projectgooroom.homepost.dto.ListedPostDto;
import GooRoom.projectgooroom.homepost.repository.HomePostRepositoryCustomImpl;
import GooRoom.projectgooroom.member.domain.Member;
import GooRoom.projectgooroom.member.domain.MemberInformation;
import GooRoom.projectgooroom.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Slf4j
public class HomePostRecommendService {

    private final HomePostRepositoryCustomImpl homePostRepository;

    private final MemberRepository memberRepository;

    //postmark 시 부여 가중치 비율
    private static final Double POST_MARK_WEIGHT = 1.1;

    ///boolean을 double로 변환
    public static double booleanToDouble(boolean value) {
        return value ? 1.0 : 0.0;
    }

    /**
     * 필터에 따라 조회 후 성향에 따른 협업필터링 결과 페이징 조회
     * @param email
     * @param pageable
     * @param homePostFilterDto
     * @return Page<HomePost>
     */
    public Page<ListedPostDto> findFilteredPost(String email, Pageable pageable, HomePostFilterDto homePostFilterDto){
        //추천 대상
        Member member = memberRepository.findMemberByEmail(email).get();
        MemberInformation clientMemberInformation = member.getMemberInformation();

        //검색 필터에 맞는 게시글 조회
        Page<HomePost> homePostByFilter = homePostRepository.findHomePostByFilter(pageable, homePostFilterDto, member.getId());
        List<HomePost> homePostList = homePostByFilter.getContent();
        //검색 필더에 맞는 게시글의 수
        int count = homePostList.size();

        //0, 1개인 경우 반환
        if(count==0){
            return new PageImpl<>(new ArrayList<>());
        }
        if(count==1){
            List<ListedPostDto> mateList = new ArrayList<>();
            mateList.add(new ListedPostDto(homePostList.get(0), homePostList.get(0).getMember().getNickname(), homePostList.get(0).getMember().getAge()));
            return new PageImpl<>(mateList);
        }

        //작성자 목록
        List<Member> writerList = homePostList.stream()
                .map(HomePost::getMember)
                .collect(Collectors.toList());


        //작성자와 회원간의 유클리드 거리 측정
        Map<Long, Double> euclideanMap = getEuclideanMap(member, clientMemberInformation, writerList);

        double maxEuclideanD = Collections.max(euclideanMap.values());
        double minEuclideanD = Collections.min(euclideanMap.values());

        //Member 간 유사도 측정
        Map<Long, Double> similarityMap = getSimilarityMap(writerList, euclideanMap, maxEuclideanD, minEuclideanD);

        //즐겨찾기 멤버에 가산점 부여.
        List<Postmark> postmarkList = member.getPostmarkList();
        postmarkList.forEach(postmark -> writerList.forEach(writer -> {
            if (postmark.getHomePost().getMember().getId() == writer.getId()) {
                Long key = writer.getId();
                similarityMap.compute(key, (k, value) -> value * POST_MARK_WEIGHT);
            }
        }));

        //최종 반환 순서
        List<HomePost> sortedHomePosts = getHomeCollaboratedFilteredPosts(homePostList, count, similarityMap);
        List<ListedPostDto> listedPosts = new ArrayList<>();


        for(HomePost homePost:sortedHomePosts){
            listedPosts.add(new ListedPostDto(homePost, homePost.getMember().getNickname(), homePost.getMember().getAge()));
        }

        return new PageImpl<>(listedPosts);
    }

    /**
     * 성향별 유클리드 거리 계산
     * @param member
     * @param clientMemberInformation
     * @param writerList
     * @return
     */
    private static Map<Long, Double> getEuclideanMap(Member member, MemberInformation clientMemberInformation, List<Member> writerList) {
        Map<Long, Double> euclideanMap = new HashMap<>();

        //유클리드 거리 측정
        for(int i = 0; i< writerList.size(); i++){
            double euclidSum = 0;
            MemberInformation writerInformation = writerList.get(i).getMemberInformation();

            euclidSum += Math.pow(member.getGender().getValue() - writerList.get(i).getGender().getValue(),2);
            euclidSum += Math.pow(booleanToDouble(clientMemberInformation.getSmokingType()) - booleanToDouble(writerInformation.getSmokingType()),2);
            euclidSum += Math.pow(clientMemberInformation.getDrinkingType().getValue() - writerInformation.getCleanupType().getValue(),2);
            euclidSum += Math.pow(booleanToDouble(clientMemberInformation.getSleepingHabitType()) - booleanToDouble(writerInformation.getSleepingHabitType()),2);
            euclidSum += Math.pow(clientMemberInformation.getWakeupType().getValue() - writerInformation.getWakeupType().getValue(),2);
            euclidSum += Math.pow(clientMemberInformation.getOrganizeType().getValue() - writerInformation.getOrganizeType().getValue(),2);
            euclidSum += Math.pow(clientMemberInformation.getCleanupType().getValue() - writerInformation.getCleanupType().getValue(),2);
            euclideanMap.put(writerList.get(i).getId(), Math.sqrt(euclidSum));
        }
        return euclideanMap;
    }

    /**
     * 유클리드 거리를 바탕으로 유사도 측정
     * @param writerList
     * @param euclideanMap
     * @param maxEuclideanD
     * @param minEuclideanD
     * @return
     */
    private static Map<Long, Double> getSimilarityMap(List<Member> writerList, Map<Long, Double> euclideanMap, double maxEuclideanD, double minEuclideanD) {
        //모두 유사도가 같은 경우
        if(maxEuclideanD==minEuclideanD){
            return writerList.stream()
                    .collect(Collectors.toMap(
                            Member::getId,
                            writer -> 1.0
                    ));
        }

        return writerList.stream()
                .collect(Collectors.toMap(
                        Member::getId,
                        writer -> 1-(euclideanMap.get(writer.getId()) - minEuclideanD) / (maxEuclideanD - minEuclideanD)
                ));
    }

    /**
     * 유사도를 바탕으로 게시글 정렬
     * @param homePostList
     * @param count
     * @param similarityMap
     * @return
     */
    private static List<HomePost> getHomeCollaboratedFilteredPosts(List<HomePost> homePostList, int count, Map<Long, Double> similarityMap) {
        List<Long> memberIdOrder = similarityMap.entrySet().stream()
                .sorted(Map.Entry.comparingByValue(Comparator.reverseOrder()))
                .map(Map.Entry::getKey)
                .collect(Collectors.toList());

        List<HomePost> sortedHomePosts = new ArrayList<>();
        for(int i = 0; i< count; i++){
            for(int j = 0; j< count; j++){
                if(homePostList.get(j).getMember().getId() == memberIdOrder.get(i)){
                    sortedHomePosts.add(homePostList.get(j));
                }
            }
        }
        return sortedHomePosts;
    }
}
