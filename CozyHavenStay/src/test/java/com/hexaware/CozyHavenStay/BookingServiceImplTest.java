package com.hexaware.CozyHavenStay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hexaware.CozyHavenStay.model.Booking;
import com.hexaware.CozyHavenStay.repository.BookingRepository;
import com.hexaware.CozyHavenStay.service.BookingServiceImpl;

class BookingServiceImplTest {

    @InjectMocks
    private BookingServiceImpl bookingService;

    @Mock
    private BookingRepository bookingRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testCreateBooking() {
        Booking booking = new Booking();
        booking.setId(1L);

        when(bookingRepository.save(any(Booking.class))).thenReturn(booking);

        Booking result = bookingService.createBooking(booking);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(bookingRepository, times(1)).save(booking);
    }

    @Test
    void testGetBookingById() {
        Booking booking = new Booking();
        booking.setId(1L);

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        Booking result = bookingService.getBookingById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(bookingRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateBooking() {
        Booking existingBooking = new Booking();
        existingBooking.setId(1L);
        existingBooking.setName("Old Name");

        Booking updatedBooking = new Booking();
        updatedBooking.setName("New Name");

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(existingBooking));
        when(bookingRepository.save(any(Booking.class))).thenReturn(existingBooking);

        Booking result = bookingService.updateBooking(1L, updatedBooking);

        assertNotNull(result);
        assertEquals("New Name", result.getName());
        verify(bookingRepository, times(1)).findById(1L);
        verify(bookingRepository, times(1)).save(existingBooking);
    }

    @Test
    void testDeleteBooking() {
        Booking booking = new Booking();
        booking.setId(1L);

        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        String result = bookingService.deleteBooking(1L);

        assertEquals("Deleted", result);
        verify(bookingRepository, times(1)).findById(1L);
        verify(bookingRepository, times(1)).deleteById(1L);
    }

    @Test
    void testGetAllBookings() {
        Booking booking1 = new Booking();
        booking1.setId(1L);

        Booking booking2 = new Booking();
        booking2.setId(2L);

        when(bookingRepository.findAll()).thenReturn(Arrays.asList(booking1, booking2));

        List<Booking> result = bookingService.getAllBookings();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(bookingRepository, times(1)).findAll();
    }

    @Test
    void testGetBookingByUserId() {
        Booking booking = new Booking();
        booking.setId(1L);

        when(bookingRepository.findByUser_Id(1L)).thenReturn(Arrays.asList(booking));

        List<Booking> result = bookingService.getBookingByUserId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookingRepository, times(1)).findByUser_Id(1L);
    }

    @Test
    void testGetBookingByHotelId() {
        Booking booking = new Booking();
        booking.setId(1L);

        when(bookingRepository.findByHotel_Id(1L)).thenReturn(Arrays.asList(booking));

        List<Booking> result = bookingService.getBookingByHotelId(1L);

        assertNotNull(result);
        assertEquals(1, result.size());
        verify(bookingRepository, times(1)).findByHotel_Id(1L);
    }
}
