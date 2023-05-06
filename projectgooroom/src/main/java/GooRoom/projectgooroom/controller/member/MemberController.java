package GooRoom.projectgooroom.controller.member;

import GooRoom.projectgooroom.domain.member.Member;
import GooRoom.projectgooroom.exception.MemberException;
import GooRoom.projectgooroom.exception.MemberExceptionType;
import GooRoom.projectgooroom.service.MemberInformationDto;
import GooRoom.projectgooroom.service.MemberInformationService;
import GooRoom.projectgooroom.service.MemberService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;
    private final MemberInformationService memberInformationService;

    //프로필 사진 경로 지정
    private static final String PROFILE_IMAGE_PATH = "/Users/junseo/Documents/Study/Gooroom_WebApplication/projectgooroom/src/main/resources/image/user/";

    /**
     * MemberInformation 저장 및 Member와 연결
     * @param member
     * @param informationDto
     * @throws IOException
     * @throws MemberException
     */
    @PostMapping("/users/lifestyle")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void createInformation(
            @AuthenticationPrincipal UserDetails member,
            @Valid @RequestBody MemberInformationDto informationDto) throws IOException, MemberException {

        if(member == null)
            throw new MemberException(MemberExceptionType.NOT_FOUND_MEMBER);

        String email = member.getUsername();
        Long memberId = memberService.findOneByEmail(email).getId();

        memberInformationService.createMemberInformation(memberId, informationDto);
    }

    /**
     * 프로필 사진 저장
     * @param member
     * @param file
     * @throws IOException
     */
    @PostMapping("/users/profileImage")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void addProfileImage(
            @AuthenticationPrincipal UserDetails member,
            @Valid @RequestPart ("file") MultipartFile file) throws IOException {
        if(member==null)
            throw new MemberException(MemberExceptionType.NOT_FOUND_MEMBER);

        String email = member.getUsername();
        Long memberId = memberService.findOneByEmail(email).getId();

        if(!file.isEmpty()){
            //멤버 Id+업로드 파일명으로 저장.
            String path = PROFILE_IMAGE_PATH+memberId.toString()+"_"+file.getOriginalFilename();
            file.transferTo(new File(path));
            memberInformationService.addProfileImage(memberId, path);
        }
    }

    /**
     * MemberInformation 수정
     * @param userDetails
     * @param informationDto
     * @throws IOException
     * @throws MemberException
     */
    @PatchMapping("/users/lifestyle")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void editMemberInformation(@AuthenticationPrincipal UserDetails userDetails,
                                      @Valid @RequestBody MemberInformationDto informationDto) throws IOException, MemberException{
        if(userDetails == null)
            throw new MemberException(MemberExceptionType.NOT_FOUND_MEMBER);

        String email = userDetails.getUsername();
        Member member = memberService.findOneByEmail(email);

        memberInformationService.editMemberInformation(member.getId(), informationDto);
    }

    /**
     * 프로필사진 수정
     * @param userDetails
     * @param file
     * @throws IOException
     */
    @PatchMapping("/users/profileImage")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void editProfileImage(
            @AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestPart ("file") MultipartFile file) throws IOException {
        if(userDetails == null)
            throw new MemberException(MemberExceptionType.NOT_FOUND_MEMBER);

        String email = userDetails.getUsername();
        Member member = memberService.findOneByEmail(email);

        if(!file.isEmpty()){
            //기존 파일 제거
            String oldFilePath = member.getMemberInformation().getProfileImage();
            File oldFile = new File(oldFilePath);
            if (oldFile.exists()) { // 파일이 존재하는지 확인
                oldFile.delete();
            }

            //멤버 Id+업로드 파일명으로 저장.
            String path = PROFILE_IMAGE_PATH+member.getId().toString()+"_"+file.getOriginalFilename();
            file.transferTo(new File(path));
            memberInformationService.addProfileImage(member.getId(), path);
        }
    }
}
