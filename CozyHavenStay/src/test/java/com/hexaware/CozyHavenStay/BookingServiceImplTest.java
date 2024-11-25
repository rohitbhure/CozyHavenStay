package com.hexaware.CozyHavenStay;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

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
import com.hexaware.CozyHavenStay.service.BookingServiceImpl;

class BookingServiceImplTest {

    @Mock
    private BookingRepository bookingRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private BookingMapper bookingMapper;

    @InjectMocks
    private BookingServiceImpl bookingService;

    private Booking booking;
    private BookingRequestDTO bookingRequestDTO;
    private User user;
    private Hotel hotel;
    private Room room;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock objects
        user = new User();
        user.setUserId(1L);
        user.setName("John Doe");

        hotel = new Hotel();
        hotel.setHotelId(1L);
        hotel.setName("Luxury Hotel");

        room = new Room();
        room.setRoomId(1L);
        room.setRoomType("KingSized");

        booking = new Booking();
        booking.setBookingId(1L);
        booking.setUser(user);
        booking.setHotel(hotel);
        booking.setRoom(room);
        booking.setCheckInDate(LocalDate.of(2023, 12, 1));
        booking.setCheckOutDate(LocalDate.of(2023, 12, 5));
        booking.setBookingStatus("Confirmed");

        bookingRequestDTO = new BookingRequestDTO();
        bookingRequestDTO.setUserId(1L);
        bookingRequestDTO.setHotelId(1L);
        bookingRequestDTO.setRoomId(1L);
        bookingRequestDTO.setCheckInDate(LocalDate.of(2023, 12, 1));
        bookingRequestDTO.setCheckOutDate(LocalDate.of(2023, 12, 5));
        bookingRequestDTO.setBookingStatus("Confirmed");
    }

    @Test
    void testCreateBooking() {
        when(bookingMapper.toEntity(bookingRequestDTO)).thenReturn(booking);
        when(bookingRepository.save(booking)).thenReturn(booking);

        Booking createdBooking = bookingService.createBooking(bookingRequestDTO);

        assertNotNull(createdBooking);
        assertEquals(booking.getBookingId(), createdBooking.getBookingId());
        verify(bookingRepository, times(1)).save(booking);
    }

    @Test
    void testGetBookingById_BookingExists() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        Booking fetchedBooking = bookingService.getBookingById(1L);

        assertNotNull(fetchedBooking);
        assertEquals(booking.getBookingId(), fetchedBooking.getBookingId());
        verify(bookingRepository, times(1)).findById(1L);
    }

    @Test
    void testGetBookingById_BookingDoesNotExist() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> bookingService.getBookingById(1L));
        verify(bookingRepository, times(1)).findById(1L);
    }

    @Test
    void testGetAllBookings() {
        List<Booking> bookings = Arrays.asList(booking, new Booking());
        when(bookingRepository.findAll()).thenReturn(bookings);

        List<Booking> fetchedBookings = bookingService.getAllBookings();

        assertNotNull(fetchedBookings);
        assertEquals(2, fetchedBookings.size());
        verify(bookingRepository, times(1)).findAll();
    }

    @Test
    void testGetBookingsByUser() {
        List<Booking> bookings = Arrays.asList(booking);
        when(bookingRepository.findByUserUserId(1L)).thenReturn(bookings);

        List<Booking> fetchedBookings = bookingService.getBookingsByUser(1L);

        assertNotNull(fetchedBookings);
        assertEquals(1, fetchedBookings.size());
        verify(bookingRepository, times(1)).findByUserUserId(1L);
    }

    @Test
    void testGetBookingsByHotel() {
        List<Booking> bookings = Arrays.asList(booking);
        when(bookingRepository.findByHotelHotelId(1L)).thenReturn(bookings);

        List<Booking> fetchedBookings = bookingService.getBookingsByHotel(1L);

        assertNotNull(fetchedBookings);
        assertEquals(1, fetchedBookings.size());
        verify(bookingRepository, times(1)).findByHotelHotelId(1L);
    }

    @Test
    void testUpdateBooking() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(bookingRepository.save(booking)).thenReturn(booking);

        Booking updatedBooking = bookingService.updateBooking(1L, bookingRequestDTO);

        assertNotNull(updatedBooking);
        assertEquals("Confirmed", updatedBooking.getBookingStatus());
        verify(bookingRepository, times(1)).findById(1L);
        verify(userRepository, times(1)).findById(1L);
        verify(hotelRepository, times(1)).findById(1L);
        verify(roomRepository, times(1)).findById(1L);
        verify(bookingRepository, times(1)).save(booking);
    }

    @Test
    void testCancelBooking() {
        when(bookingRepository.findById(1L)).thenReturn(Optional.of(booking));

        bookingService.cancelBooking(1L);

        assertEquals("Cancelled", booking.getBookingStatus());
        verify(bookingRepository, times(1)).findById(1L);
        verify(bookingRepository, times(1)).save(booking);
    }
}
