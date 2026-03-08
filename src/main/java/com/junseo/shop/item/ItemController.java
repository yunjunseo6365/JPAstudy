package com.junseo.shop.item;

import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Slice;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
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
    public String list(Model model) {
//        List<Item> result = itemService.viewList(); // 테이블 모든 데이터 가져옴, List자료형으로 가져옴(Object 형태임)
//
//        model.addAttribute("items", result);
//        return "list.html";
        return "redirect:/list/page/1";
    }

    @GetMapping("/write")
    public String write(Model model) {
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
    public String addPost(@ModelAttribute Item item, Authentication auth){
        // 현재 로그인한 사용자의 정보 가져오기
        String username = auth.getName();
//        Item item = new Item();
//        item.setTitle(title);
//        item.setPrice(price);
//        itemRepository.save(item); service 레이어로 뺌
        itemService.saveItem(item.getTitle(), item.getPrice(), username); // 작성자 추가
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
    public String detail(@PathVariable Long id, Model model) {
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

    // 수정폼으로 이동 showUpdateForm
    @GetMapping("/edit/{id}")
    public String showUpdateForm(@PathVariable Long id, Model model, Authentication auth) {
        // 1. 로그인 여부 확인
        if (auth == null || !auth.isAuthenticated()) {
            return "redirect:/login"; // 로그인하지 않았다면 로그인 페이지로
        }
        // 2. 아이템 조회
        Optional<Item> result = itemService.itemId(id);

        // 3. 아이템이 존재하지 않는 경우
        if (result.isEmpty()) {
            // 존재하지 않는 아이템에 접근 시 목록 페이지로 보냅니다.
            return "redirect:/list?error=not_found";
        }

        // 4. 아이템이 존재하는 경우, 객체를 꺼내서 작성자 확인
        Item item = result.get();

        // 5. 작성자 본인 확인
        // 아이템에 작성자 정보가 없거나, 현재 로그인한 사용자와 아이템 작성자가 다른 경우
        if (item.getMember() == null || !item.getMember().getUsername().equals(auth.getName())) {
            // 권한이 없는 경우 목록 페이지로 보냅니다.
            return "redirect:/list?error=permission_denied";
        }

        // 6. 모든 검사를 통과한 경우, 모델에 데이터를 담아 수정 페이지로 이동
        model.addAttribute("item", item);
        return "edit.html";
    }

    @PostMapping("/edit")
    public String updateItem(Long id, String title, Integer price){ //@RequestParam은 생략가능
        itemService.updateItem(id, title, price);
        return "redirect:/list";
    }

//    @PostMapping("/test1")
//    public String test1(@RequestBody Map<String, Object> body){ //@RequestParam은 생략가능
//        System.out.println(body.get("name"));
//        return "redirect:/list";
//    }

    @DeleteMapping("/item")
    public ResponseEntity<String> deleteItem(@RequestParam Long id){
         itemRepository.deleteById(id);
        return ResponseEntity.status(200).body("삭제완료");
    }

    // BCryptPasswordEncoder 출력해보기!
    @GetMapping("/test2")
    public String bcryptPassword(){
        var result = new BCryptPasswordEncoder().encode("1234aaa");
        System.out.println(result); // salt부분과 hashing 부분 합쳐져서 만들어짐
        return "redirect:/list";
    }

    @GetMapping("/list/page/{abc}")
    public String getListPage(Model model, @PathVariable Integer abc) {
        System.out.println(abc);
        Page<Item> result = itemRepository.findPageBy(PageRequest.of(abc-1,3));
        // result.getTotalPages(); // 전체 페이지 개수
        model.addAttribute("items", result);
        return "list.html";
    }
}
