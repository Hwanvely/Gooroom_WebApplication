package GooRoom.projectgooroom.homepost.repository;

import GooRoom.projectgooroom.homepost.domain.HomePost;
import GooRoom.projectgooroom.homepost.dto.ListedPostDto;
import GooRoom.projectgooroom.member.domain.Member;
import com.querydsl.core.Tuple;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

import static GooRoom.projectgooroom.homepost.domain.QHomePost.homePost;
import static GooRoom.projectgooroom.homepost.domain.QPostmark.postmark;
import static GooRoom.projectgooroom.member.domain.QMember.member;

@Repository
@Transactional(readOnly = true)
public class PostmarkRepositoryCustomImpl implements PostmarkRepositoryCustom{

    private JPAQueryFactory queryFactory;

    public PostmarkRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<ListedPostDto> findAllByMember_Id(Long memberId, Pageable pageable) {
        List<Tuple> tuples = queryFactory
                .select(homePost, homePost.member)
                .from(postmark)
                .join(postmark.homePost, homePost)
                .join(postmark.member, member)
                .where(member.id.eq(memberId))
                .orderBy(homePost.lastEditTime.desc())
                .limit(pageable.getPageSize())
                .offset(pageable.getOffset() * pageable.getPageSize())
                .fetch();

        JPAQuery<HomePost> query = queryFactory
                .select(homePost)
                .from(postmark)
                .join(postmark.homePost, homePost)
                .join(postmark.member, member)
                .where(member.id.eq(memberId));

        List<HomePost> homePosts = new ArrayList<>();
        List<Member> members = new ArrayList<>();

        for (Tuple tuple : tuples) {
            HomePost homePost = tuple.get(0, HomePost.class);
            Member member = tuple.get(1, Member.class);

            homePosts.add(homePost);
            members.add(member);
        }
        return PageableExecutionUtils.getPage(
                getListedPostDtos(homePosts,members)
                , pageable, query::fetchCount);
    }

    private static List<ListedPostDto> getListedPostDtos(List<HomePost> homePosts, List<Member> members) {
        List<ListedPostDto> listedPostDtos = new ArrayList<>();

        for (int i = 0; i< members.size(); i++){
            listedPostDtos.add(new ListedPostDto(homePosts.get(i), members.get(i).getNickname(), members.get(i).getAge()));
        }

        return listedPostDtos;
    }
}
