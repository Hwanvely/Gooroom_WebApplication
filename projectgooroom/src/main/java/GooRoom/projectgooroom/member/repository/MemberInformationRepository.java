package GooRoom.projectgooroom.member.repository;

import GooRoom.projectgooroom.member.domain.MemberInformation;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberInformationRepository extends JpaRepository<MemberInformation, Long> {

    /**
     * Id를 통한 검색
     * @param memberInformationId must not be {@literal null}.
     * @return
     */
    Optional<MemberInformation> findById(Long memberInformationId);
}
