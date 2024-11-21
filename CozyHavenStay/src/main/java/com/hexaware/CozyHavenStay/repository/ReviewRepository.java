package com.hexaware.CozyHavenStay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hexaware.CozyHavenStay.model.Review;

@Repository
public interface ReviewRepository extends JpaRepository<Review, Long> {

    // Find reviews by hotel ID
    List<Review> findByHotelHotelId(Long hotelId);

    // Find reviews by user ID
    List<Review> findByUserUserId(Long userId);

    // Custom query to get average rating for a hotel
    @Query("SELECT AVG(r.rating) FROM Review r WHERE r.hotel.hotelId = :hotelId")
    Double findAverageRatingByHotel(@Param("hotelId") Long hotelId);

    // Native query to count reviews for a specific hotel
    @Query(value = "SELECT COUNT(*) FROM review WHERE hotel_id = :hotelId", nativeQuery = true)
    Long countReviewsByHotel(@Param("hotelId") Long hotelId);
}