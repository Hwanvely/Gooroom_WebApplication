package GooRoom.projectgooroom.service;

import GooRoom.projectgooroom.domain.member.Member;
import GooRoom.projectgooroom.exception.MemberException;
import GooRoom.projectgooroom.repository.MemberRepository;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@SpringBootTest
@Transactional
class MemberServiceTest {

    @Autowired
    private EmailMemberService memberService;
    @Autowired
    private MemberRepository memberRepository;

    @Test
    public void 회원가입(){
        EmailSignupMemberDto memberDto = new EmailSignupMemberDto();
        memberDto.setBirth(LocalDate.of(2023,5,5));
        memberDto.setNickname("test");
        memberDto.setPassword("1234");
        memberDto.setName("test");
        memberDto.setEmail("test@test.com");
        memberDto.setPhoneNumber("1234");


        Member joinedMember = memberService.joinWithEmail(memberDto);
        Assertions.assertThat(memberRepository.findMemberByName(memberDto.getName()).get()).isEqualTo(joinedMember);
    }

    @Test()
    public void 중복회원검증(){
        org.junit.jupiter.api.Assertions.assertThrows(MemberException.class, ()->{
                    EmailSignupMemberDto memberDto = new EmailSignupMemberDto();
                    memberDto.setBirth(LocalDate.of(2023, 5, 5));
                    memberDto.setNickname("test");
                    memberDto.setPassword("1234");
                    memberDto.setName("test");
                    memberDto.setEmail("test@test.com");
                    memberDto.setPhoneNumber("1234");

                    EmailSignupMemberDto memberDto2 = new EmailSignupMemberDto();
                    memberDto2.setBirth(LocalDate.of(2023, 5, 5));
                    memberDto2.setNickname("test");
                    memberDto2.setPassword("1234");
                    memberDto2.setName("test");
                    memberDto2.setEmail("test@test.com");
                    memberDto2.setPhoneNumber("1234");

                    memberService.joinWithEmail(memberDto);
                    memberService.joinWithEmail(memberDto2);
                }
                );
    }
}