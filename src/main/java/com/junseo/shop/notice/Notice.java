package com.junseo.shop.notice;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import java.time.LocalDateTime;


@ToString // Lombok
@Entity
@Getter
@EntityListeners(AuditingEntityListener.class) // 날짜 자동 생성을 위해 필요!
public class Notice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String noticeTitle;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime date;
}
