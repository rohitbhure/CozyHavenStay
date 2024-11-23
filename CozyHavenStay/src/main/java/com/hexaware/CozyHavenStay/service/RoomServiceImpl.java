package com.hexaware.CozyHavenStay.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.hexaware.CozyHavenStay.dto.RoomRequestDTO;
import com.hexaware.CozyHavenStay.exception.ResourceNotFoundException;
import com.hexaware.CozyHavenStay.mapper.RoomMapper;
import com.hexaware.CozyHavenStay.model.Hotel;
import com.hexaware.CozyHavenStay.model.Room;
import com.hexaware.CozyHavenStay.repository.HotelRepository;
import com.hexaware.CozyHavenStay.repository.RoomRepository;

@Service
public class RoomServiceImpl implements RoomService {

    @Autowired
    private RoomRepository roomRepository;

    @Autowired
    private HotelRepository hotelRepository;

    @Autowired
    private RoomMapper roomMapper;

    @Override
    public Room addRoom(RoomRequestDTO roomRequestDTO, Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + hotelId));
        Room room = roomMapper.toEntity(roomRequestDTO, hotelId);
        return roomRepository.save(room);
    }

    @Override
    public Room getRoomById(Long roomId, Long hotelId) {
        return roomRepository.findById(roomId)
                .filter(room -> room.getHotel().getHotelId().equals(hotelId))
                .orElseThrow(() -> new ResourceNotFoundException("Room not found with ID: " + roomId + " for Hotel ID: " + hotelId));
    }

    @Override
    public List<Room> getRoomsByHotel(Long hotelId) {
        return roomRepository.findByHotelHotelId(hotelId);
    }

    @Override
    public Room updateRoom(Long roomId, RoomRequestDTO roomRequestDTO, Long hotelId) {
        Room existingRoom = getRoomById(roomId, hotelId);
        roomMapper.updateEntity(existingRoom, roomRequestDTO);
        return roomRepository.save(existingRoom);
    }

    @Override
    public void deleteRoom(Long roomId) {
        roomRepository.deleteById(roomId);
    }
}