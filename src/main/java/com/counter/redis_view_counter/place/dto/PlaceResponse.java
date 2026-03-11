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

    public static PlaceResponse from(Place place){
        return PlaceResponse.builder()
                .id(place.getId())
                .name(place.getName())
                .viewCount(place.getViewCount())
                .build();
    }
}
