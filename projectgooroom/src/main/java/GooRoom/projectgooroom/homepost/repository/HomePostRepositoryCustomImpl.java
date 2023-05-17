package GooRoom.projectgooroom.homepost.repository;

import GooRoom.projectgooroom.homepost.domain.HomePost;
import GooRoom.projectgooroom.homepost.domain.PostStatus;
import GooRoom.projectgooroom.global.embedded.RentType;
import GooRoom.projectgooroom.global.embedded.ResidenceType;
import GooRoom.projectgooroom.homepost.dto.HomePostFilterDto;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.NumberExpression;
import com.querydsl.jpa.impl.JPAQueryFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static GooRoom.projectgooroom.homepost.domain.QHomePost.homePost;
import static GooRoom.projectgooroom.member.domain.QMember.member;

@Repository
@Transactional
public class HomePostRepositoryCustomImpl implements HomePostRepositoryCustom {

    private JPAQueryFactory queryFactory;
    public HomePostRepositoryCustomImpl(JPAQueryFactory queryFactory) {
        this.queryFactory = queryFactory;
    }

    @Override
    public Page<HomePost> findHomePostByFilter(Pageable pageable, HomePostFilterDto homePostFilter, Long memberId) {
        //HomePost 조회.
        List<HomePost> content = queryFactory
                .selectFrom(homePost)
                .join(homePost.member, member)
                .where(
                        postTypeEq(homePostFilter.postStatus()),
                        hasHomeEq(homePostFilter.hasHome()),
                        residenceTypeEq(homePostFilter.residenceType()),
                        rentTypeEq(homePostFilter.rentType()),
                        priceBetween(homePostFilter.minPrice(), homePostFilter.maxPrice()),
                        addressDongEq(homePostFilter.dong()),
                        ageBetween(homePostFilter.minAge(), homePostFilter.maxAge()),
                        member.id.ne(memberId)
                )
                .fetch();
        //HomePost 수
        Long count = queryFactory
                .select(homePost.count())
                .from(homePost)
                .join(homePost.member, member)
                .where(
                        postTypeEq(homePostFilter.postStatus()),
                        hasHomeEq(homePostFilter.hasHome()),
                        residenceTypeEq(homePostFilter.residenceType()),
                        rentTypeEq(homePostFilter.rentType()),
                        addressDongEq(homePostFilter.dong()),
                        ageBetween(homePostFilter.minAge(), homePostFilter.maxAge()),
                        member.id.ne(memberId)
                )
                .fetchOne();
        return new PageImpl<>(content, pageable, count);
    }

    private BooleanExpression postTypeEq(PostStatus postStatus) {
        return postStatus != null ? homePost.postStatus.eq(postStatus) : null;
    }

    ///행정동 확인 쿼리
    private BooleanExpression addressDongEq(String dong) {
        return dong != null ? homePost.address.dong.eq(dong) : null;
    }

    ///작성자 나이 범위 확인 쿼리
    private BooleanExpression ageBetween(int minAge, int maxAge) {
        NumberExpression<Integer> ageExpression = member.age;
        if (minAge > 0 && maxAge > 0) {
            return ageExpression.between(minAge, maxAge);
        }
        return null;
    }

    ///가격 범위 확인 쿼리
    private BooleanExpression priceBetween(int minPrice, int maxPrice) {
        NumberExpression<Integer> priceExpression = homePost.roomPrice;
        if (minPrice > 0 && maxPrice > 0) {
            return priceExpression.between(minPrice, maxPrice);
        }
        return null;
    }

    ///rentType 확인 쿼리
    private BooleanExpression rentTypeEq(RentType rentType) {
        return rentType!=null?homePost.rentType.eq(rentType):null;
    }

    ///residenceType 확인 쿼리
    private BooleanExpression residenceTypeEq(ResidenceType residenceType) {
        return residenceType!=null?homePost.residenceType.eq(residenceType):null;
    }

    ///hasHome 확인 쿼리
    private BooleanExpression hasHomeEq(Boolean hashome) {
        return hashome!=null?homePost.hasHome.eq(hashome):null;
    }
}
