package ourfood.example.foodforum.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.LoginUrlAuthenticationEntryPoint;

@Configuration
@RequiredArgsConstructor
@EnableWebSecurity
public class SecurityConfig {

    private final CustomUserDetailsService customUserDetailsService;
    private final CustomAuthenticationFailureHandler failureHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers(
                                "/member/restaurants", "/restaurants/new", "/restaurants/{id}/update"
                        ).hasRole("OWNER") // OWNER 역할이 필요한 경로
                        .requestMatchers("/admin/**")
                        .hasRole("ADMIN") // ADMIN 역할이 필요한 경로
                        .requestMatchers(
                                "/css/**", "/js/**", "/images/**", "/static/**", "/api/**",
                                "/", "/login", "/members/new",
                                "/restaurants/{id}", "/restaurants/search"
                        ).permitAll() // 인증 필요 없는 경로
                        .anyRequest().authenticated() // 나머지 모든 요청은 인증 필요
                )
                .formLogin(form -> form
                        .loginPage("/login") // 로그인 페이지
                        .usernameParameter("memberName")
                        .defaultSuccessUrl("/", false) // 로그인 성공 후 이동할 페이지
                        .failureHandler(failureHandler)
                        .permitAll()
                )
                .logout(logout -> logout
                        .logoutUrl("/logout")
                        .logoutSuccessUrl("/")
                        .permitAll()
                );

        return http.build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(); // 비밀번호 암호화
    }

    // In-Memory 사용자 계정 설정
    @Bean
    public AuthenticationManager authenticationManager(HttpSecurity http, PasswordEncoder passwordEncoder) throws Exception {
        AuthenticationManagerBuilder authenticationManagerBuilder =
                http.getSharedObject(AuthenticationManagerBuilder.class);
        return authenticationManagerBuilder.build();
    }
}
