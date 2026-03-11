package com.counter.redis_view_counter.place.service;

import com.counter.redis_view_counter.place.dto.PlaceCreateRequest;
import com.counter.redis_view_counter.place.dto.PlaceResponse;
import com.counter.redis_view_counter.place.entity.Place;
import com.counter.redis_view_counter.place.repository.PlaceRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class PlaceService {
    private final PlaceRepository placeRepository;

    @Transactional
    public PlaceResponse createPlace(PlaceCreateRequest request){
        Place place = Place.builder()
                .name(request.getName())
                .viewCount(0L)
                .build();
        Place savePlace = placeRepository.save(place);
        return PlaceResponse.from(savePlace);
    }

    @Transactional(readOnly = true)
    public PlaceResponse getPlace(Long id){
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 장소가 없습니다. id = " + id));
        return PlaceResponse.from(place);
    }

    @Transactional
    public void increaseViewCount(Long id){
        Place place = placeRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("해당 장소가 없습니다. id = " + id));
        place.increaseViewCount();
    }
}
