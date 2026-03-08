package com.junseo.shop;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        http.csrf((csrf) -> csrf.disable()); // csrf 해제
        http.authorizeHttpRequests((authorize) -> authorize
                // 1. "/login", "/register", "/member" URL은 익명 사용자만 접근 가능
                .requestMatchers("/login", "/register", "/member").anonymous()

                // 2. 나머지 모든 요청은 일단 모두에게 허용 (필요에 따라 수정)
                .anyRequest().permitAll()
        );

        // login용 폼
        http.formLogin((formLogin) -> formLogin
                        .loginPage("/login")
                        .defaultSuccessUrl("/list") // 로그인 성공시 이동하고자 하는 곳
                        .permitAll() // 로그인 페이지는 모두에게 허용
                     // .failureUrl("/fail") -> 로그인 실패시 이동하고자 하는 곳. 따로 지정 안하면 로그인 실패시 기본적으로 /login?error로 이동함
        );
        // logout하는법
        // 해당 url로 get요청시 로그아웃
        http.logout(logout -> logout.logoutUrl("/logout")
                .logoutSuccessUrl("/login") // 로그아웃 성공시 이동하고자 하는 곳
                .invalidateHttpSession(true) // 세션 무효화(로그아웃 시)
                .deleteCookies("JSESSIONID") // 쿠키 삭제(선택사항)
        );

        return http.build();
    }
    
    // 비밀번호 암호화 함수(dependency Injection) -> Bean으로 만들어줌(Spring이 뽑은 Object)
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }


}
/* () -> { } : 화살표 문법, 함수 만드는 법 , lambda expression, arrow operator */

/* FilterChain : 모든 유저의 요청과 서버의 응답 사이에 자동으로 실행해주고 싶은 코드 담는 곳 */
