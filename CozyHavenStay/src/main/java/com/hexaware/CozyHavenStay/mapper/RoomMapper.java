package com.hexaware.CozyHavenStay.mapper;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.hexaware.CozyHavenStay.dto.RoomRequestDTO;
import com.hexaware.CozyHavenStay.exception.ResourceNotFoundException;
import com.hexaware.CozyHavenStay.model.Hotel;
import com.hexaware.CozyHavenStay.model.Room;
import com.hexaware.CozyHavenStay.repository.HotelRepository;

@Component
public class RoomMapper {

    @Autowired
    private HotelRepository hotelRepository;

    public Room toEntity(RoomRequestDTO dto, Long hotelId) {
        Hotel hotel = hotelRepository.findById(hotelId)
                .orElseThrow(() -> new ResourceNotFoundException("Hotel not found with ID: " + hotelId));

        Room room = new Room();
        room.setHotel(hotel);
        room.setRoomType(dto.getRoomType());
        room.setPricePerNight(dto.getPricePerNight());
        room.setAmenities(dto.getAmenities());
        room.setAvailabilityStatus(dto.getAvailabilityStatus());
        return room;
    }
    public void updateEntity(Room room, RoomRequestDTO dto) {
        if (dto.getRoomType() != null) room.setRoomType(dto.getRoomType());
        if (dto.getPricePerNight() != null) room.setPricePerNight(dto.getPricePerNight());
        if (dto.getAmenities() != null) room.setAmenities(dto.getAmenities());
        if (dto.getAvailabilityStatus() != null) room.setAvailabilityStatus(dto.getAvailabilityStatus());
    }
}
