package com.junseo.shop.item;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@ToString // Lombok
@Getter
@Setter
public class Item {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment 기능
    private Long id;

    // @Column(nullable = false, columnDefinition = "TEXT", length = 1000) // not null, 최대길이 등 설정가능
    private String title;
    private Integer price;  // 컬럼용 변수에는 int말고 integer 강요함
    

}
