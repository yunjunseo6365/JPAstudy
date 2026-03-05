package com.junseo.shop.user;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@Entity
@ToString // Lombok
@Setter
@Getter
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY) // Auto Increment 기능
    private Long id;
    private String name;
    private Integer age;

    public Integer addOneAge(){
        return this.age + 1;
    }

    public void setAge(Integer age) {
        if(age > 0 && age < 100){
            this.age = age;
        }
    }
}
