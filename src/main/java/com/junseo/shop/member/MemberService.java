package com.junseo.shop.member;

import com.junseo.shop.SecurityConfig;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ModelAttribute;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MemberService {
    private final MemberRepository memberRepository;
    private final PasswordEncoder passwordEncoder;

    public void registMember(Member member){
        // 1. 아이디 중복 검사
        // 위에서 만든 findByUsername 메서드를 사용
        Optional<Member> existingMember = memberRepository.findByUsername(member.getUsername());
        if (existingMember.isPresent()) { // 조회된 회원이 존재한다면
            throw new IllegalArgumentException("이미 존재하는 아이디입니다.");
        }
        // 2. 아이디, 비밀번호 길이 검사
        if (member.getUsername().length() < 8 || member.getPassword().length() < 8) {
            throw new IllegalArgumentException("아이디 또는 비밀번호는 8자 이상이어야 합니다.");
        }

        // 비밀번호 암호화
        // String encodePassword = new BCryptPasswordEncoder().encode(member.getPassword()); 써도 되지만 강한 결합임
        String encodePassword = passwordEncoder.encode(member.getPassword());
        member.setPassword(encodePassword);

        System.out.println("--- 회원가입 요청 데이터 ---");
        System.out.println(member); // member.toString()이 자동으로 호출

        memberRepository.save(member);
        System.out.println("회원가입 성공");
    }
}
