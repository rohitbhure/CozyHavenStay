package com.hexaware.CozyHavenStay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hexaware.CozyHavenStay.model.Booking;

@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {

    // Find bookings by user ID
    List<Booking> findByUserUserId(Long userId);

    // Find bookings by hotel ID
    List<Booking> findByHotelHotelId(Long hotelId);

    // Custom query to find active bookings
    @Query("SELECT b FROM Booking b WHERE b.bookingStatus = 'Booked'")
    List<Booking> findActiveBookings();

    // Native query to count total bookings in a hotel
    @Query(value = "SELECT COUNT(*) FROM booking WHERE hotel_id = :hotelId", nativeQuery = true)
    Long countBookingsByHotel(@Param("hotelId") Long hotelId);
}