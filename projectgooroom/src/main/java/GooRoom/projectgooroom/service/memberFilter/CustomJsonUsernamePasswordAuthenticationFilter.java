package GooRoom.projectgooroom.service.memberFilter;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.StreamUtils;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Map;

/**
 * 이메일을 사용한 로그인 필터
 * Json{
 *     "email" :    ,
 *     "password"   :
 * }
 */
public class CustomJsonUsernamePasswordAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    /**
     * username대신 email사용
     */
    public static final String SPRING_SECURITY_FORM_USERNAME_KEY = "email";
    public static final String SPRING_SECURITY_FORM_PASSWORD_KEY = "password";
    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER = new AntPathRequestMatcher("/login",
            "POST");
    private static final String DEFAULT_LOGIN_PATH_REQUEST_MATCHER = "/login/email";
    private String usernameParameter = SPRING_SECURITY_FORM_USERNAME_KEY;
    private String passwordParameter = SPRING_SECURITY_FORM_PASSWORD_KEY;
    private boolean postOnly = true;
    private ObjectMapper objectMapper = new ObjectMapper();

    public CustomJsonUsernamePasswordAuthenticationFilter(){
        super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER);
    }
    public CustomJsonUsernamePasswordAuthenticationFilter(ObjectMapper objectMapper) {
        super(DEFAULT_LOGIN_PATH_REQUEST_MATCHER); // 위에서 설정한 "login" + POST로 온 요청을 처리하기 위해 설정
        this.objectMapper = objectMapper;
    }

    /**
     * Json 형식에서 이메일과 비밀번호 추출하여 토큰 발행
     * @param request from which to extract parameters and perform the authentication
     * @param response the response, which may be needed if the implementation has to do a
     * redirect as part of a multi-stage authentication process (such as OIDC).
     * @return
     * @throws AuthenticationException
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException, IOException, ServletException {
        if(request.getContentType() == null || !request.getContentType().equals("application/json")  ) {
            throw new AuthenticationServiceException("Authentication Content-Type not supported: " + request.getContentType());
        }
        String messageBody = StreamUtils.copyToString(request.getInputStream(), StandardCharsets.UTF_8);

        Map<String, String> usernamePasswordMap = objectMapper.readValue(messageBody, Map.class);

        String email = usernamePasswordMap.get(SPRING_SECURITY_FORM_USERNAME_KEY);
        String password = usernamePasswordMap.get(SPRING_SECURITY_FORM_PASSWORD_KEY);

        UsernamePasswordAuthenticationToken authRequest = new UsernamePasswordAuthenticationToken(email, password);//principal 과 credentials 전달

        return this.getAuthenticationManager().authenticate(authRequest);
    }
}
