package com.counter.redis_view_counter.place.dto;

import com.counter.redis_view_counter.place.entity.Place;
import lombok.Builder;
import lombok.Getter;

@Getter
@Builder
public class PlaceResponseDto {
    private Long id;
    private String name;
    private Long viewCount;
    
    public static PlaceResponseDto from(Place place, Long viewCount) {
        return PlaceResponseDto.builder()
                .id(place.getId())
                .name(place.getName())
                .viewCount(viewCount)
                .build();
    }
}
