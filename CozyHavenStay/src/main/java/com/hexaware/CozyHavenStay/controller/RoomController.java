package com.hexaware.CozyHavenStay.controller;

import java.util.List;
import java.util.Optional;

import org.modelmapper.ModelMapper;
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

import com.hexaware.CozyHavenStay.dto.RoomDTO;
import com.hexaware.CozyHavenStay.exception.RoomNotFoundException;
import com.hexaware.CozyHavenStay.model.Room;
import com.hexaware.CozyHavenStay.service.RoomServiceImpl;

@RestController
@RequestMapping("/api/rooms")
public class RoomController {

    @Autowired
    private RoomServiceImpl roomService;

    @Autowired
    private ModelMapper mp;

    @PostMapping("/create")
    public ResponseEntity<RoomDTO> createRoom(@RequestBody RoomDTO room) {
        Room room1 = mp.map(room, Room.class);
        Room savedRoom = roomService.createRoom(room1);
        RoomDTO response = mp.map(savedRoom, RoomDTO.class);
        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @GetMapping("/getroombyid/{id}")
    public ResponseEntity<RoomDTO> getRoom(@PathVariable Long id) throws RoomNotFoundException {
        Optional<Room> room = roomService.getRoomById(id);
        if (room.isPresent()) {
            RoomDTO roomDTO = mp.map(room.get(), RoomDTO.class);
            return new ResponseEntity<>(roomDTO, HttpStatus.OK);
        } else {
            throw new RoomNotFoundException("Room not found");
        }
    }

    @PutMapping("/updateroombyid/{id}")
    public ResponseEntity<RoomDTO> updateRoom(@PathVariable Long id, @RequestBody RoomDTO r) throws RoomNotFoundException {
        Room room = mp.map(r, Room.class);
        Room updatedRoom = roomService.updateRoom(id, room);
        if(updatedRoom!=null) {
        RoomDTO roomDTO = mp.map(updatedRoom, RoomDTO.class);
        return new ResponseEntity<>(roomDTO, HttpStatus.OK);
        }
        else {
        	throw new RoomNotFoundException("Room not found");
			//return new ResponseEntity<>( HttpStatus.NOT_FOUND);
		}
    }

    @DeleteMapping("/deleteroombyid/{id}")
    public ResponseEntity<String> deleteRoom(@PathVariable Long id) {
        String str=roomService.deleteRoom(id);
        if(str.equals("Deleted"))
        	return new ResponseEntity<String>(str,HttpStatus.OK);
        else
        return new ResponseEntity<String>(str,HttpStatus.NOT_FOUND);
    }
    
    

    @GetMapping("/allrooms")
    public ResponseEntity<List<RoomDTO>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        List<RoomDTO> roomDTOs = rooms.stream().map((temp)->mp.map(temp,RoomDTO.class)).toList();
        
        if(roomDTOs.isEmpty())
        {
        	return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        else {
        	return new ResponseEntity<>(roomDTOs, HttpStatus.OK);
		}
    }
    
    
    
    
}
