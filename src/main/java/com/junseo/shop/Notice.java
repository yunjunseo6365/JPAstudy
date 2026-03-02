package com.junseo.shop;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@EnableJpaAuditing
@ToString // Lombok
@Entity
@Getter
public class Notice {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer id;
    public String noticeTitle;

    @CreatedDate
    @Column(updatable = false)
    private LocalDateTime date;

    // Notice 엔티티 내부 LocalDateTime 포맷팅
    public String getFormattedDate() {
        return date.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
    }
}
