package GooRoom.projectgooroom.global.login.controller;

import GooRoom.projectgooroom.domain.member.Member;
import GooRoom.projectgooroom.service.EmailSignupMemberDto;
import GooRoom.projectgooroom.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class EmailLoginController {

    private final MemberService emailMemberService;

    /**
     * Email을 통한 회원가입
     * @param memberDto
     * @throws Exception 중복회원 가입시 예외
     */
    @PostMapping("/signup/email")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void createByEmail(@Valid @RequestBody EmailSignupMemberDto memberDto) throws Exception{
        Member member = emailMemberService.joinWithEmail(memberDto);
        log.info("Create member by email: " +memberDto.getEmail());
    }
}
