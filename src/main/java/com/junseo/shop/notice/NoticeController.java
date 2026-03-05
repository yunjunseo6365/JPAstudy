package com.junseo.shop.notice;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
@RequiredArgsConstructor
public class NoticeController {

    private final NoticeRepository noticeRepository;

    @GetMapping("/notice")
    String notice(Model model) {
        List<Notice> result = noticeRepository.findAll(); // 테이블 모든 데이터 가져옴, List자료형으로 가져옴(Object 형태임)
        model.addAttribute("notices", result);
        System.out.println(result);
        return "notice.html";
    }
}
