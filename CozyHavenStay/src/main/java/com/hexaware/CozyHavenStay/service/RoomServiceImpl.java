package com.hexaware.CozyHavenStay.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.CozyHavenStay.model.Room;
import com.hexaware.CozyHavenStay.repository.RoomRepository;

@Service
public class RoomServiceImpl {

    @Autowired
    private RoomRepository roomRepository;

    public Room createRoom(Room room) {
        // Save and return the room
        return roomRepository.save(room);
    }

    public Optional<Room> getRoomById(Long id) {
        // Retrieve room by id
        return roomRepository.findById(id);
    }

    public Room updateRoom(Long id, Room roomDetails) {
        // Find existing room by id
        Room room = roomRepository.findById(id).orElse(null);

        if (room == null) {
            throw new IllegalArgumentException("Room not found with id " + id);
        }

        // Check if hotel is null and throw an exception or handle accordingly
        if (roomDetails.getHotel() == null || roomDetails.getHotel().getId() == null) {
            throw new IllegalArgumentException("Hotel information must be provided.");
        }

        // Set the new values, including the new roomSize field
        room.setAC(roomDetails.isAC());
        room.setRoomType(roomDetails.getRoomType());
        room.setBaseFare(roomDetails.getBaseFare());
        room.setMaxOccupancy(roomDetails.getMaxOccupancy());
        room.setHotel(roomDetails.getHotel());  // Ensure the hotel is correctly set
        room.setFeatures(roomDetails.getFeatures());
        room.setRoomSize(roomDetails.getRoomSize()); // Set the roomSize

        // Save and return the updated room
        return roomRepository.save(room);
    }

    public String deleteRoom(Long id) {
        // Check if room exists before deleting
        Room room = roomRepository.findById(id).orElse(null);
        if (room != null) {
            roomRepository.deleteById(id);
            return "Deleted";
        } else {
            return "Not Found";
        }
    }

    public List<Room> getAllRooms() {
        // Retrieve all rooms
        return roomRepository.findAll();
    }
}
