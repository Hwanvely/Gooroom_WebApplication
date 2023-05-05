package GooRoom.projectgooroom.service.member;

import GooRoom.projectgooroom.domain.member.LoginType;
import GooRoom.projectgooroom.domain.member.Member;
import GooRoom.projectgooroom.domain.member.Role;
import GooRoom.projectgooroom.exception.MemberException;
import GooRoom.projectgooroom.exception.MemberExceptionType;
import GooRoom.projectgooroom.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Email 회원가입 시 MemberService
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class EmailMemberService implements UserDetailsService {

    private final MemberRepository memberRepository;
    private static final PasswordEncoder passwordEncoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();

    /**
     * Email을 통한 회원가입
     * @param memberDto
     * @return Member 회원가입한 Member
     * @throws MemberException
     */
    @Transactional
    public Member joinWithEmail(EmailSignupMemberDto memberDto) throws MemberException{
        validateDuplicateMember(memberDto);

        Member member = Member.builder()
                .name(memberDto.getName())
                .birth(memberDto.getBirth())
                .email(memberDto.getEmail())
                .gender(memberDto.getGender())
                .password(memberDto.getPassword())
                .phoneNumber(memberDto.getPhoneNumber())
                .nickname(memberDto.getNickname())
                .loginType(LoginType.EMAIL)
                .role(Role.USER)
                .build();

        member.passwordEncode(passwordEncoder);
        memberRepository.save(member);
        return member;
    }

    /**
     * Id를 통한 Member검색
     * @param memberId
     * @return Member
     */
    public Member findOne(Long memberId){
        return memberRepository.findMemberById(memberId).get();
    }

    /**
     * Email 중복확인
     * @param memberDto
     */
    private void validateDuplicateMember(EmailSignupMemberDto memberDto) {
        if(!memberRepository.findMemberByEmail(memberDto.getEmail()).isEmpty())
            throw new MemberException(MemberExceptionType.ALREADY_EXIST_USER_EMAIL);
    }

    /**
     * Json을 통한 로그인의 UserDetailService 구현
     * userName 대신 email 사용
     * @param email the username identifying the user whose data is required.
     * @return UserDetails 객체 반환(email, password)
     * @throws MemberException
     */
    @Override
    public UserDetails loadUserByUsername(String email) throws MemberException {
        Member member = memberRepository.findMemberByEmail(email)
                .orElseThrow(() -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER));
        return User.builder()
                .username(member.getEmail())
                .password(member.getPassword())
                .roles(Role.USER.toString())
                .build();
    }
}
