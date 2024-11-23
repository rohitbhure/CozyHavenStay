package com.hexaware.CozyHavenStay.service;

import java.util.List;

import com.hexaware.CozyHavenStay.dto.RoomRequestDTO;
import com.hexaware.CozyHavenStay.model.Room;

public interface RoomService {
    Room addRoom(RoomRequestDTO roomRequestDTO, Long hotelId);
    Room getRoomById(Long roomId, Long hotelId);
    List<Room> getRoomsByHotel(Long hotelId);
    Room updateRoom(Long roomId, RoomRequestDTO roomRequestDTO, Long hotelId);
    void deleteRoom(Long roomId);
}