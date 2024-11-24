package com.hexaware.CozyHavenStay.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.hexaware.CozyHavenStay.dto.RoomRequestDTO;
import com.hexaware.CozyHavenStay.model.Room;
import com.hexaware.CozyHavenStay.service.RoomService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/hotels/{hotelId}/rooms")
public class RoomController {

    @Autowired
    private RoomService roomService;

    @PostMapping
    public ResponseEntity<Room> addRoom(@PathVariable Long hotelId, @Valid @RequestBody RoomRequestDTO roomRequestDTO) {
        Room room = roomService.addRoom(roomRequestDTO, hotelId);
        return ResponseEntity.status(HttpStatus.CREATED).body(room);
    }

    @GetMapping("/{roomId}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long hotelId, @PathVariable Long roomId) {
        Room room = roomService.getRoomById(roomId, hotelId);
        return ResponseEntity.ok(room);
    }

    @GetMapping
    public ResponseEntity<List<Room>> getRoomsByHotel(@PathVariable Long hotelId) {
        List<Room> rooms = roomService.getRoomsByHotel(hotelId);
        return ResponseEntity.ok(rooms);
    }

    @PutMapping("/{roomId}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long hotelId, @PathVariable Long roomId, @Valid @RequestBody RoomRequestDTO roomRequestDTO) {
        Room updatedRoom = roomService.updateRoom(roomId, roomRequestDTO, hotelId);
        return ResponseEntity.ok(updatedRoom);
    }

    @DeleteMapping("/{roomId}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long hotelId, @PathVariable Long roomId) {
        roomService.deleteRoom(roomId);
        return ResponseEntity.ok("Room Deleted");
    }
}