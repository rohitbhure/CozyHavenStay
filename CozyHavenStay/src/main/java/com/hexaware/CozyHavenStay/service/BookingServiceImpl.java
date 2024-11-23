package com.hexaware.CozyHavenStay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.CozyHavenStay.dto.BookingRequestDTO;
import com.hexaware.CozyHavenStay.exception.ResourceNotFoundException;
import com.hexaware.CozyHavenStay.mapper.BookingMapper;
import com.hexaware.CozyHavenStay.model.Booking;
import com.hexaware.CozyHavenStay.model.Hotel;
import com.hexaware.CozyHavenStay.model.Room;
import com.hexaware.CozyHavenStay.model.User;
import com.hexaware.CozyHavenStay.repository.BookingRepository;
import com.hexaware.CozyHavenStay.repository.HotelRepository;
import com.hexaware.CozyHavenStay.repository.RoomRepository;
import com.hexaware.CozyHavenStay.repository.UserRepository;

@Service
public class BookingServiceImpl implements BookingService {

    @Autowired
    private BookingRepository bookingRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private BookingMapper bookingMapper;

    @Override
    public Booking createBooking(BookingRequestDTO bookingRequestDTO) {
        Booking booking = bookingMapper.toEntity(bookingRequestDTO);
        return bookingRepository.save(booking);
    }

    @Override
    public Booking getBookingById(Long bookingId) {
        return bookingRepository.findById(bookingId)
                .orElseThrow(() -> new ResourceNotFoundException("Booking not found with ID: " + bookingId));
    }

    @Override
    public List<Booking> getAllBookings() {
        return bookingRepository.findAll();
    }

    @Override
    public List<Booking> getBookingsByUser(Long userId) {
        return bookingRepository.findByUserUserId(userId);
    }

    @Override
    public List<Booking> getBookingsByHotel(Long hotelId) {
        return bookingRepository.findByHotelHotelId(hotelId);
    }

    @Override
    public Booking updateBooking(Long bookingId, BookingRequestDTO bookingRequestDTO) {
        // Fetch the existing booking
        Booking existingBooking = getBookingById(bookingId);

        // Update fields from DTO
        if (bookingRequestDTO.getUserId() != null) {
            User user = userRepository.findById(bookingRequestDTO.getUserId())
                    .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + bookingRequestDTO.getUserId()));
            existingBooking.setUser(user);
        }

        if (bookingRequestDTO.getHotelId() != null) {
            Hotel hotel = hotelRepository.findById(bookingRequestDTO.getHotelId())
                    .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + bookingRequestDTO.getHotelId()));
            existingBooking.setHotel(hotel);
        }

        if (bookingRequestDTO.getRoomId() != null) {
            Room room = roomRepository.findById(bookingRequestDTO.getRoomId())
                    .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + bookingRequestDTO.getRoomId()));
            existingBooking.setRoom(room);
        }

        if (bookingRequestDTO.getCheckInDate() != null) {
            existingBooking.setCheckInDate(bookingRequestDTO.getCheckInDate());
        }

        if (bookingRequestDTO.getCheckOutDate() != null) {
            existingBooking.setCheckOutDate(bookingRequestDTO.getCheckOutDate());
        }

        if (bookingRequestDTO.getBookingStatus() != null) {
            existingBooking.setBookingStatus(bookingRequestDTO.getBookingStatus());
        }

        // Save updated booking
        return bookingRepository.save(existingBooking);
    }

    @Override
    public void cancelBooking(Long bookingId) {
        Booking booking = getBookingById(bookingId);
        booking.setBookingStatus("Cancelled");
        bookingRepository.save(booking);
    }
}