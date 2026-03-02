package com.junseo.shop;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;


@Controller
public class BasicController {
    @GetMapping("/")
    @ResponseBody
    String hello(){
        return "안녕하세요";
    }

    @GetMapping("/about")
    @ResponseBody
    String about(){
        return "응애~ 사이트에요~";
    }

    @GetMapping("/mypage")
    @ResponseBody
    String mypage(){
        return "마이페이지에요";
    }
    
    // html파일 보내는법(@ResponseBody는 빼야함)
    @GetMapping("/test")
    String test(){
        return "index.html"; // 기본경로가 static 폴더
    }
    // 웹서버와 웹페이지 만들기(Controller)
    @GetMapping("/date")
    @ResponseBody
    public String getTodayDateTime() {
//        // 현재 날짜와 시간 가져오기
//        LocalDateTime now = LocalDateTime.now();
//
//        // 보기 좋게 포맷팅 (예: 2026-03-01 12:22:15)
//        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
//        String formattedNow = now.format(formatter);
//
//        return "현재 날짜와 시간은: " + formattedNow + " 입니다.";

        // 코딩애플 답
        return ZonedDateTime.now().toString();
    }
    
}
