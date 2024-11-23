package com.hexaware.CozyHavenStay.service;

import java.util.List;

import com.hexaware.CozyHavenStay.dto.BookingRequestDTO;
import com.hexaware.CozyHavenStay.model.Booking;

public interface BookingService {
    Booking createBooking(BookingRequestDTO bookingRequestDTO);
    Booking getBookingById(Long bookingId);
    List<Booking> getAllBookings();
    List<Booking> getBookingsByUser(Long userId);
    List<Booking> getBookingsByHotel(Long hotelId);
    Booking updateBooking(Long bookingId, BookingRequestDTO bookingRequestDTO); // New Method
    void cancelBooking(Long bookingId);
}