package GooRoom.projectgooroom.global.jwt;


import GooRoom.projectgooroom.global.exception.MemberException;
import GooRoom.projectgooroom.global.exception.MemberExceptionType;
import GooRoom.projectgooroom.member.repository.MemberRepository;
import com.auth0.jwt.algorithms.Algorithm;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.auth0.jwt.JWT;
import org.springframework.transaction.annotation.Transactional;


import java.util.Arrays;
import java.util.Date;
import java.util.Optional;


@Service
@RequiredArgsConstructor
@Getter
@Slf4j
public class JwtService {

    /**
     * application.yml 프로퍼티 주입.
     */
    @Value("${jwt.secretKey}")
    private String secretKey;

    @Value("${jwt.access.expiration}")
    private int accessTokenExpirationPeriod;

    @Value("${jwt.refresh.expiration}")
    private int refreshTokenExpirationPeriod;

    @Value("${jwt.access.header}")
    private String accessHeader;

    @Value("${jwt.refresh.header}")
    private String refreshHeader;

    @Value("${jwt.cookie.path}")
    private String COOKIE_PATH;

    @Value("${jwt.cookie.domain}")
    private String COOKIE_DOMAIN;

    /**
     * JWT의 Subject와 Claim으로 email 사용 -> 클레임의 name을 "email"으로 설정
     * JWT의 헤더에 들어오는 값 : 'Authorization(Key) = Bearer {토큰} (Value)'
     */
    private static final String ACCESS_TOKEN_SUBJECT = "AccessToken";
    private static final String REFRESH_TOKEN_SUBJECT = "RefreshToken";
    private static final String EMAIL_CLAIM = "email";
    private static final String BEARER = "Bearer ";

    private final MemberRepository memberRepository;

    /**
     * AccessToken 생성 메소드
     */
    public String createAccessToken(String email) {
        Date now = new Date();
        return JWT.create() // JWT 토큰을 생성
                .withSubject(ACCESS_TOKEN_SUBJECT) // JWT의 Subject: AccessToken
                .withExpiresAt(new Date(now.getTime() + accessTokenExpirationPeriod)) // 토큰 만료 시간 설정
                .withClaim(EMAIL_CLAIM, email)  //Claim으로 이메일 설정
                .sign(Algorithm.HMAC512(secretKey)); // HMAC512 알고리즘, secret 키로 암호화
    }

    /**
     * RefreshToken 생성
     */
    public String createRefreshToken() {
        Date now = new Date();
        return JWT.create()
                .withSubject(REFRESH_TOKEN_SUBJECT)
                .withExpiresAt(new Date(now.getTime() + refreshTokenExpirationPeriod))
                .sign(Algorithm.HMAC512(secretKey));
    }

    /**
     * AccessToken 헤더에 실어서 보내기
     */
    public void sendAccessToken(HttpServletResponse response, String accessToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        response.setHeader(accessHeader, accessToken);
        log.info("재발급된 Access Token : {}", accessToken);
    }

    /**
     * AccessToken은 헤더에, RefreshToken은 쿠키에 보내기.
     */
    public void sendAccessAndRefreshToken(HttpServletResponse response, String accessToken, String refreshToken) {
        response.setStatus(HttpServletResponse.SC_OK);

        setAccessTokenHeader(response, accessToken);
        setRefreshTokenCookie(response, refreshToken);
        log.info("Access Token 헤더, Refresh Token 쿠키 설정 완료.");
    }

    /**
     * 쿠키에서 RefreshToken 추출
     * 토큰 형식 : Bearer XXX -> "" XXX
     */
    public Optional<String> extractRefreshToken(HttpServletRequest request) {
        try{
            Cookie refreshCookie = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(refreshHeader))
                    .findFirst()
                    .orElse(null);
            if (refreshCookie == null) {
                throw new MemberException(MemberExceptionType.REFRESH_TOKEN_NOT_EXIST);
            }
            return Optional.ofNullable(refreshCookie.getValue());
        }catch(Exception e){
            new MemberException(MemberExceptionType.REFRESH_TOKEN_NOT_EXIST);
        }
        return Optional.empty();
    }

    /**
     * 헤더에서 AccessToken 추출
     * 토큰 형식 : Bearer XXX -> "" XXX
     */
    public Optional<String> extractAccessToken(HttpServletRequest request) {
        return Optional.ofNullable(request.getHeader(accessHeader))
                .filter(refreshToken -> refreshToken.startsWith(BEARER))
                .map(refreshToken -> refreshToken.replace(BEARER, ""));
    }


    /**
     * AccessToken에서 Email 추출
     * 추출 전에 JWT.require() verify로 검증 후 이메일 추출
     * @param accessToken
     * @return {@link Optional} containing the {@link String} email
     */
    public Optional<String> extractEmail(String accessToken) {
        try {
            // 토큰 유효성 검사하는 데에 사용할 알고리즘이 있는 JWT verifier builder 반환
            return Optional.ofNullable(JWT.require(Algorithm.HMAC512(secretKey))
                    .build() // 반환된 빌더로 JWT verifier 생성
                    .verify(accessToken) // accessToken을 검증하고 유효하지 않다면 예외 발생
                    .getClaim(EMAIL_CLAIM) // claim(Emial) 가져오기
                    .asString());
        } catch (Exception e) {
            log.error("액세스 토큰이 유효하지 않습니다.");
            return Optional.empty();
        }
    }

    /**
     * AccessToken 헤더 설정
     */
    public void setAccessTokenHeader(HttpServletResponse response, String accessToken) {
        response.setHeader(accessHeader, accessToken);
    }

    /**
     * RefreshToken 쿠키 설정
     */
    public void setRefreshTokenCookie(HttpServletResponse response, String refreshToken){
        Cookie cookie = new Cookie(refreshHeader, refreshToken);
        cookie.setMaxAge(accessTokenExpirationPeriod);
        cookie.setHttpOnly(true);
        cookie.setPath(COOKIE_PATH);
//        cookie.setDomain(COOKIE_DOMAIN);
//        cookie.setSecure(true);
        response.addCookie(cookie);
    }

    /**
     * RefreshToken DB 저장(업데이트)
     */
    @Transactional
    public void updateRefreshToken(String email, String refreshToken) {
        memberRepository.findMemberByEmail(email)
                .ifPresentOrElse(
                        user -> user.updateRefreshToken(refreshToken),
                        () -> new MemberException(MemberExceptionType.NOT_FOUND_MEMBER)
                );
    }

    /**
     * Refresh Token 을 통해 AccessToken 재발급
     * @param response
     * @param refreshToken
     */
    public void reissueAccessToken(HttpServletResponse response, String refreshToken){
        try {
            if(isTokenValid(refreshToken)) {
                String email = memberRepository.findMemberByRefreshToken(refreshToken).get().getEmail();
                String accessToken = createAccessToken(email);
                setAccessTokenHeader(response, accessToken);
            }
            else {
                throw new MemberException(MemberExceptionType.INVALIDATE_REFRESH_TOKEN);
            }
        }catch (Exception e){
            throw new MemberException(MemberExceptionType.NOT_FOUND_MEMBER);
        }
    }

    /**
     * 로그아웃 시 쿠키 만료를 통한 Refresh Token 만료.
     * @param response
     * @param request
     */
    public void expireRefreshToken(HttpServletResponse response, HttpServletRequest request){
        try{
            Cookie refreshCookie = Arrays.stream(request.getCookies())
                    .filter(cookie -> cookie.getName().equals(refreshHeader))
                    .findFirst()
                    .orElse(null);
            if (refreshCookie == null) {
                throw new MemberException(MemberExceptionType.REFRESH_TOKEN_NOT_EXIST);
            }
            refreshCookie.setMaxAge(0);
            refreshCookie.setHttpOnly(true);
            response.addCookie(refreshCookie);
        }catch (Exception e){
            throw new MemberException(MemberExceptionType.REFRESH_TOKEN_NOT_EXIST);
        }
    }

    /**
     * Token의 유효성 검사
     * @param token
     * @return {@link Boolean}
     */
    public boolean isTokenValid(String token) {
        try {
            JWT.require(Algorithm.HMAC512(secretKey)).build().verify(token);
            return true;
        } catch (Exception e) {
            log.error("유효하지 않은 토큰입니다. {}", e.getMessage());
            return false;
        }
    }
}
