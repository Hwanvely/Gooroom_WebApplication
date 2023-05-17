package GooRoom.projectgooroom.member.service;

import GooRoom.projectgooroom.homepost.dto.ListedPostmarkDto;
import GooRoom.projectgooroom.homepost.repository.PostmarkRepositoryCustom;
import GooRoom.projectgooroom.member.domain.LoginType;
import GooRoom.projectgooroom.member.domain.Member;
import GooRoom.projectgooroom.member.domain.MemberInformation;
import GooRoom.projectgooroom.member.domain.Role;
import GooRoom.projectgooroom.global.exception.MemberException;
import GooRoom.projectgooroom.global.exception.MemberExceptionType;
import GooRoom.projectgooroom.member.dto.*;
import GooRoom.projectgooroom.member.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.util.ArrayList;


/**
 * Email 회원가입 시 MemberService
 */
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class MemberService {


    private final MemberRepository memberRepository;

    private final PostmarkRepositoryCustom postmarkRepositoryCustom;

    private final PasswordEncoder passwordEncoder;

    /**
     * Email을 통한 회원가입
     * @param emailSignupDto
     * @return Member 회원가입한 Member
     * @throws MemberException
     */
    @Transactional
    public Member joinWithEmail(EmailSignupDto emailSignupDto) throws MemberException{
        validateDuplicateMember(emailSignupDto.email(), emailSignupDto.nickname());

        Member member = Member.builder()
                .name(emailSignupDto.name())
                .birthday(emailSignupDto.birthday())
                .birthyear(emailSignupDto.birthyear())
                .email(emailSignupDto.email())
                .gender(emailSignupDto.gender())
                .password(emailSignupDto.password())
                .mobile(emailSignupDto.mobile())
                .nickname(emailSignupDto.nickname())
                .loginType(LoginType.EMAIL)
                .role(Role.USER)
                .homePostList(new ArrayList<>())
                .postmarkList(new ArrayList<>())
                .build();

        member.calculateAge();
        member.passwordEncode(passwordEncoder);
        memberRepository.save(member);
        return member;
    }

    /**
     * nickname, mobile 수정
     * @param memberUpdateDto
     */
    @Transactional
    public void update(Long memberId, MemberUpdateDto memberUpdateDto) throws Exception{
        Member member = memberRepository.findMemberById(memberId).get();
        memberUpdateDto.nickname().ifPresent(member::updateNickname);
        memberUpdateDto.mobile().ifPresent(member::updateMobile);
    }

    /**
     * 비밀번호 수정
     * @param checkPassword
     */
    @Transactional
    public void updatePassword(Long memberId, String checkPassword, String toBePassword)throws Exception{
        Member member = memberRepository.findMemberById(memberId).get();
        if(!member.matchPassword(passwordEncoder, checkPassword)){
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }
        member.updatePassword(passwordEncoder,toBePassword);
    }

    /**
     * 회원탈퇴
     * @param checkPassword
     */
    @Transactional
    public void withdraw(Long memberId, String checkPassword) throws Exception {
        Member member = memberRepository.findMemberById(memberId).get();

        if(!member.matchPassword(passwordEncoder, checkPassword) ) {
            throw new Exception("비밀번호가 일치하지 않습니다.");
        }

        //프로필 사진 삭제
        if(member.getMemberInformation().getProfileImage()!=null){
            String profileImagePath = member.getMemberInformation().getProfileImage();
            File oldFile = new File(profileImagePath);
            if (oldFile.exists()) { // 파일이 존재하는지 확인
                oldFile.delete();
            }
        }

        memberRepository.delete(member);
    }

    /**
     * 회원 조회 by id (해당 id의 회원조회)
     * @param memberId
     */
    public MemberDto getInfo(Long memberId) throws Exception {
        Member member = memberRepository.findMemberById(memberId).get();
        return new MemberDto(member);
    }

    /**
     * Id를 통한 나의 정보 조회
     * @param memberId
     * @return MemberDto
     */
    public MemberDto getMyInfo(Long memberId) throws Exception {
        Member member = memberRepository.findMemberById(memberId).get();
        return new MemberDto(member);
    }

    /**
     * nickname을 통한 MemberInformation조회
     * @param nickname
     * @return MemberInformationDto
     * @throws Exception
     */
    public MemberGetInformationDto getMemberInformation(String nickname) throws Exception{
        try{
            MemberInformation memberInformation = memberRepository.findMemberByNickname(nickname).get().getMemberInformation();
            return new MemberGetInformationDto(
                    memberInformation.getMember().getName(),
                    memberInformation.getMember().getGender(),
                    memberInformation.getMember().getAge(),
                    memberInformation.getSmokingType(),
                    memberInformation.getDrinkingType(),
                    memberInformation.getSleepingHabitType(),
                    memberInformation.getWakeupType(),
                    memberInformation.getOrganizeType(),
                    memberInformation.getCleanupType(),
                    memberInformation.getIntroduce()
            );
        }catch (Exception e){
            throw new MemberException(MemberExceptionType.NOT_FOUND_MEMBER);
        }
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
     * email을 통한 Member 검색
     * @param email
     * @return
     */
    public Member findOneByEmail(String email){
        try{
            return memberRepository.findMemberByEmail(email).get();
        }catch (Exception e){
            throw new MemberException(MemberExceptionType.NOT_FOUND_MEMBER);
        }
    }

    /**
     * nickname을 통한 Member 검색
     * @param nickname
     * @return member
     */
    public Member findOneByNickname(String nickname){
        try{
            return memberRepository.findMemberByNickname(nickname).get();
        }catch (Exception e){
            throw new MemberException(MemberExceptionType.NOT_FOUND_MEMBER);
        }
    }

    /**
     * 찜 목록 조회
     *
     * @param memberId
     * @param pageable
     * @return
     */
    public PageImpl<ListedPostmarkDto> getPostmarkList(Long memberId, Pageable pageable){
        return postmarkRepositoryCustom.findAllByMember_Id(memberId, pageable);
    }

    /**
     * Email 중복확인
     * @param email, nickname
     */
    private void validateDuplicateMember(String email, String nickname) {
        if(!memberRepository.findMemberByEmail(email).isEmpty())
            throw new MemberException(MemberExceptionType.ALREADY_EXIST_USER_EMAIL);
        if(!memberRepository.findMemberByNickname(nickname).isEmpty())
            throw new MemberException(MemberExceptionType.ALREADY_EXIST_USER_NICKNAME);
    }
}
