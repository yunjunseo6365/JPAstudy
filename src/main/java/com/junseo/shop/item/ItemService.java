package com.junseo.shop.item;

import com.junseo.shop.member.Member;
import com.junseo.shop.member.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ItemService {

    private final ItemRepository itemRepository;
    private final MemberRepository memberRepository;

    public List<Item> viewList(){
        return itemRepository.findAll();
    }

    public void saveItem(String title, Integer price, String username) {
        // 간단한 에러 처리(터미널 에러 확인용)
        if(title == null || title.length() > 100){
            throw new IllegalArgumentException("제목이 너무 길거나 비어있습니다.");
        }
        if(price == null || price < 0){
            throw new IllegalArgumentException("가격은 공백과 음수가 들어갈 수 없습니다.");
        } else if(price > 10000000){
            throw new IllegalArgumentException("적용할 수 없는 가격입니다.");
        }

        // username으로 작성자(Member) 정보를 DB에서 조회, 없으면 에러
        Member member = memberRepository.findByUsername(username)
                .orElseThrow(() -> new IllegalArgumentException("해당 유저를 찾을 수 없습니다."));

        Item item = new Item();
        item.setTitle(title);
        item.setPrice(price);
        item.setMember(member); // Item 객체에 작성자 정보 설정
        itemRepository.save(item);
    }

    public Optional<Item> itemId(Long id){
        return itemRepository.findById(id);
    }

    public void updateItem(Long id, String title, Integer price){
        // 간단한 에러 처리(터미널 에러 확인용)
        if(title == null || title.length() > 100){
            throw new IllegalArgumentException("제목이 너무 길거나 비어있습니다.");
        }
        if(price == null || price < 0){
            throw new IllegalArgumentException("가격은 공백과 음수가 들어갈 수 없습니다.");
        } else if(price > 10000000){
            throw new IllegalArgumentException("적용할 수 없는 가격입니다.");
        }

        Item item = new Item();
        item.setId(id);
        item.setTitle(title);
        item.setPrice(price);
        itemRepository.save(item);
        /* JPA의 경우 Primary Key값에 해당하는 데이터(id)를 강제로 기입해서
        * 테이블에 이미 존재하는 id를 기입하여 .save() 하면 덮어쓰기(수정)  */
    }

    // 기존의 List<Item> 반환하던 메서드를 Page<Item> 반환으로 변경
    // page: 조회할 페이지 번호 (0부터 시작)
    public Page<Item> getList(int page) {
        // 한 페이지에 5개씩 보여주고, id 기준 내림차순 정렬 (최신순)
        PageRequest pageable = PageRequest.of(page, 5, Sort.by(Sort.Direction.DESC, "id"));
        return itemRepository.findAll(pageable);
    }

}
