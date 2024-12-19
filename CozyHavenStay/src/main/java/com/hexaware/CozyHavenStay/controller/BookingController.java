package com.hexaware.CozyHavenStay.controller;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.CozyHavenStay.dto.BookingDTO;
import com.hexaware.CozyHavenStay.exception.BookingNotFoundException;
import com.hexaware.CozyHavenStay.model.Booking;
import com.hexaware.CozyHavenStay.model.Hotel;
import com.hexaware.CozyHavenStay.model.Room;
import com.hexaware.CozyHavenStay.model.User;
import com.hexaware.CozyHavenStay.repository.BookingRepository;
import com.hexaware.CozyHavenStay.repository.HotelRepository;
import com.hexaware.CozyHavenStay.repository.RoomRepository;
import com.hexaware.CozyHavenStay.repository.UserRepository;
import com.hexaware.CozyHavenStay.service.BookingServiceImpl;

@RestController
@RequestMapping("/api/bookings")
public class BookingController {

    @Autowired
    private BookingServiceImpl bookingService;
    
    @Autowired
    private HotelRepository hotelRepository;
    
    @Autowired
    private UserRepository userRepository;
    
    @Autowired
    private RoomRepository roomRepository;
    
    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private ModelMapper mp;

    @PostMapping("/createbooking")
    public ResponseEntity<BookingDTO> createBooking(@RequestBody BookingDTO bookingdto) {
    	System.out.println("Received Booking: " + bookingdto);
    	Hotel hotel = hotelRepository.findById(bookingdto.getHotelId())
                .orElseThrow(() -> new RuntimeException("Hotel not found"));
        Room room = roomRepository.findById(bookingdto.getRoomId())
                .orElseThrow(() -> new RuntimeException("Room not found"));
        User user = userRepository.findById(bookingdto.getUserId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        Booking booking = mp.map(bookingdto, Booking.class);
        booking.setHotel(hotel);
        booking.setRoom(room);
        booking.setUser(user);
        booking.setBookingDate(LocalDate.now());
        System.out.println("Received Booking: " + booking);
        Booking savedBooking = bookingService.createBooking(booking);
        BookingDTO response = mp.map(savedBooking, BookingDTO.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
        
    }

    @GetMapping("getbookingbyid/{id}")
    public ResponseEntity<BookingDTO> getBooking(@PathVariable Long id) throws BookingNotFoundException {
        Booking booking = bookingService.getBookingById(id);
        if (booking!=null) {
            BookingDTO bookingDTO = mp.map(booking, BookingDTO.class);
            return new ResponseEntity<>(bookingDTO, HttpStatus.OK);
        } else {
            throw new BookingNotFoundException("Booking not found");
        }
    }

    @PutMapping("/updatebookingbyid/{id}")
    public ResponseEntity<BookingDTO> updateBooking(@PathVariable Long id, @RequestBody BookingDTO bookingDto) {
        Booking booking = mp.map(bookingDto, Booking.class);
        Booking updatedBooking = bookingService.updateBooking(id, booking);
        if(updatedBooking!=null) {
        BookingDTO bookingDTO = mp.map(updatedBooking, BookingDTO.class);
        return new ResponseEntity<>(bookingDTO, HttpStatus.OK);
        }
        else {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
    }

    @DeleteMapping("/deletebookingbyid/{id}")
    public ResponseEntity<String> deleteBooking(@PathVariable Long id) throws BookingNotFoundException {
        if(bookingService.deleteBooking(id).equals("Deleted")) {
        	return new ResponseEntity<String>("Deleted",HttpStatus.OK);
        }
        else
        throw new BookingNotFoundException("Booking doesn not exist with entered booking id");
    }

    @GetMapping("/allbookings")
    public ResponseEntity<List<BookingDTO>> getAllBookings() {
        List<Booking> bookings = bookingService.getAllBookings();
        if(bookings.isEmpty()) {
        return new ResponseEntity<>( HttpStatus.NO_CONTENT);
        }
        else {
        	List<BookingDTO> li = bookings.stream().map((temp)->mp.map(temp, BookingDTO.class)).toList();
        	return new ResponseEntity<List<BookingDTO>>(li,HttpStatus.OK);
        }
    }
    
    @GetMapping("getbookingbyuserid/{userid}")
    public ResponseEntity<List<BookingDTO>> getBookinguser(@PathVariable Long userid) throws BookingNotFoundException {
        List<Booking> booking = bookingService.getBookingByUserId(userid);
        if(booking.isEmpty()) {
            return new ResponseEntity<>( HttpStatus.NO_CONTENT);
            }
            else {
            	List<BookingDTO> li = booking.stream().map((temp)->mp.map(temp, BookingDTO.class)).toList();
            	return new ResponseEntity<List<BookingDTO>>(li,HttpStatus.OK);
            }
    }
    
	
	  @GetMapping("getbookingbyhotelid/{hotelid}")
	  public ResponseEntity<List<BookingDTO>> getBookinghotel(@PathVariable Long hotelid) throws BookingNotFoundException 
	  { 
		  List<Booking> booking =bookingService.getBookingByHotelId(hotelid); 
		  if(booking.isEmpty()) 
		  { 
			  return new ResponseEntity<>( HttpStatus.NO_CONTENT); 
		  } 
		  else 
		  { 
			  List<BookingDTO> li = booking.stream().map((temp)->mp.map(temp, BookingDTO.class)).toList();
			  return new ResponseEntity<List<BookingDTO>>(li,HttpStatus.OK); 
		  }
		  
	  }
	  
	  @GetMapping("/stats")
	    public Map<String, Object> getStats() {
	        Map<String, Object> stats = new HashMap<>();

	        // Count Users
	        stats.put("users", userRepository.count());

	        // Count Hotels
	        stats.put("hotels", hotelRepository.count());

	        // Count Bookings
	        stats.put("bookings", bookingRepository.count());

	        // Count Today's New Bookings
	        stats.put("newBookingsToday", bookingRepository.countByBookingDate(LocalDate.now()));

	        return stats;
	    }
	 
}
