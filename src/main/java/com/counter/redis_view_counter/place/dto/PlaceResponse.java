package com.counter.redis_view_counter.place.dto;

import com.counter.redis_view_counter.place.entity.Place;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceResponse {
    private Long id;
    private String name;
    private Long viewCount;

    //    DB용
    public static PlaceResponse from(Place place) {
        return PlaceResponse.builder()
                .id(place.getId())
                .name(place.getName())
                .viewCount(place.getViewCount())
                .build();
    }

    //    Redis용: DB에 저장하지 않기 때문에 별도로 만들어 준다.
    public static PlaceResponse from(Place place, Long viewCount) {
        return PlaceResponse.builder()
                .id(place.getId())
                .name(place.getName())
                .viewCount(viewCount)
                .build();
    }
}
