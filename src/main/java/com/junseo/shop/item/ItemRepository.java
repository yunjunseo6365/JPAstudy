package com.junseo.shop.item;

import org.springframework.data.jpa.repository.JpaRepository;

// step 1. Repository 만들기 JpaRepository<db만드는클래스, id타입>
public interface ItemRepository extends JpaRepository<Item, Long> {
    // 인터페이스만 만들어두면 구현체는 Spring이 알아서 생성
}
