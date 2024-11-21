package com.hexaware.CozyHavenStay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.hexaware.CozyHavenStay.model.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room, Long> {

    // Find rooms by hotel ID
    List<Room> findByHotelHotelId(Long hotelId);

    // Custom query to find available rooms in a hotel
    @Query("SELECT r FROM Room r WHERE r.hotel.hotelId = :hotelId AND r.availabilityStatus = true")
    List<Room> findAvailableRoomsByHotel(@Param("hotelId") Long hotelId);

    // Native query to find the total number of rooms in a hotel
    @Query(value = "SELECT COUNT(*) FROM room WHERE hotel_id = :hotelId", nativeQuery = true)
    Long countRoomsByHotel(@Param("hotelId") Long hotelId);
}