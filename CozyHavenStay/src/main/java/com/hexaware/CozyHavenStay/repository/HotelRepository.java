package com.hexaware.CozyHavenStay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hexaware.CozyHavenStay.model.Hotel;

@Repository
public interface HotelRepository extends JpaRepository<Hotel, Long> {

    // Find hotels by location
    List<Hotel> findByLocation(String location);

    // Custom query to find hotels with a minimum rating
    @Query("SELECT h FROM Hotel h WHERE h.rating >= :minRating")
    List<Hotel> findHotelsWithMinRating(@Param("minRating") Double minRating);

    // Native query to find the average rating of hotels
    @Query(value = "SELECT AVG(rating) FROM hotel", nativeQuery = true)
    Double findAverageRating();
}