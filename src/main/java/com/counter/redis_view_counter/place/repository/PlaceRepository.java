package com.counter.redis_view_counter.place.repository;

import com.counter.redis_view_counter.place.entity.Place;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


public interface PlaceRepository extends JpaRepository<Place, Long> {

    @Modifying
    @Transactional
    @Query("update Place p set p.viewCount = p.viewCount + :count where p.id = :placeId")
    void increaseViewCount(@Param("placeId") Long placeId, @Param("count") Long count);
}
