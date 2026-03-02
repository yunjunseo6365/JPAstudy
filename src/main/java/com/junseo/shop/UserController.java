package com.junseo.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class UserController {
    private final UserRepository userRepository;

    @GetMapping("/user")
    String list(Model model) {
        User us = new User();
        us.setAge(10);
        us.addOneAge();
        System.out.println("초기 설정 10 후 +1 메소드 실행 : " + us.addOneAge());

        us.setAge(12);
        System.out.println("나이 12로 설정 " + us.getAge());

        us.setAge(-10);
        System.out.println("나이 -10으로 설정 " + us.getAge());

        us.setAge(101);
        System.out.println("나이 101으로 설정 " + us.getAge());
        return "user.html";
    }
}
