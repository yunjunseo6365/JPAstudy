package com.junseo.shop;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@Controller
@RequiredArgsConstructor // Lombok문법
public class ItemController {
    
    // step 2. 원하는 클래스에 Repository 등록 및 @RequiredArgsConstructor추가 혹은 생성자에 선언
    private final ItemRepository itemRepository;
    private final ItemService itemService;


    // lombok 안쓰면 이렇게 써야함. Dependency Injection
    //    @Autowired
    //    public ItemController(ItemRepository itemRepository) {
    //        this.itemRepository = itemRepository;
    //        this.itemService = itemService;
    //    }

    @GetMapping("/list")
    String list(Model model) {
        List<Item> result = itemService.viewList(); // 테이블 모든 데이터 가져옴, List자료형으로 가져옴(Object 형태임)
        model.addAttribute("items", result);
        return "list.html";
    }

    @GetMapping("/write")
    String write(Model model) {
        return "write.html";
    }


//    @PostMapping("/add")
//    String addPost(@RequestParam(name="title") String title,
//                   @RequestParam String price) {
//        // ajax는 @RequestBody 써야 출력가능
//
//
//        Item item = new Item();
//        item.setTitle(title);
//        item.setPrice(Integer.parseInt(price));
//
//        // itemRepository.save();
//        return "redirect:/list"; // 특정페이지로 돌아가게 만드는법(redirect)
//    }


//    @PostMapping("/add")
//    String writePost(@ModelAttribute Item item) { // @ModelAttribute는 input 데이터들을 바로 object로 변환해줌
//        itemRepository.save(item);
//
//        System.out.println(item.toString());
//        return "redirect:/list"; // 특정페이지로 돌아가게 만드는법(redirect)
//    }

    @PostMapping("/add")
    String addPost(String title, Integer price){

//        Item item = new Item();
//        item.setTitle(title);
//        item.setPrice(price);
//        itemRepository.save(item); service 레이어로 뺌
        itemService.saveItem(title, price);
        return "redirect:/list";
    }

//    @GetMapping("/detail/{id}") // url파라미터
//    ResponseEntity<String> detail(@PathVariable Long id, Model model) {
//        try{
//            // Optional : Optional은 null일 수도 있고 아닐 수도 있다는 타입
//            Optional<Item> result = itemRepository.findById(id);
//            // result안에 값들이 비어있을 수 있으므로 if문으로 한번 걸러주는것이 관례임
//            if(result.isPresent()){
//                throw new Exception("이런 저런 에러임");
//                model.addAttribute("data", result.get());
//                return "detail.html";
//            } else{
//                return "redirect:/list";
//            }
//        } catch (Exception e){
//            System.out.println(e.getMessage());
//            // 에러기록할때 Logging 라이브러리 씀
//            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("니잘못임"); // ajax 통신에선 redirect 불가능 // 유저잘못 4xx, 서버잘못 5xx, 정상 200 (관습임)
//        }
//    }
    @GetMapping("/detail/{id}") // url파라미터
    String detail(@PathVariable Long id, Model model) {
            // Optional : Optional은 null일 수도 있고 아닐 수도 있다는 타입
            Optional<Item> result = itemService.itemId(id);
            // result안에 값들이 비어있을 수 있으므로 if문으로 한번 걸러주는것이 관례임
            if (result.isPresent()) {
                model.addAttribute("data", result.get());
                return "detail.html";
            } else {
                return "redirect:/list";
            }
        }
}
