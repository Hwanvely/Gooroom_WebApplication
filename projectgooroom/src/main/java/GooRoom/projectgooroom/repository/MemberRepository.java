package GooRoom.projectgooroom.repository;

import GooRoom.projectgooroom.domain.member.LoginType;
import GooRoom.projectgooroom.domain.member.Member;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MemberRepository extends JpaRepository<Member, Long> {
    Optional<Member> findMemberByName(String name);
    Optional<Member> findMemberById(Long id);

    Optional<Member> findMemberByEmail(String email);
    Optional<Member> findMemberByRefreshToken(String refreshToken);
    Optional<Member> findMemberByLoginTypeAndSocialId(LoginType loginType, String socialId);
}
