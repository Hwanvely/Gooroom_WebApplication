package GooRoom.projectgooroom.config;

import GooRoom.projectgooroom.jwt.JwtService;
import GooRoom.projectgooroom.repository.MemberRepository;
import GooRoom.projectgooroom.service.member.EmailMemberService;
import GooRoom.projectgooroom.service.memberFilter.CustomJsonUsernamePasswordAuthenticationFilter;
import GooRoom.projectgooroom.service.memberHandler.LoginFailureHandler;
import GooRoom.projectgooroom.service.memberHandler.LoginSuccessHandler;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.factory.PasswordEncoderFactories;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {

    private final EmailMemberService emailMemberService;
    private final MemberRepository memberRepository;
    private final JwtService jwtService;
    private final ObjectMapper objectMapper;
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http
                .formLogin().disable() // FormLogin 사용 X
                .httpBasic().disable() // httpBasic 사용 X
                .csrf().disable() // csrf 보안 사용 X
                .headers().frameOptions().disable()
                .and()

                // 세션 사용하지 않으므로 STATELESS로 설정
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)

                .and()

                //== URL별 권한 관리 옵션 ==//
                .authorizeRequests()
                // 아이콘, css, js 관련
                // 기본 페이지, css, image, js 하위 폴더에 있는 자료들은 모두 접근 가능, h2-console에 접근 가능
                .requestMatchers("/","/css/**","/images/**","/js/**","/favicon.ico","/h2-console/**").permitAll()
                .requestMatchers("/users/**").permitAll() //회원가입 접근 가능
                .requestMatchers("/login/**").permitAll()
                .anyRequest().authenticated();

        return http.build();
    }

    /**
     * Password Encoder
     * @return
     */
    @Bean
    public PasswordEncoder passwordEncoder() {
        return PasswordEncoderFactories.createDelegatingPasswordEncoder();
    }

    /**
     * AuthenticationManager 설정 후 등록
     * UserDetailsService는 커스텀 emailMemberService 사용
     */
    @Bean
    public AuthenticationManager authenticationManager() {
        DaoAuthenticationProvider provider = new DaoAuthenticationProvider();
        provider.setPasswordEncoder(passwordEncoder());
        provider.setUserDetailsService(emailMemberService);
        return new ProviderManager(provider);
    }

    /**
     * 로그인 성공 시 LoginSuccessJWTProviderHandler
     */
    @Bean
    public LoginSuccessHandler loginSuccessHandler() {
        return new LoginSuccessHandler(jwtService, memberRepository);
    }

    /**
     * 로그인 실패 시 LoginFailureHandler
     */
    @Bean
    public LoginFailureHandler loginFailureHandler() {
        return new LoginFailureHandler();
    }

    /**
     * CustomJsonUsernamePasswordAuthenticationFilter 커스텀 필터 빈 등록
     * 로그인 성공 유무에 따른 필터 등록
     */
    @Bean
    public CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordAuthenticationFilter() {
        CustomJsonUsernamePasswordAuthenticationFilter customJsonUsernamePasswordLoginFilter
                = new CustomJsonUsernamePasswordAuthenticationFilter(objectMapper);
        customJsonUsernamePasswordLoginFilter.setAuthenticationManager(authenticationManager());
        customJsonUsernamePasswordLoginFilter.setAuthenticationSuccessHandler(loginSuccessHandler());
        customJsonUsernamePasswordLoginFilter.setAuthenticationFailureHandler(loginFailureHandler());
        return customJsonUsernamePasswordLoginFilter;
    }
}
