package com.hexaware.CozyHavenStay;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import com.hexaware.CozyHavenStay.dto.RoomRequestDTO;
import com.hexaware.CozyHavenStay.exception.ResourceNotFoundException;
import com.hexaware.CozyHavenStay.mapper.RoomMapper;
import com.hexaware.CozyHavenStay.model.Hotel;
import com.hexaware.CozyHavenStay.model.Room;
import com.hexaware.CozyHavenStay.repository.HotelRepository;
import com.hexaware.CozyHavenStay.repository.RoomRepository;
import com.hexaware.CozyHavenStay.service.RoomServiceImpl;

class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    @Mock
    private HotelRepository hotelRepository;

    @Mock
    private RoomMapper roomMapper;

    @InjectMocks
    private RoomServiceImpl roomService;

    private Room room;
    private Hotel hotel;
    private RoomRequestDTO roomRequestDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        // Mock Hotel
        hotel = new Hotel();
        hotel.setHotelId(1L);
        hotel.setName("Luxury Stay");

        // Mock Room
        room = new Room();
        room.setRoomId(101L);
        room.setRoomType("Deluxe");
        room.setPricePerNight(200.0);
        room.setHotel(hotel);

        // Mock RoomRequestDTO
        roomRequestDTO = new RoomRequestDTO();
        roomRequestDTO.setRoomType("Deluxe");
        roomRequestDTO.setPricePerNight(200.0);
    }

    @Test
    void testAddRoom() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.of(hotel));
        when(roomMapper.toEntity(roomRequestDTO, 1L)).thenReturn(room);
        when(roomRepository.save(room)).thenReturn(room);

        Room addedRoom = roomService.addRoom(roomRequestDTO, 1L);

        assertNotNull(addedRoom);
        assertEquals(room.getRoomId(), addedRoom.getRoomId());
        verify(hotelRepository, times(1)).findById(1L);
        verify(roomMapper, times(1)).toEntity(roomRequestDTO, 1L);
        verify(roomRepository, times(1)).save(room);
    }

    @Test
    void testAddRoom_HotelNotFound() {
        when(hotelRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> roomService.addRoom(roomRequestDTO, 1L));
        verify(hotelRepository, times(1)).findById(1L);
        verifyNoInteractions(roomMapper, roomRepository);
    }

    @Test
    void testGetRoomById_RoomExists() {
        when(roomRepository.findById(101L)).thenReturn(Optional.of(room));

        Room fetchedRoom = roomService.getRoomById(101L, 1L);

        assertNotNull(fetchedRoom);
        assertEquals(room.getRoomId(), fetchedRoom.getRoomId());
        verify(roomRepository, times(1)).findById(101L);
    }

    @Test
    void testGetRoomById_RoomNotFound() {
        when(roomRepository.findById(101L)).thenReturn(Optional.empty());

        assertThrows(ResourceNotFoundException.class, () -> roomService.getRoomById(101L, 1L));
        verify(roomRepository, times(1)).findById(101L);
    }

    @Test
    void testGetRoomsByHotel() {
        List<Room> rooms = Arrays.asList(room, new Room());
        when(roomRepository.findByHotelHotelId(1L)).thenReturn(rooms);

        List<Room> fetchedRooms = roomService.getRoomsByHotel(1L);

        assertNotNull(fetchedRooms);
        assertEquals(2, fetchedRooms.size());
        verify(roomRepository, times(1)).findByHotelHotelId(1L);
    }

    @Test
    void testUpdateRoom() {
        when(roomRepository.findById(101L)).thenReturn(Optional.of(room));
        doNothing().when(roomMapper).updateEntity(room, roomRequestDTO);
        when(roomRepository.save(room)).thenReturn(room);

        Room updatedRoom = roomService.updateRoom(101L, roomRequestDTO, 1L);

        assertNotNull(updatedRoom);
        assertEquals(room.getRoomType(), updatedRoom.getRoomType());
        verify(roomRepository, times(1)).findById(101L);
        verify(roomMapper, times(1)).updateEntity(room, roomRequestDTO);
        verify(roomRepository, times(1)).save(room);
    }

    @Test
    void testDeleteRoom() {
        doNothing().when(roomRepository).deleteById(101L);

        roomService.deleteRoom(101L);

        verify(roomRepository, times(1)).deleteById(101L);
    }
}
