package com.counter.redis_view_counter.place.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@AllArgsConstructor
@Builder
public class Place {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    @Column(nullable = false)
    private Long viewCount;

//    Redis를 사용하면 일단 사용하지 않는 메서드
    public void increaseViewCount(){
        this.viewCount += 1;
    }
}
