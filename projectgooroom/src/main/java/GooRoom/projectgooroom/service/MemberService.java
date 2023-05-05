package GooRoom.projectgooroom.service;

import GooRoom.projectgooroom.domain.member.LoginType;
import GooRoom.projectgooroom.domain.member.Member;
import GooRoom.projectgooroom.domain.member.Role;
import GooRoom.projectgooroom.exception.MemberException;
import GooRoom.projectgooroom.exception.MemberExceptionType;
import GooRoom.projectgooroom.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {

    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public Member joinWithEmail(EmailSignupMemberDto memberDto) throws MemberException{
        validateDuplicateMember(memberDto);

        Member member = Member.builder()
                .name(memberDto.getName())
                .birth(memberDto.getBirth())
                .email(memberDto.getEmail())
                .gender(memberDto.getGender())
                .password(passwordEncoder.encode(memberDto.getPassword()))
                .phoneNumber(memberDto.getPhoneNumber())
                .nickname(memberDto.getNickname())
                .loginType(LoginType.EMAIL)
                .role(Role.USER)
                .build();

        memberRepository.save(member);
        return member;
    }

    public Member findOne(Long memberId){
        return memberRepository.findMemberById(memberId).get();
    }

    private void validateDuplicateMember(EmailSignupMemberDto memberDto) {
        if(!memberRepository.findMemberByEmail(memberDto.getEmail()).isEmpty())
            throw new MemberException(MemberExceptionType.ALREADY_EXIST_USEREMAIL);
    }
}
