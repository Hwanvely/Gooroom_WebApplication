package GooRoom.projectgooroom.global.jwt;


import GooRoom.projectgooroom.member.domain.Member;
import GooRoom.projectgooroom.member.repository.MemberRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.security.SecureRandom;
import java.util.Random;

/**
 * Jwt 인증 필터
 * "/login", "/signup/" 이외의 URI 요청이 왔을 때 처리하는 필터
 * 1. AccessToken 검증, AccessToken이 유효한 경우 -> 인증 성공 처리
 * 2. AccessToken이 없거나 유효하지 않은 경우 -> 인증 실패 처리 HTTP 406반환
 * 3. Refresh Token 처리에서 AccessToken 재발급 또는 에러코드 반환
 */
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationProcessingFilter extends OncePerRequestFilter {

    private static final String NO_CHECK_URL_LOGIN = "/api/login/"; // "/login/**"으로 들어오는 요청은 Filter 작동 X
    private static final String NO_CHECK_URL_LOGOUT = "/api/signout"; // "/signout"으로 들어오는 요청은 Filter 작동 X
    private static final String NO_CHECK_URL_SIGNUP = "/api/signup/"; // "/signup/**"으로 들어오는 요청은 Filter 작동 X

    private static final String NO_CHECK_URL_ROOMS = "/api/rooms/";

    private static final String NO_CHECK_URL_NAVER = "/"; // "네이버 로그인"으로 들어오는 요청은 Filter 작동 X



    private final JwtService jwtService;
    private final MemberRepository memberRepository;


    private static final Random RANDOM = new SecureRandom();
    private static final String CHARACTERS = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";


    private GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (request.getRequestURI().startsWith(NO_CHECK_URL_LOGIN)) {
            filterChain.doFilter(request, response); // "/login/**" 요청이 들어오면, 다음 필터 호출
            return; // return으로 이후 현재 필터 진행 막기 (안해주면 아래로 내려가서 계속 필터 진행시킴)
        }
        if (request.getRequestURI().startsWith(NO_CHECK_URL_SIGNUP)) {
            filterChain.doFilter(request, response); // "/signup/**" 요청이 들어오면, 다음 필터 호출
            return; // return으로 이후 현재 필터 진행 막기 (안해주면 아래로 내려가서 계속 필터 진행시킴)
        }

        if (request.getRequestURI().equals(NO_CHECK_URL_NAVER)) {
            filterChain.doFilter(request, response); // "네이버 로그인 접근" 요청이 들어오면, 다음 필터 호출
            return; // return으로 이후 현재 필터 진행 막기 (안해주면 아래로 내려가서 계속 필터 진행시킴)
        }
        if (request.getRequestURI().equals(NO_CHECK_URL_LOGOUT)) {
            filterChain.doFilter(request, response); // "로그아웃" 요청이 들어오면, 다음 필터 호출
            return; // return으로 이후 현재 필터 진행 막기 (안해주면 아래로 내려가서 계속 필터 진행시킴)
        }

        if (request.getRequestURI().equals(NO_CHECK_URL_ROOMS)) {
            filterChain.doFilter(request, response); // "매물정보 접근" 요청이 들어오면, 다음 필터 호출
            return; // return으로 이후 현재 필터 진행 막기 (안해주면 아래로 내려가서 계속 필터 진행시킴)
        }

        checkAccessTokenAndAuthentication(request, response, filterChain);
    }


    /**
     * [액세스 토큰 체크 & 인증 처리 메소드]
     * request에서 extractAccessToken()으로 액세스 토큰 추출 후, isTokenValid()로 토큰 유효성 검증
     * 유효한 토큰이면, 액세스 토큰에서 extractEmail로 Email을 추출한 후 findByEmail()로 해당 이메일을 사용하는 유저 객체 반환
     * 그 유저 객체를 saveAuthentication()으로 인증 처리하여
     * 인증 허가 처리된 객체를 SecurityContextHolder에 담아 다음 필터 진행.
     */
    public void checkAccessTokenAndAuthentication(HttpServletRequest request, HttpServletResponse response,
                                                  FilterChain filterChain) throws ServletException, IOException {
        jwtService.extractAccessToken(request)
                .filter(jwtService::isTokenValid)
                .ifPresent(accessToken -> jwtService.extractEmail(accessToken)
                        .ifPresent(email -> memberRepository.findMemberByEmail(email)
                                .ifPresent(this::saveAuthentication)));
        filterChain.doFilter(request, response);
    }

    /**
     * [인증 허가 메소드]
     * 파라미터의 유저 : 우리가 만든 회원 객체 / 빌더의 유저 : UserDetails의 User 객체
     *
     * new UsernamePasswordAuthenticationToken()로 인증 객체인 Authentication 객체 생성
     * UsernamePasswordAuthenticationToken의 파라미터
     * 1. 위에서 만든 UserDetailsUser 객체 (유저 정보)
     * 2. credential(보통 비밀번호로, 인증 시에는 보통 null로 제거)
     * 3. Collection < ? extends GrantedAuthority>로,
     * UserDetails의 User 객체 안에 Set<GrantedAuthority> authorities이 있어서 getter로 호출한 후에,
     * new NullAuthoritiesMapper()로 GrantedAuthoritiesMapper 객체를 생성하고 mapAuthorities()에 담기
     *
     * SecurityContextHolder.getContext()로 SecurityContext를 꺼낸 후,
     * setAuthentication()을 이용하여 위에서 만든 Authentication 객체에 대한 인증 허가 처리
     */
    public void saveAuthentication(Member member) {
        String password = member.getPassword();
        if (password == null) {
            password = generateRandomPassword();
        }

        UserDetails userDetailsUser = User.builder()
                .username(member.getEmail())
                .password(password)
                .roles(member.getRole().name())
                .build();

        Authentication authentication =
                new UsernamePasswordAuthenticationToken(userDetailsUser, null,
                        authoritiesMapper.mapAuthorities(userDetailsUser.getAuthorities()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
    }

    /**
     * 소셜 로그인 시 비밀번호 임의 설정
     * @return {@link String} password
     */
    private String generateRandomPassword() {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 15; i++) {
            int randomIndex = RANDOM.nextInt(CHARACTERS.length());
            sb.append(CHARACTERS.charAt(randomIndex));
        }
        return sb.toString();
    }
}
