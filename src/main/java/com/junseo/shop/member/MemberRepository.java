package com.junseo.shop.member;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MemberRepository extends JpaRepository<Member, Long>{
    // Member 클래스의 자동으로 채워지는 id값 Long

    // username으로 회원을 조회하는 메서드
    // 해당 아이디의 회원이 없을 경우를 안전하게 처리할 수 있음
    Optional<Member> findByUsername(String username);
}
