package com.hexaware.CozyHavenStay.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.CozyHavenStay.model.Hotel;
import com.hexaware.CozyHavenStay.repository.BookingRepository;
import com.hexaware.CozyHavenStay.repository.HotelRepository;
import com.hexaware.CozyHavenStay.repository.ReviewRepository;
import com.hexaware.CozyHavenStay.repository.RoomRepository;

@RestController
@RequestMapping("/api/owner")
public class HotelOwnerController {
	
	
	@Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ReviewRepository reviewRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

      // Assuming you have an Owner repository to fetch owner data

    @GetMapping("/stats")
    public Map<String, Object> getStats(@RequestParam Long ownerId) {
        Map<String, Object> stats = new HashMap<>();

        // Get all hotels for the owner
        System.out.println("Hello");
        List<Hotel> hotels = hotelRepository.findByOwnerOwnerId(ownerId);
        System.out.println(hotels);

        // Initialize counters for stats
        int totalRooms = 0;
        int totalBookings = 0;
        int activeBookings = 0;
        int reviews = 0;
        int owners = hotels.size(); // Assuming each hotel has one owner (for simplicity)

        // Iterate through the hotels to calculate the total stats
        for (Hotel hotel : hotels) {
            Long hotelId = hotel.getId(); // Assuming Hotel has an 'id' field
            
            // Count total rooms for each hotel
            totalRooms += roomRepository.countByHotelId(hotelId);

            // Count total bookings for each hotel
            totalBookings += bookingRepository.countByHotelId(hotelId);

            // Count active bookings (where depDate > today) for each hotel
            activeBookings += bookingRepository.countByHotelIdAndDepartureDateAfter(hotelId, LocalDate.now());

            // Count reviews for each hotel
            reviews += reviewRepository.countByHotelId(hotelId);
        }

        // Add accumulated stats to the response map
        stats.put("totalRooms", totalRooms);
        stats.put("totalBookings", totalBookings);
        stats.put("activeBookings", activeBookings);
        stats.put("reviews", reviews);
        stats.put("hotels", owners); // The number of hotels corresponds to the number of owners

        return stats;
    }

}
