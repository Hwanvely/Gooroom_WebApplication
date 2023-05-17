package GooRoom.projectgooroom.member.controller;

import GooRoom.projectgooroom.global.jwt.JwtService;
import GooRoom.projectgooroom.homepost.domain.HomePost;
import GooRoom.projectgooroom.homepost.dto.HomePostListDto;
import GooRoom.projectgooroom.homepost.dto.ListedPostDto;
import GooRoom.projectgooroom.homepost.dto.ListedPostmarkDto;
import GooRoom.projectgooroom.homepost.dto.PostmarkListDto;
import GooRoom.projectgooroom.homepost.service.HomePostService;
import GooRoom.projectgooroom.member.domain.Member;
import GooRoom.projectgooroom.member.domain.MemberInformation;
import GooRoom.projectgooroom.global.exception.MemberException;
import GooRoom.projectgooroom.global.exception.MemberExceptionType;
import GooRoom.projectgooroom.member.dto.*;
import GooRoom.projectgooroom.member.service.MemberInformationService;
import GooRoom.projectgooroom.member.service.MemberService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.*;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequiredArgsConstructor
@Slf4j
public class MemberController {

    private final MemberService memberService;

    private final MemberInformationService memberInformationService;

    private final JwtService jwtService;

    private final HomePostService homePostService;

    //프로필 사진 절대 경로 지정
    private static final String PROFILE_IMAGE_PATH = "/Users/junseo/Documents/Study/Gooroom_WebApplication/projectgooroom/src/main/resources/image/user/";

    /**
     * Email을 통한 회원가입
     * @param emailSignupDto
     * @throws Exception 중복회원 가입시 예외
     */
    @PostMapping("/signup/email")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void createByEmail(@Valid @RequestBody EmailSignupDto emailSignupDto) throws Exception{
        Member member = memberService.joinWithEmail(emailSignupDto);
        log.info("Create member by email: " +emailSignupDto.email());
    }

    /**
     * 내 정보 조회
     * @param userDetails
     * @param response
     * @return
     * @throws Exception
     */
    @GetMapping("/users")
    public ResponseEntity getMyInfo(@AuthenticationPrincipal UserDetails userDetails,
                                    HttpServletResponse response) throws Exception {

        if(userDetails == null)
            throw new MemberException(MemberExceptionType.NOT_FOUND_MEMBER);

        String email = userDetails.getUsername();
        Member member = memberService.findOneByEmail(email);


        MemberDto info = memberService.getMyInfo(member.getId());
        return new ResponseEntity(info, HttpStatus.OK);
    }

    /**
     * 회원 정보 조회
     * @param id(memberId)
     * @return
     * @throws Exception
     */
    @GetMapping("/users/{id}")
    public ResponseEntity getInfo(@Valid @PathVariable("id") Long id) throws Exception {
        MemberDto memberDto = memberService.getInfo(id);
        return new ResponseEntity(memberDto, HttpStatus.OK);
    }

    /**
     * 회원 정보 수정
     * @param userDetails
     * @param memberUpdateDto
     * @throws Exception
     */
    @PutMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public void updateBasicInfo(@AuthenticationPrincipal UserDetails userDetails,
            @Valid @RequestBody MemberUpdateDto memberUpdateDto) throws Exception {

        if(userDetails == null)
            throw new MemberException(MemberExceptionType.NOT_FOUND_MEMBER);

        String email = userDetails.getUsername();
        Member member = memberService.findOneByEmail(email);

        memberService.update(member.getId(),memberUpdateDto);
    }

    /**
     * 회원 삭제, MemberInformation 또한 삭제.
     * @param userDetails
     * @param memberWithdrawDto
     * @throws Exception
     */
    @DeleteMapping("/users")
    @ResponseStatus(HttpStatus.OK)
    public void withdraw( @AuthenticationPrincipal UserDetails userDetails,
                          @Valid @RequestBody MemberWithdrawDto memberWithdrawDto) throws Exception {

        if(userDetails == null)
            throw new MemberException(MemberExceptionType.NOT_FOUND_MEMBER);

        String email = userDetails.getUsername();
        Member member = memberService.findOneByEmail(email);

        memberService.withdraw(member.getId(), memberWithdrawDto.checkPassword());
    }

    /**
     * nickname을 통한 MemberInformation 조회
     * @param nickname
     * @return
     * @throws Exception
     */
    @GetMapping("/users/lifestyle/{nickname}")
    public ResponseEntity getMemberInformation(@Valid @PathVariable("nickname") String nickname) throws Exception {
        MemberGetInformationDto memberInformation = memberService.getMemberInformation(nickname);
        return new ResponseEntity(memberInformation, HttpStatus.OK);
    }

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
        Member findMember = memberService.findOneByEmail(email);

        if(findMember.getMemberInformation()!=null){
            throw new MemberException(MemberExceptionType.ALREADY_EXIST_MEMBERINFORMATION);
        }

        memberInformationService.createMemberInformation(findMember.getId(), informationDto);
    }

    /**
     * nickname을 통한 프로필이미지 조회
     * @param nickname
     * @return
     * @throws IOException
     */
    @GetMapping("/users/profileImage/{nickname}")
    public ResponseEntity getProfileImage(@Valid @PathVariable("nickname") String nickname)throws IOException{
        try{
            MemberInformation memberInformation = memberService.findOneByNickname(nickname).getMemberInformation();
            String profileImage = memberInformation.getProfileImage();
            File image = new File(profileImage);
            InputStream inputStream = new FileInputStream(image);
            MediaType imageType;

            if (image.getName().endsWith(".jpg") || image.getName().endsWith(".jpeg")
                    ||image.getName().endsWith(".JPG")||image.getName().endsWith(".JPEG")) {
                imageType = MediaType.IMAGE_JPEG;
            } else if (image.getName().endsWith(".png")) {
                imageType = MediaType.IMAGE_PNG;
            } else {
                throw new IllegalArgumentException("Unsupported image format");
            }

            // Response 헤더 설정
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(imageType);

            return new ResponseEntity(new InputStreamResource(inputStream), headers, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
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

    /**
     * 비밀번호 수정
     */
    @PatchMapping("/users/password")
    @ResponseStatus(HttpStatus.OK)
    public void updatePassword(@AuthenticationPrincipal UserDetails userDetails,
                               @Valid @RequestBody UpdatePasswordDto updatePasswordDto) throws Exception {
        if(userDetails == null)
            throw new MemberException(MemberExceptionType.NOT_FOUND_MEMBER);

        String email = userDetails.getUsername();
        Member member = memberService.findOneByEmail(email);

        memberService.updatePassword(member.getId(),updatePasswordDto.checkPassword(),updatePasswordDto.toBePassword());
    }

    /**
     * Refresh Token을 통해 Access Token 갱신
     * @param request
     * @param response
     */
    @GetMapping("/login/refresh")
    public void refreshAccessToken(HttpServletRequest request, HttpServletResponse response){
        jwtService.extractRefreshToken(request)
                .ifPresent(refreshToken -> jwtService.reissueAccessToken(response, refreshToken));
    }

    /**
     * 내가 작성한 게시글 조회
     * @param userDetails
     * @param page
     * @return
     */
    @GetMapping("/users/mates")
    public ResponseEntity getMyHomePosts(@AuthenticationPrincipal UserDetails userDetails,
                                         @RequestParam(defaultValue = "0")int page){
        Member member = getMemberFromUserDetails(userDetails);

        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "lastEditTime"));

        Page<HomePost> allPostsByMember = homePostService.findAllPostsByMember(member.getId(), pageable);
        List<HomePost> homePostList = allPostsByMember.getContent();

        List<ListedPostDto> listedPostDtoList = new ArrayList<>();

        homePostList.forEach(homePost -> listedPostDtoList.add(new ListedPostDto(homePost, member.getNickname(), member.getAge())));

        return new ResponseEntity(new HomePostListDto(listedPostDtoList.size(), listedPostDtoList), HttpStatus.OK);
    }

    /**
     * HomePost 찜하기
     * @param userDetails
     * @param postId
     */
    @PostMapping("/users/matesmark/{postId}")
    @ResponseStatus(HttpStatus.OK)
    public void addMyPostmark(@AuthenticationPrincipal UserDetails userDetails,
                              @PathVariable Long postId){
        homePostService.addPostMark(userDetails.getUsername(), postId);
    }

    /**
     * 내가 찜한 게시글 조회
     * @param userDetails
     * @param page
     * @return
     */
    @GetMapping("/users/matesmark")
    public ResponseEntity getMyPostmarks(@AuthenticationPrincipal UserDetails userDetails,
                                         @RequestParam(defaultValue = "0")int page){
        Member member = getMemberFromUserDetails(userDetails);

        Pageable pageable = PageRequest.of(page, 10, Sort.by(Sort.Direction.DESC, "lastEditTime"));

        PageImpl<ListedPostmarkDto> postmarkList = memberService.getPostmarkList(member.getId(), pageable);

        List<ListedPostmarkDto> listedPostDtoList = postmarkList.getContent();

        return new ResponseEntity(new PostmarkListDto(listedPostDtoList.size(), listedPostDtoList), HttpStatus.OK);
    }

    /**
     * 찜한 게시글 찜 목록에서 삭제
     * @param userDetails
     * @param postmarkId
     */
    @DeleteMapping("/users/matesmark/{postmarkId}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void deleteMyPostmark(@AuthenticationPrincipal UserDetails userDetails,
                                 @PathVariable Long postmarkId){
        Member member = getMemberFromUserDetails(userDetails);
        try {
            member.getPostmarkList().removeIf(postmark -> postmark.getId() == postmarkId);
        }catch (Exception e){
            throw new MemberException(MemberExceptionType.CANNOT_DELETE_POSTMARK);
        }
    }

    /**
     * UserDetails로 부터 email출 후 Member 반환
     * @param userDetails
     * @return member
     */
    private Member getMemberFromUserDetails(UserDetails userDetails) {
        if(userDetails == null)
            throw new MemberException(MemberExceptionType.NOT_FOUND_MEMBER);

        Member member = memberService.findOneByEmail(userDetails.getUsername());
        return member;
    }

}
