package com.hexaware.CozyHavenStay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
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

import com.hexaware.CozyHavenStay.model.Hotel;
import com.hexaware.CozyHavenStay.model.HotelOwner;
import com.hexaware.CozyHavenStay.model.Room;
import com.hexaware.CozyHavenStay.repository.HotelOwnerRepository;
import com.hexaware.CozyHavenStay.repository.HotelRepository;
import com.hexaware.CozyHavenStay.repository.RoomRepository;
import com.hexaware.CozyHavenStay.service.HotelServiceImpl;

class HotelServiceImplTest {

    @InjectMocks
    private HotelServiceImpl hotelService;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private HotelOwnerRepository hotelOwnerRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllHotels() {
        Hotel hotel1 = new Hotel();
        Hotel hotel2 = new Hotel();
        List<Hotel> hotels = Arrays.asList(hotel1, hotel2);

        when(hotelRepository.findAll()).thenReturn(hotels);

        List<Hotel> result = hotelService.getAllHotels();

        assertEquals(2, result.size());
        assertTrue(result.contains(hotel1));
        assertTrue(result.contains(hotel2));
    }

    @Test
    void testGetHotelById() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));

        Hotel result = hotelService.getHotelById(1L);

        assertNotNull(result);
        assertEquals(1L, result.getId());
    }

    @Test
    void testCreateHotel() {
        Hotel hotel = new Hotel();
        HotelOwner owner = new HotelOwner();
        owner.setOwnerId(1L);
        hotel.setOwner(owner);

        when(hotelOwnerRepository.findById(1L)).thenReturn(Optional.of(owner));
        when(hotelRepository.save(hotel)).thenReturn(hotel);

        Hotel result = hotelService.createHotel(hotel);

        assertNotNull(result);
        assertEquals(owner, result.getOwner());
        verify(hotelRepository, times(1)).save(hotel);
    }

    @Test
    void testUpdateHotel() {
        Hotel existingHotel = new Hotel();
        existingHotel.setId(1L);
        existingHotel.setName("Old Name");

        Hotel hotelDetails = new Hotel();
        hotelDetails.setName("New Name");

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(existingHotel));
        when(hotelRepository.save(any(Hotel.class))).thenReturn(existingHotel);

        Hotel result = hotelService.updateHotel(1L, hotelDetails);

        assertEquals("New Name", result.getName());
        verify(hotelRepository, times(1)).save(existingHotel);
    }

    @Test
    void testDeleteHotel() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));

        String result = hotelService.deleteHotel(1L);

        assertEquals("Deleted", result);
        verify(hotelRepository, times(1)).deleteById(1L);
    }

    @Test
    void testAddRoom() {
        Hotel hotel = new Hotel();
        hotel.setId(1L);

        Room room = new Room();

        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(roomRepository.save(room)).thenReturn(room);

        Room result = hotelService.addRoom(1L, room);

        assertNotNull(result);
        assertEquals(hotel, room.getHotel());
    }

    @Test
    void testGetAllRooms() {
        Room room1 = new Room();
        Room room2 = new Room();
        List<Room> rooms = Arrays.asList(room1, room2);

        when(roomRepository.findByHotel_Id(1L)).thenReturn(rooms);

        List<Room> result = hotelService.getAllRooms(1L);

        assertEquals(2, result.size());
        assertTrue(result.contains(room1));
        assertTrue(result.contains(room2));
    }

    @Test
    void testSearchHotels() {
        Hotel hotel = new Hotel();
        List<Hotel> hotels = Arrays.asList(hotel);

        when(hotelRepository.searchHotelsByLocationAndRoomType("Location", "RoomType")).thenReturn(hotels);

        List<Hotel> result = hotelService.searchHotels("Location", "RoomType");

        assertEquals(1, result.size());
        assertEquals(hotel, result.get(0));
    }

    @Test
    void testFindHotelsByLocation() {
        Hotel hotel = new Hotel();
        List<Hotel> hotels = Arrays.asList(hotel);

        when(hotelRepository.findHotelsByLocation("Location")).thenReturn(hotels);

        List<Hotel> result = hotelService.findHotelsByLocation("Location");

        assertEquals(1, result.size());
        assertEquals(hotel, result.get(0));
    }

    @Test
    void testGetHotelsByOwnerId() {
        Hotel hotel = new Hotel();
        List<Hotel> hotels = Arrays.asList(hotel);

        when(hotelRepository.findByOwnerOwnerId(1L)).thenReturn(hotels);

        List<Hotel> result = hotelService.getHotelsByOwnerId(1L);

        assertEquals(1, result.size());
        assertEquals(hotel, result.get(0));
    }
}
