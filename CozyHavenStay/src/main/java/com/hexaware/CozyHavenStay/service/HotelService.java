package com.hexaware.CozyHavenStay.service;

import java.util.List;

import com.hexaware.CozyHavenStay.dto.HotelRequestDTO;
import com.hexaware.CozyHavenStay.model.Hotel;

public interface HotelService {
    Hotel addHotel(HotelRequestDTO hotelRequestDTO);
    Hotel getHotelById(Long hotelId);
    List<Hotel> getAllHotels();
    Hotel updateHotel(Long hotelId, HotelRequestDTO hotelRequestDTO);
    void deleteHotel(Long hotelId);
}