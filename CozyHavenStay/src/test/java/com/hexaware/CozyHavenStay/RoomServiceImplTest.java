package com.hexaware.CozyHavenStay;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.hexaware.CozyHavenStay.model.Hotel;
import com.hexaware.CozyHavenStay.model.Room;
import com.hexaware.CozyHavenStay.repository.RoomRepository;
import com.hexaware.CozyHavenStay.service.RoomServiceImpl;

@ExtendWith(MockitoExtension.class)
public class RoomServiceImplTest {

    @Mock
    private RoomRepository roomRepository;

    @InjectMocks
    private RoomServiceImpl roomService;

    private Room room;
    private Hotel hotel;

    @BeforeEach
    void setUp() {
        hotel = new Hotel();
        hotel.setId(1L);
        hotel.setName("Test Hotel");

        room = new Room();
        room.setId(1L);
        room.setAC(true);
        room.setRoomType("Deluxe");
        room.setBaseFare(2000.0);
        room.setMaxOccupancy(4);
        room.setHotel(hotel);
        room.setFeatures(Arrays.asList("Sea View", "King Bed"));
        room.setRoomSize(300.0);
    }

    @Test
    void testCreateRoom() {
        when(roomRepository.save(room)).thenReturn(room);

        Room createdRoom = roomService.createRoom(room);

        assertNotNull(createdRoom);
        assertEquals(room.getId(), createdRoom.getId());
        verify(roomRepository, times(1)).save(room);
    }

    @Test
    void testGetRoomById() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        Optional<Room> foundRoom = roomService.getRoomById(1L);

        assertTrue(foundRoom.isPresent());
        assertEquals(room.getId(), foundRoom.get().getId());
        verify(roomRepository, times(1)).findById(1L);
    }

    @Test
    void testUpdateRoom() {
        Room updatedDetails = new Room();
        updatedDetails.setAC(false);
        updatedDetails.setRoomType("Standard");
        updatedDetails.setBaseFare(1500.0);
        updatedDetails.setMaxOccupancy(3);
        updatedDetails.setHotel(hotel);
        updatedDetails.setFeatures(Arrays.asList("Garden View"));
        updatedDetails.setRoomSize(250.0);

        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));
        when(roomRepository.save(any(Room.class))).thenReturn(updatedDetails);

        Room updatedRoom = roomService.updateRoom(1L, updatedDetails);

        assertNotNull(updatedRoom);
        assertEquals(updatedDetails.getRoomType(), updatedRoom.getRoomType());
        assertEquals(updatedDetails.getRoomSize(), updatedRoom.getRoomSize());
        verify(roomRepository, times(1)).findById(1L);
        verify(roomRepository, times(1)).save(any(Room.class));
    }

    @Test
    void testDeleteRoom() {
        when(roomRepository.findById(1L)).thenReturn(Optional.of(room));

        String result = roomService.deleteRoom(1L);

        assertEquals("Deleted", result);
        verify(roomRepository, times(1)).findById(1L);
        verify(roomRepository, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteRoomNotFound() {
        when(roomRepository.findById(1L)).thenReturn(Optional.empty());

        String result = roomService.deleteRoom(1L);

        assertEquals("Not Found", result);
        verify(roomRepository, times(1)).findById(1L);
        verify(roomRepository, times(0)).deleteById(anyLong());
    }

    @Test
    void testGetAllRooms() {
        when(roomRepository.findAll()).thenReturn(Arrays.asList(room));

        List<Room> rooms = roomService.getAllRooms();

        assertNotNull(rooms);
        assertEquals(1, rooms.size());
        verify(roomRepository, times(1)).findAll();
    }
}
