package GooRoom.projectgooroom.homepost.controller;

import GooRoom.projectgooroom.global.exception.HomePostException;
import GooRoom.projectgooroom.global.exception.HomePostExceptionType;
import GooRoom.projectgooroom.global.exception.MemberException;
import GooRoom.projectgooroom.global.exception.MemberExceptionType;
import GooRoom.projectgooroom.homepost.domain.HomePost;
import GooRoom.projectgooroom.homepost.domain.PostStatus;
import GooRoom.projectgooroom.global.embedded.RentType;
import GooRoom.projectgooroom.global.embedded.ResidenceType;
import GooRoom.projectgooroom.homepost.dto.*;
import GooRoom.projectgooroom.homepost.service.HomePostRecommendService;
import GooRoom.projectgooroom.homepost.service.HomePostService;
import GooRoom.projectgooroom.member.domain.Member;
import GooRoom.projectgooroom.member.service.MemberService;
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
import java.io.InputStream;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Slf4j
public class HomePostController {

    private final HomePostService homePostService;

    private final HomePostRecommendService recommendService;

    private final MemberService memberService;

    private static final String ROOM_IMAGE_PATH = "/Users/junseo/Documents/Study/Gooroom_WebApplication/projectgooroom/src/main/resources/image/homePost/";

    private static int PAGING_SIZE = 10;

    /**
     * 새 HomePost 작성
     * @param userDetails
     */
    @PostMapping("/mates")
    @Transactional
    public ResponseEntity postHomePost(@Valid @AuthenticationPrincipal UserDetails userDetails,
                                   @Valid @RequestPart("homePost") HomePostDto homePostDto,
                                   @Valid @RequestPart(value = "file", required = false)MultipartFile file){
        Member member = getMemberFromUserDetails(userDetails);
        List<HomePost> homePostList = member.getHomePostList();
        int homePostCount = homePostList.size();

        //진행 중인 게시글은 인당 1개만 등록 가능.
        if(homePostCount >0&&homePostList.get(homePostCount -1).getPostStatus()!=PostStatus.COMPLETE){
            throw new HomePostException(HomePostExceptionType.ALREADY_PROGRESS);
        }

        try{
            if(file!=null){
                String path = ROOM_IMAGE_PATH + UUID.randomUUID() + "_" + file.getOriginalFilename();
                homePostService.createHomePost(member.getId(), homePostDto, path);
                file.transferTo(new File(path));
                return ResponseEntity.status(HttpStatus.OK).build();
            }
            else{
                homePostService.createHomePost(member.getId(), homePostDto, null);
                return ResponseEntity.status(HttpStatus.OK).build();
            }
        }catch (Exception e){
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }
    }

    /**
     * postId를 통한 게시글 조회
     * 작성자 닉네임, 제목, 게시글 정보(hashome, postStatus, lastEditTime, residenceType, rentType, roomPrice, address, content)
     * @param postId
     * @return
     */
    @GetMapping("/mates/{postId}")
    public ResponseEntity getHomePost(@Valid @PathVariable("postId") Long postId) {
        HomePost homePost = homePostService.findOne(postId);
        Member member = homePost.getMember();

        try {
            boolean isPostmark = member.getPostmarkList().stream()
                    .anyMatch(postmark -> postmark.getPostId().equals(postId));
            GetHomePostDto getHomePostDto = new GetHomePostDto(homePost, member.getNickname(), member.getAge(), isPostmark);
            return new ResponseEntity(getHomePostDto, HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity(null, HttpStatus.NOT_FOUND);
        }
    }

    /**
     * HomePost 작성자 프로필사진 조회
     * @param postId
     * @return
     */
    @GetMapping("/mates/{postId}/profileImage")
    public ResponseEntity getHomePostProfileImage(@Valid @PathVariable("postId")Long postId){
        try{
            HomePost homePost = homePostService.findOne(postId);
            String profileImage = homePost.getMember().getMemberInformation().getProfileImage();

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
     * HomePost 방 사진 조회
     * @param postId
     * @return
     */
    @GetMapping("/mates/{postId}/roomImage")
    public ResponseEntity getHomePostRoomImage(@Valid @PathVariable("postId")Long postId){
        try{
            HomePost homePost = homePostService.findOne(postId);
            String roomImage = homePost.getRoomImage();

            File image = new File(roomImage);
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
     * HomPost 수정
     * 제목, 게시글상태(postStatus, rentType, roomPrice, address), content, roomImage 수정 가능
     * @param userDetails
     * @param postId
     * @param homePostDto
     * @param file
     */
    @PatchMapping("/mates/{postId}")
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    public void editHomePost(@AuthenticationPrincipal UserDetails userDetails,
                                       @Valid @PathVariable("postId")Long postId,
                                       @Valid @RequestPart("homePost") EditHomePostDto homePostDto,
                                       @Valid @RequestPart(value = "file", required = false)MultipartFile file){
        try{
            HomePost homePost = homePostService.findOne(postId);
            if(file!=null){
                //기존 파일 제거
                String oldFilePath = homePost.getRoomImage();
                File oldFile = new File(oldFilePath);
                if (oldFile.exists()) { // 파일이 존재하는지 확인
                    oldFile.delete();
                }

                //멤버 Id+업로드 파일명으로 저장.
                String path = ROOM_IMAGE_PATH + UUID.randomUUID() + "_" + file.getOriginalFilename();
                file.transferTo(new File(path));
                homePostService.updateHomePost(postId, userDetails.getUsername(), homePostDto, path);
            }
            else{
                homePostService.updateHomePost(postId, userDetails.getUsername(), homePostDto, null);
            }
        }catch (HomePostException e){
            throw e;
        }
        catch (Exception e){
            log.error(e.getMessage());
            throw new HomePostException(HomePostExceptionType.CANNOT_UPDATE_HOME_POST);
        }
    }

    /**
     * HomePost삭제
     * @param userDetails
     * @param postId
     */
    @DeleteMapping("mates/{postId}")
    @Transactional
    @ResponseStatus(HttpStatus.OK)
    public void deleteHomePost(@AuthenticationPrincipal UserDetails userDetails,
                               @Valid @PathVariable("postId")Long postId){
        try{
            homePostService.deleteHomePost(postId, userDetails.getUsername());
        }catch (Exception e){
            log.error(e.getMessage());
            throw e;
        }
    }

    /**
     * 필터에 따라 글 목록 조회, 협업필터링에 의해 정렬
     * @param userDetails
     * @param rentType
     * @param minPrice
     * @param maxPrice
     * @param residenceType
     * @param dong
     * @param minAge
     * @param maxAge
     * @param hasHome
     * @param postStatus
     * @param page
     * @return homePostListDto
     */
    @GetMapping("/mates")
    public ResponseEntity<HomePostListDto> getHomePostList(@AuthenticationPrincipal UserDetails userDetails,
                                                           @RequestParam Optional<RentType> rentType,
                                                           @RequestParam(defaultValue = "0") int minPrice,
                                                           @RequestParam(defaultValue = "999999999") int maxPrice,
                                                           @RequestParam Optional<ResidenceType> residenceType,
                                                           @RequestParam Optional<String> dong,
                                                           @RequestParam(defaultValue = "0") int minAge,
                                                           @RequestParam(defaultValue = "100") int maxAge,
                                                           @RequestParam Optional<Boolean> hasHome,
                                                           @RequestParam(defaultValue = "PROGRESS") Optional<PostStatus> postStatus,
                                                           @RequestParam(defaultValue = "0") int page
                                ){
        if(userDetails == null)
            throw new MemberException(MemberExceptionType.NOT_FOUND_MEMBER);

        String email = userDetails.getUsername();

        HomePostFilterDto homePostFilterDto = new HomePostFilterDto(
                postStatus.isPresent() ? postStatus.get() : null,
                rentType.isPresent() ? rentType.get() : null,
                minPrice,
                maxPrice,
                residenceType.isPresent() ? residenceType.get() : null,
                dong.isPresent() ? dong.get() : null,
                minAge,
                maxAge,
                hasHome.isPresent() ? hasHome.get().booleanValue() : null
        );
        if(homePostFilterDto.postStatus()==PostStatus.PROGRESS){
            Pageable pageable = PageRequest.of(page, PAGING_SIZE);
            Page<ListedPostDto> filteredPost = recommendService.findFilteredPostProgress(email, pageable, homePostFilterDto);
            int postCount = filteredPost.getContent().size();

            return new ResponseEntity<>(new HomePostListDto(postCount, filteredPost.getContent()),HttpStatus.OK);
        }
        else{
            Pageable pageable = PageRequest.of(page, PAGING_SIZE);
            Page<ListedPostDto> filteredPost = recommendService.findFilteredPostNotProgress(email, pageable, homePostFilterDto);
            int postCount = filteredPost.getContent().size();

            return new ResponseEntity<>(new HomePostListDto(postCount, filteredPost.getContent()),HttpStatus.OK);
        }

    }

    /**
     * HomePost 찜하기
     * @param userDetails
     * @param postId
     */
    @PostMapping("/mark/{postId}")
    @ResponseStatus(HttpStatus.OK)
    @Transactional
    public void addMyPostmark(@AuthenticationPrincipal UserDetails userDetails,
                              @PathVariable("postId") Long postId){
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

        PageImpl<ListedPostDto> postmarkList = memberService.getPostmarkList(member.getId(), pageable);

        List<ListedPostDto> listedPostDtoList = postmarkList.getContent();

        return new ResponseEntity(new HomePostListDto(listedPostDtoList.size(), listedPostDtoList), HttpStatus.OK);
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
