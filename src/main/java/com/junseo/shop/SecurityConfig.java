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
        http.authorizeHttpRequests((authorize) ->
                authorize.requestMatchers("/**").permitAll() // 전체 페이지 로그인 제한 해제
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
