package com.counter.redis_view_counter.place.service;

import com.counter.redis_view_counter.place.dto.PlaceCreateRequestDto;
import com.counter.redis_view_counter.place.dto.PlaceResponseDto;
import com.counter.redis_view_counter.place.entity.Place;
import com.counter.redis_view_counter.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    @Transactional
    public PlaceResponseDto createPlace(PlaceCreateRequestDto request) {
        Place place = Place.builder()
                .name(request.getName())
                .viewCount(0L)
                .build();
        Place savePlace = placeRepository.save(place);
        return PlaceResponseDto.from(savePlace);
    }

    @Transactional(readOnly = true)
    public PlaceResponseDto getPlace(Long id) {
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 장소가 없습니다. id = " + id));
        return PlaceResponseDto.from(place);
    }

    @Transactional(readOnly = true)
    public void increaseViewCount(Long id) {
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 장소가 없습니다. id = " + id));
        place.increaseViewCount();
    }
}
