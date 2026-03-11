package com.counter.redis_view_counter.place.controller;

import com.counter.redis_view_counter.place.dto.PlaceCreateRequest;
import com.counter.redis_view_counter.place.dto.PlaceResponse;
import com.counter.redis_view_counter.place.service.PlaceService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/places")
public class PlaceController {
    private final PlaceService placeService;

    @PostMapping
    public PlaceResponse createPlace(@RequestBody PlaceCreateRequest request){
        return placeService.createPlace(request);
    }

    @GetMapping("/{id}")
    public PlaceResponse getPlace(@PathVariable Long id){
        return placeService.getPlace(id);
    }

    @PostMapping("/{id}/view")
    public void increaseViewCount(@PathVariable Long id){
        placeService.increaseViewCount(id);
    }
}
