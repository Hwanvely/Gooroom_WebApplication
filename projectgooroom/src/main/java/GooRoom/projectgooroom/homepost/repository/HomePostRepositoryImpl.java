package GooRoom.projectgooroom.homepost.repository;

import GooRoom.projectgooroom.homepost.domain.HomePost;
import GooRoom.projectgooroom.homepost.dto.HomePostFilter;
import GooRoom.projectgooroom.homepost.dto.HomePostListDto;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.awt.print.Pageable;

import static GooRoom.projectgooroom.homepost.domain.QHomePost.homePost;

@Repository
@Transactional
@RequiredArgsConstructor
public class HomePostRepositoryImpl implements HomePostRepositoryCustom {

    private final JPAQueryFactory queryFactory;
    @Override
    public Page<HomePost> findHomePostByFiter(Pageable pageable, HomePostFilter homePostFilter) {
        return null;
    }
}
