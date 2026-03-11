package com.counter.redis_view_counter.place.repository;

import com.counter.redis_view_counter.place.entity.Place;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlaceRepository extends JpaRepository<Place, Long> {

}
