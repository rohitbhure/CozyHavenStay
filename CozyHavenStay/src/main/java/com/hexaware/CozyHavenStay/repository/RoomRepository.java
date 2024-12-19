package com.hexaware.CozyHavenStay.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.CozyHavenStay.model.Room;


@Repository
public interface RoomRepository extends JpaRepository<Room, Long>{
	

	List<Room> findByHotel_Id(Long hotelId);

	int countByHotelId(Long hotelId);
}
