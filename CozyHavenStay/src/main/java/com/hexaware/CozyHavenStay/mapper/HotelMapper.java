package com.hexaware.CozyHavenStay.mapper;

import org.springframework.stereotype.Component;

import com.hexaware.CozyHavenStay.dto.HotelRequestDTO;
import com.hexaware.CozyHavenStay.model.Hotel;

@Component
public class HotelMapper {

    public Hotel toEntity(HotelRequestDTO dto) {
        Hotel hotel = new Hotel();
        hotel.setName(dto.getName());
        hotel.setLocation(dto.getLocation());
        hotel.setDescription(dto.getDescription());
        hotel.setRating(dto.getRating());
        return hotel;
    }
    public void updateEntity(Hotel hotel, HotelRequestDTO dto) {
        if (dto.getName() != null) hotel.setName(dto.getName());
        if (dto.getLocation() != null) hotel.setLocation(dto.getLocation());
        if (dto.getDescription() != null) hotel.setDescription(dto.getDescription());
        if (dto.getRating() != null) hotel.setRating(dto.getRating());
    }
}