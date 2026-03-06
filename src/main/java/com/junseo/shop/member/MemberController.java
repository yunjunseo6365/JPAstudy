package com.junseo.shop.member;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
@RequiredArgsConstructor
public class MemberController {
    // private final MemberRepository memberRepository;
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
}
