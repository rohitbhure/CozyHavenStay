package com.hexaware.CozyHavenStay.repository;

import java.time.LocalDate;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.hexaware.CozyHavenStay.model.Booking;
import com.hexaware.CozyHavenStay.model.User;




@Repository
public interface BookingRepository extends JpaRepository<Booking, Long> {
	long countByBookingDate(LocalDate date);
    List<Booking> findByUser(User user);

	List<Booking> findAllById(Long userId);

	List<Booking> findByUser_Id(Long userid);

	public List<Booking> findByHotel_Id(Long hotelid);
	int countByHotelId(Long hotelId);
	int countByHotelIdAndDepartureDateAfter(Long hotelId, LocalDate now);
	
	
}


