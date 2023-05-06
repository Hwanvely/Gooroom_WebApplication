package GooRoom.projectgooroom.service;

import GooRoom.projectgooroom.domain.member.LoginType;
import GooRoom.projectgooroom.domain.member.Member;
import GooRoom.projectgooroom.domain.member.Role;
import GooRoom.projectgooroom.exception.MemberException;
import GooRoom.projectgooroom.exception.MemberExceptionType;
import GooRoom.projectgooroom.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
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
public class EmailMemberService {


    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

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
                .birthday(memberDto.getBirthday())
                .birthyear(memberDto.getBirthyear())
                .email(memberDto.getEmail())
                .gender(memberDto.getGender())
                .password(memberDto.getPassword())
                .mobile(memberDto.getMobile())
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
}
