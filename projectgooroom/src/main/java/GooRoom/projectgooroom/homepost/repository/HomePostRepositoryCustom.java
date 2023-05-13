package GooRoom.projectgooroom.homepost.repository;

import GooRoom.projectgooroom.homepost.domain.HomePost;
import GooRoom.projectgooroom.homepost.dto.HomePostFilter;
import org.springframework.data.domain.Page;
import org.springframework.data.jpa.repository.JpaRepository;

import java.awt.print.Pageable;

public interface HomePostRepositoryCustom {
    Page<HomePost> findHomePostByFiter(Pageable pageable, HomePostFilter homePostFilter);
}
