package com.junseo.shop.member;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MyUserDetailsService implements UserDetailsService { // MyUserDetailsService클래스가 UserDetailsService(interface) 따라하나 검사해주세요
    // interface는 함수 정의만 넣는 class로, 클래스가 특정한 함수를 만들게 강요할때 사용

    private final MemberRepository memberRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // DB에서 username을 가진 유저를 찾아와서
        // return new User(유저아이디, 비번, 권한)
        // 유저가 제출한 비번 == DB에 있던 비번(Spring Security는 DB에 있던 비번 모름)

        Optional<Member> result = memberRepository.findByUsername(username);
        if (result.isEmpty()){
            throw new UsernameNotFoundException("해당 아이디가 존재하지 않음");
        }
        Member user = result.get();
        // var 권한 = new ArrayList<>();
        // 권한.add(new SimpleGrantedAuthority("일반유저")); <- API에서 현재 유저의 권한 출력! 하는 기능임

        List<GrantedAuthority> authorities = new ArrayList<>();
        authorities.add(new SimpleGrantedAuthority("일반유저"));

        var a = new CustomUser(user.getUsername(), user.getPassword(), authorities);
        a.displayName = user.getDisplayName();
        return a;

        //  return new User(아이디, 비밀번호, 권한);
        // return new User(user.getUsername(), user.getPassword(), authorities);
    }

    // 커스텀 유저 생성(displayName을 보고자 커스텀 유저 정보용 만듦)
    class CustomUser extends User {

        public String displayName;

        public CustomUser(
                String username,
                String password,
                Collection<? extends GrantedAuthority> authorities
        ) {
            super(username, password, authorities); // extends로 복사한 클래스의 생성자 호출
        }
    }

}
