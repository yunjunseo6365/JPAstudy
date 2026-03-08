package com.junseo.shop.member;

import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.net.Authenticator;

@Controller
@RequiredArgsConstructor
public class MemberController {
    private final MemberRepository memberRepository;
    private final MemberService memberService;

    // 회원가입 페이지
    @GetMapping("/register")
    public String register() {
        return "register.html";
    }

    // 회원가입 정보 DB전송
    @PostMapping("/member")
    public String registerMember(@ModelAttribute Member member){
        try {
            memberService.registMember(member);
            return "redirect:/list"; // 성공 시 목록 페이지로
        } catch (IllegalArgumentException e) {
            // MemberService에서 예외가 발생하면 이 코드가 실행됨
            System.out.println(e.getMessage()); // 서버 로그에 에러 메시지 출력
            return "register.html"; // 다시 회원가입 페이지로 돌아감
        }
    }

    @GetMapping("/login")
    public String login() {
        return "login.html";
    }

    @GetMapping("/my-page")
    public String mypage(Authentication auth) {
        MyUserDetailsService.CustomUser result = (MyUserDetailsService.CustomUser) auth.getPrincipal();

        System.out.println(auth);
//        System.out.println(auth.getName());
//        System.out.println(auth.isAuthenticated()); // 현재 로그인 여부
//        System.out.println(auth.getAuthorities().contains(
//                new SimpleGrantedAuthority("일반유저")
//        ));
        System.out.println(result.displayName);

        if(auth.isAuthenticated()==true){
            return "mypage.html";
        } else {
            return "redirect:/login";
        }
    }
    // DTO : Object를 변환해서 전송하려면 Map또는 DTO클래스 쓰는데
    @GetMapping("/user/1")
    @ResponseBody
    public MemberDto getUser() {
        var a = memberRepository.findById(1L);
        var result = a.get();
        var data = new MemberDto(result.getUsername(), result.getDisplayName());
        return data;
    }

    // DTO 쓰면 장점 -> 보내는 데이터의 타입 체크가 쉬움, 재사용이 쉬움
    class MemberDto {
        public String username;
        public String displayName;
        public Long id;
        MemberDto(String username, String displayName){
            this.username = username;
            this.displayName = displayName;
        }
        MemberDto(String username, String displayName, Long id){
            this.username = username;
            this.displayName = displayName;
            this.id = id;
        }
    }

}
